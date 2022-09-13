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

public class RenameCardController {

    @FXML
    private Label inputMessageLabel;

    @FXML
    private TextField rowPositionTextField;

    @FXML
    private TextField columnPositionTextField;

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
    	if(!this.rowPositionTextField.getText().isEmpty() && this.rowPositionTextField.getText() != null &&
    		!this.nameTextField.getText().isEmpty() && this.nameTextField.getText() != null &&
    		!this.columnPositionTextField.getText().isEmpty() && this.columnPositionTextField.getText() != null)
    	{
    		int iRow = Integer.valueOf(this.rowPositionTextField.getText());
    		int iColumn = Integer.valueOf(this.columnPositionTextField.getText());
    		if(iColumn < this.model.getLists().size() && iRow < this.model.getLists().get(iColumn).getCards().size())
    		{
    			this.model.getLists().get(iColumn).getCards().get(iRow).updateName(this.nameTextField.getText());
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
