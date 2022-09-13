package View;

import Trello.BoardConcrete;
import Trello.CardConcrete;
import Trello.Component;
import Trello.ComponentDescription;
import Trello.TrelloClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class RemoveListController {

    @FXML
    private Label inputMessageLabel;

    @FXML
    private TextField columnPositionTextField;


    @FXML
    private Button inputButton;
    BoardConcrete model;
    BoardController controller;
    BorderPane pane;
    String desc;

    //this is lazy but gets the job done without setting up a new pattern right now.
    @FXML
    void inputButtonTapped(ActionEvent event) {
    	if(!this.columnPositionTextField.getText().isEmpty() && this.columnPositionTextField.getText() != null)
    	{
    		
    		int iColumn = Integer.valueOf(this.columnPositionTextField.getText());
    		if(iColumn < this.model.getLists().size())
    		{
    			
    			
    			this.model.getLists().remove(iColumn);
        		this.controller.editorDoneDisplaying(pane);
    		} else 
    		{
    			//this would be index out of bounds
    		}
    		
    		
    	}
    }
    
    
    
    void setModel(BoardConcrete model, BoardController controller, BorderPane pane)
    {
    	this.model = model;
    	this.controller = controller;
    	this.pane = pane;  	
    }
    
    

}
