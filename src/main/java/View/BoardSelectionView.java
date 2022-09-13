package View;

import Trello.TrelloClient;
import Trello.TrelloServer;
import Trello.UserConcrete;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BoardSelectionView
{
	
	public BoardSelectionView(Stage stage, TrelloClient client) throws Exception
	{
		System.out.println("here again?");
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(BoardSelectionView.class.getResource("/View/boardSelection.fxml"));
		
		BorderPane pane = loader.load();
		
		BoardSelectionController cont = loader.getController();
		
		cont.setModel((UserConcrete) client.getLoggedInUser(), client);
		System.out.println("BoardSelectionView");
//		System.out.println(TrelloClient.getInstance().getLoggedInUser().getBoards().get(0).getName());
		Scene s = new Scene(pane);
		
		stage.setScene(s);
		
		stage.show();
		
	}
}
