package View;

import java.io.IOException;

import Trello.Card;
import Trello.Component;
import Trello.ComponentChecklist;
import Trello.ComponentDescription;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CardController {

    @FXML
    private VBox labelsVBox;

    @FXML
    private Button editLabelsButton;

    @FXML
    private VBox checklistVBox;

    @FXML
    private Button editChecklistButton;

    @FXML
    private VBox descriptionVBox;

    @FXML
    private Button editDescriptionButton;

    @FXML
    private Label cardTitleLabel;

    @FXML
    private Button editTitleButton;

    @FXML
    private Button exitButton;
    
    Card card;
    
    Stage stage;
    
    BoardController boardController;
    
    BorderPane cardView;
    
    StackPane stackPane;
    
    ComponentChecklist checkList;
    
    void setModel(Card c, BoardController bc, BorderPane cv, StackPane stack)
    {
    	this.card = c;
    	this.boardController = bc;
    	this.cardView = cv;
    	this.stackPane = stack;
    }
    
    void generateCard()
    {
//    	this.stackPane.getChildren().remove(cardView);
    	
    	
    	
//		FXMLLoader loader = new FXMLLoader();
//		
//		loader.setLocation(CardController.class.getResource("/View/cardView.fxml"));
//		BorderPane card_loaded = null;
//		try
//		{
//			card_loaded = loader.load();
//			
//		} catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		this.cardView = card_loaded;
//		
//		
//		
//		
//		cardView.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
////		this.stackPane.getChildren().add(cardView);
//		
//		this.stage.setScene(new Scene(cardView, 600, 400));
    	this.cardTitleLabel.setText("test Jacob");
		
    	if(!card.getName().isEmpty() && card.getName() != null)
    	{
    		this.cardTitleLabel = new Label();
    		this.cardTitleLabel.setText(card.getName());
    	}
    	if(card.getLabels().size() > 0)
    	{
    		System.out.println("> 0");
    		for(Node n : this.labelsVBox.getChildren())
    		{
    			if(n instanceof Label)
    			{
    				if(!((Label)n).getText().equals("Card Labels:"))
    				{
    					this.labelsVBox.getChildren().remove(n);
    				}
    			}
    		}
//    		this.labelsVBox.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
    		for(String label : card.getLabels())
    		{
    			System.out.println(label);
    			this.labelsVBox.getChildren().add(new Label(label));
    		}
    	}
    	
    	if(card.getComponents().size() > 0)
    	{
    		for(Component com : card.getComponents())
    		{
    			if(com instanceof ComponentChecklist)
    			{
    				
    				ComponentChecklist cc = (ComponentChecklist) com;
    				this.checkList = cc;
    				for(Node n : this.checklistVBox.getChildren())
    				{
    					if(n instanceof CheckBox)
    					{
//    						System.out.println("hey");
    						this.checklistVBox.getChildren().remove(n);
    					}
    				}
    				if(cc.getChecklist().size() > 0 && cc.getChecklist().size() == cc.getIsSelected().size())
    				{
    					
    					for(int i = this.checklistVBox.getChildren().size() - 2; i < cc.getChecklist().size(); i++)
    					{
    						System.out.println("getChecklist().size()");
    						System.out.println(cc.getChecklist().get(i));
    						CheckBox box = new CheckBox();
    						box.setText(cc.getChecklist().get(i));
    						box.setSelected(cc.getIsSelected().get(i));
    						this.checklistVBox.getChildren().add(1, box);
    					}
    				}
    			} else if(com instanceof ComponentDescription)
    			{
    				this.descriptionVBox = new VBox();
    				ComponentDescription cd = (ComponentDescription) com;
    				Label l = new Label(cd.getDescription());
    				this.descriptionVBox.getChildren().add(l);
    			}
    		}
    	}
    }
    
    @FXML
    void editChecklistButtonTapped(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
		stage = new Stage();
		loader.setLocation(BoardView.class.getResource("/View/editChecklistView.fxml"));
		BorderPane checkView = null;
		try
		{
			checkView = loader.load();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ChecklistController cont = loader.getController();
		
		
		
		if(this.checkList != null)
		{
			cont.setModel(this.checkList, this, checkView);
		} else {
			this.checkList = new ComponentChecklist();
			cont.setModel(this.checkList, this, checkView);
			this.card.addComponent(this.checkList);
			
		}
		
		
		stage.setScene(new Scene(checkView, 600, 400));
		checkView.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.stackPane.getChildren().add(checkView);
		stage.show();
    }

    @FXML
    void editDescriptionButtonTapped(ActionEvent event) {
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
		
		InputTextController cont = loader.getController();
		
		//this should be template pattern, easier to do it this way for now
		cont.setModel(this.card, this, input_view, "edit_card_desc");
		
		stage.setScene(new Scene(input_view, 600, 400));
		
		
		input_view.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.stackPane.getChildren().add(input_view);
		stage.show();
    }

    @FXML
    void editLabelsButtonTapped(ActionEvent event) {
    	System.out.println("why?");
    	
    	stage = new Stage();
    	FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(CardController.class.getResource("/View/editLabelView.fxml"));
		BorderPane input_view = null;
		try
		{
			input_view = loader.load();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("why again?");
		
		LabelController cont = loader.getController();
		
		//this should be template pattern, easier to do it this way for now
		cont.setModel(this.card.getLabels(), this, input_view);
		
		System.out.println("why 2?");
		
		stage.setScene(new Scene(input_view, 600, 400));
		
		
		
		
		input_view.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.stackPane.getChildren().add(input_view);
		stage.show();
		
		System.out.println("why4 ?");
    }

    @FXML
    void editTitleButtonTapped(ActionEvent event) {
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
		
		InputTextController cont = loader.getController();
		
		//this should be template pattern, easier to do it this way for now
		cont.setModel(this.card, this, input_view, "edit_card_title");
		
		stage.setScene(new Scene(input_view, 600, 400));
		
		
		
		input_view.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
//		this.stackPane.getChildren().add(input_view);
		
		stage.show();
    }
    
    void editorDoneDisplaying(BorderPane editor)
    {
    	//reusuable for all editing
//    	this.stackPane.getChildren().remove(editor);
    	stage.hide();
    	this.generateCard();
    }

    @FXML
    void exitButtonTapped(ActionEvent event) {
    	this.boardController.cardDoneDisplaying(cardView);
    }
    
    

}
