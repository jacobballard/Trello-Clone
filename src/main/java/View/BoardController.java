package View;

import java.io.IOException;
import java.util.ArrayList;

import Trello.BoardConcrete;
import Trello.Card;
import Trello.CardConcrete;
import Trello.List;
import Trello.ListConcrete;
import Trello.TrelloClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BoardController {

	private BoardConcrete model;
	
	private Stage primaryStage;
	
	private StackPane stackPane;
	
	private BorderPane borderPane;
	
	Stage stage;
	

	Scene mainScene;
	
	TrelloClient client;
	
    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem saveBoardMenuItem;
    
    @FXML
    private MenuItem deleteBoardMenuItem;

    @FXML
    private MenuItem exitBoardMenuItem;

    @FXML
    private Menu listMenu;

    @FXML
    private MenuItem addListMenuItem;

    @FXML
    private MenuItem deleteListMenuItem;

    @FXML
    private MenuItem renameListMenuItem;

    @FXML
    private Menu cardMenu;

    @FXML
    private MenuItem addCardMenuItem;

    @FXML
    private MenuItem deleteCardMenuItem;
    
    @FXML
    private MenuItem reorderCardMenuItem;

    @FXML
    private MenuItem renameCardMenuItem;

    @FXML
    private MenuItem addMemberMenuItem;

    @FXML
    private MenuItem removeMemberMenuItem;

    @FXML
    private HBox boardHBox;
    
    @FXML
    private MenuItem renameBoardMenuItem;
    

	


	public void setModel(BoardConcrete model, Stage stage, StackPane sp, BorderPane bp, TrelloClient client)
	{
		this.model = model;
		this.primaryStage = stage;
		this.client = client;
		
//		this.mainScene = stage.getScene();

		this.stackPane = sp;
		this.borderPane = bp;
	}
	
	public BoardConcrete getModel()
	{
		return this.model;
	}
	
	
	public void generateBoard()
	{
		System.out.println("generate board?");
		HBox h = new HBox();
		
		h.setSpacing(10);
//		for(Node n : h.getChildren())
//		{
//			h.getChildren().remove((Pane)n);
//		}
		
		borderPane.setCenter(h);
		VBox v;
		Pane p;
		Label l;
		Button b;
		
		int i = 0;
		int j = 0;
		
		for(List list : model.getLists())
		{
			//make vbox
			v = new VBox();
			
			v.setBackground(new Background(new BackgroundFill(Color.BLANCHEDALMOND, CornerRadii.EMPTY, Insets.EMPTY)));
			
			v.getChildren().add(new Label(list.getName()));
			//add cards to vbox
			for(Card c : list.getCards())
			{
				p = new VBox();
				l = new Label(c.getName());
				b = new Button();
				b.setId("cardButton" + Integer.toString(i) + Integer.toString(j));
				
				
				b.setText("Edit");
				p.getChildren().add(l);
				p.getChildren().add(b);
				
				b.setOnAction(new EventHandler<ActionEvent>() {
					
				    @Override public void handle(ActionEvent e) {
				    	
				        displayCard(c);
				    }
				});
				
				
				v.getChildren().add(p);
				j++;
			}
			
			i++;
			
			h.getChildren().add(v);
			
			
			
		}
		
		
		
		
	}
	
	@FXML
    void deleteBoard(ActionEvent event) {
		
		try
		{
			this.client.deleteBoard(this.model);
    		this.client.hideStage();
			this.client.loadNextStep("Selector", null);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void displayCard(Card c)
	{
//		this.primaryStage.hide();
		
//		Pane p = new Pane();
		
		stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(BoardView.class.getResource("/View/cardView.fxml"));
		BorderPane card = null;
		try
		{
			card = loader.load();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CardController cont = loader.getController();
		
		cont.setModel(c, this, card, stackPane);
		
		Scene scene = new Scene(card, 600, 400);
		card.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.stackPane.getChildren().add(card);
		stage.setScene(scene);
		stage.show();
		
	}
	
	void cardDoneDisplaying(BorderPane card)
	{
//		this.stackPane.getChildren().remove(card);
		stage.hide();
		this.generateBoard();
		
	}
	
	@FXML
    void reorderCard(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader();
    	
    	stage = new Stage();
    	
    	
		
		loader.setLocation(BoardView.class.getResource("/View/reorder_card.fxml"));
		BorderPane input_view = null;
		try
		{
			input_view = loader.load();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scene scene = new Scene(input_view, 300, 200);
		
		stage.setScene(scene);
		
		ReorderCardController cont = loader.getController();
		
		//this should be template pattern, easier to do it this way for now
		cont.setModel(this.model, this, input_view);
	
		
		
		input_view.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.stackPane.getChildren().add(input_view);
		stage.show();
	}
	

    @FXML
    void addCard(ActionEvent event) {
    	
    	if(model.getLists().size() > 0)
    	{
    		model.getLists().get(model.getLists().size() - 1).getCards().add(new CardConcrete());
    	} else
    	{
    		model.getLists().add(new ListConcrete());
    		model.getLists().get(model.getLists().size() - 1).getCards().add(new CardConcrete());
    	}
    	
    	this.generateBoard();
    }

    @FXML
    void addList(ActionEvent event) {
    	model.getLists().add(new ListConcrete());
    	this.generateBoard();
    }

    @FXML
    void addMember(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	
    	stage = new Stage();
    	
    	
		
		loader.setLocation(BoardView.class.getResource("/View/input_view.fxml"));
		BorderPane input_view = null;
		try
		{
			input_view = loader.load();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scene scene = new Scene(input_view, 300, 200);
		
		stage.setScene(scene);
		
		InputTextController cont = loader.getController();
		
		//this should be template pattern, easier to do it this way for now
		cont.setModel(this.model, this, input_view, "add_board_member");
	
		
		
		input_view.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.stackPane.getChildren().add(input_view);
		stage.show();
    	
    }

    @FXML
    void deleteCard(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	
    	stage = new Stage();
    	
    	
		
		loader.setLocation(BoardView.class.getResource("/View/remove_card.fxml"));
		BorderPane input_view = null;
		try
		{
			input_view = loader.load();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scene scene = new Scene(input_view, 300, 200);
		
		stage.setScene(scene);
		
		RemoveCardController cont = loader.getController();
		
		//this should be template pattern, easier to do it this way for now
		cont.setModel(this.model, this, input_view);
	
		
		
		input_view.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.stackPane.getChildren().add(input_view);
		stage.show();
    }

    @FXML
    void deleteList(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	
    	stage = new Stage();
    	
    	
		
		loader.setLocation(BoardView.class.getResource("/View/remove_list.fxml"));
		BorderPane input_view = null;
		try
		{
			input_view = loader.load();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scene scene = new Scene(input_view, 300, 200);
		
		stage.setScene(scene);
		
		RemoveListController cont = loader.getController();
		
		//this should be template pattern, easier to do it this way for now
		cont.setModel(this.model, this, input_view);
	
		
		
		input_view.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.stackPane.getChildren().add(input_view);
		stage.show();
    }

    @FXML
    void exitBoard(ActionEvent event) {
    	//go back to selection via client
    	try
		{
    		this.client.hideStage();
			this.client.loadNextStep("Selector", null);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    void removeMember(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	
    	stage = new Stage();
    	
    	
		
		loader.setLocation(BoardView.class.getResource("/View/input_view.fxml"));
		BorderPane input_view = null;
		try
		{
			input_view = loader.load();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scene scene = new Scene(input_view, 300, 200);
		
		stage.setScene(scene);
		
		InputTextController cont = loader.getController();
		
		//this should be template pattern, easier to do it this way for now
		cont.setModel(this.model, this, input_view, "remove_board_member");
	
		
		
		input_view.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.stackPane.getChildren().add(input_view);
		stage.show();
    	
    }

    @FXML
    void renameCard(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	
    	stage = new Stage();
    	
    	
		
		loader.setLocation(BoardView.class.getResource("/View/rename_card.fxml"));
		BorderPane input_view = null;
		try
		{
			input_view = loader.load();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scene scene = new Scene(input_view, 300, 200);
		
		stage.setScene(scene);
		
		RenameCardController cont = loader.getController();
		
		//this should be template pattern, easier to do it this way for now
		cont.setModel(this.model, this, input_view);
	
		
		
		input_view.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.stackPane.getChildren().add(input_view);
		stage.show();
    	
    	
    }

    @FXML
    void renameList(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	
    	stage = new Stage();
    	
    	
		
		loader.setLocation(BoardView.class.getResource("/View/rename_list.fxml"));
		BorderPane input_view = null;
		try
		{
			input_view = loader.load();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scene scene = new Scene(input_view, 300, 200);
		
		stage.setScene(scene);
		
		RenameListController cont = loader.getController();
		
		//this should be template pattern, easier to do it this way for now
		cont.setModel(this.model, this, input_view);
	
		
		
		input_view.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.stackPane.getChildren().add(input_view);
		stage.show();
    	
    	
    }

    @FXML
    void saveBoard(ActionEvent event) {
    	
    	
    	System.out.println("To Save :  " + this.model.getBoardName());
    	
    	this.client.updateBoard(model);
    	
    	System.out.println("save board");
    	
    
    }
    
    @FXML
    void renameBoard(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	
    	stage = new Stage();
    	
    	
		
		loader.setLocation(BoardView.class.getResource("/View/input_view.fxml"));
		BorderPane input_view = null;
		try
		{
			input_view = loader.load();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scene scene = new Scene(input_view, 300, 200);
		
		stage.setScene(scene);
		
		InputTextController cont = loader.getController();
		
		//this should be template pattern, easier to do it this way for now
		cont.setModel(this.model, this, input_view, "edit_board_name");
		
		System.out.println("this.model test");
		System.out.println(this.model);
		
		
		
		input_view.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.stackPane.getChildren().add(input_view);
		stage.show();
    }
    
    void editorDoneDisplaying(BorderPane editor)
    {
    	System.out.println("this.model done displaying");
		System.out.println(this.model);
    	stage.hide();
    	this.generateBoard();
    }

}
