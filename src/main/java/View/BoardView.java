package View;

import Trello.BoardConcrete;
import Trello.TrelloClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BoardView
{

	
	public BoardView(Stage stage, BoardConcrete b, TrelloClient client) throws Exception
	{
		
		
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(BoardView.class.getResource("/View/board_view.fxml"));
		
		BorderPane pane = loader.load();
		
		BoardController cont = loader.getController();
		StackPane sp = new StackPane(pane);
		
		cont.setModel(b, stage, sp, pane, client);
		
		cont.generateBoard();
		
		
		
		Scene s = new Scene(sp);
		
		stage.setScene(s);
		
		stage.show();
		
	}

}
