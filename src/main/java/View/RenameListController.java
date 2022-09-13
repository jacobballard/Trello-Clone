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

public class RenameListController {

    @FXML
    private Label inputMessageLabel;

    @FXML
    private TextField positionTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button inputButton;
    BoardConcrete model;
    BoardController controller;
    BorderPane pane;
    String desc;

    //this is lazy but gets the job done without setting up a new pattern right now.
    @FXML
    void inputButtonTapped(ActionEvent event) {
    	if(!this.positionTextField.getText().isEmpty() && this.positionTextField.getText() != null &&
    		!this.nameTextField.getText().isEmpty() && this.nameTextField.getText() != null)
    	{
    		int i = Integer.valueOf(this.positionTextField.getText());
    		if(this.model.getLists().size() <= i)
    		{
    			//this would be index out of bounds
    		} else 
    		{
    			this.model.getLists().get(i).updateName(this.nameTextField.getText());
        		this.controller.editorDoneDisplaying(pane);
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
