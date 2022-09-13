package View;

import Trello.TrelloClient;
import Trello.TrelloServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginView extends Application
{

	@Override
	public void start(Stage stage) throws Exception
	{

//		TrelloServer server = new TrelloServer(2010);
//		TrelloClient client  =  new TrelloClient

		TrelloServer server = new TrelloServer(2013);
		TrelloClient client = new TrelloClient(2013);

		
		client.setStage(stage);
		System.out.println("client and server done");
		
		FXMLLoader loader = new FXMLLoader();
		
		System.out.println("loginView?");
		
		loader.setLocation(LoginView.class.getResource("/View/loginView.fxml"));
		
		System.out.println("LoginView.");
		
		
		AnchorPane pane = loader.load();
		
		System.out.println("loader.load");
		
		System.out.println("get controller");

		LoginController cont = loader.getController();
		cont.setup();
		
		System.out.println("get controller?");
		cont.setClient(client);
		
		Scene s = new Scene(pane);
		
		stage.setScene(s);
		stage.show();

		
	}
	
	public static void main(String[] args) {
        launch(args);
    }

}
