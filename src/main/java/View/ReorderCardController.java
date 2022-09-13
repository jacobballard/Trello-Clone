package View;

import Trello.BoardConcrete;
import Trello.Card;
import Trello.CardConcrete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class ReorderCardController {

    @FXML
    private Label inputMessageLabel;

    @FXML
    private TextField oldRowPositionTextField;

    @FXML
    private TextField oldColumnPositionTextField;

    @FXML
    private TextField newRowPositionTextField1;

    @FXML
    private TextField newColumnPositionTextField1;

    @FXML
    private Button inputButton;
    
    BoardConcrete model;
    BoardController controller;
    BorderPane pane;

    @FXML
    void inputButtonTapped(ActionEvent event) {
    	if(!this.oldRowPositionTextField.getText().isEmpty() && this.oldRowPositionTextField.getText() != null &&
        	!this.oldColumnPositionTextField.getText().isEmpty() && this.oldColumnPositionTextField.getText() != null &&
        	!this.newColumnPositionTextField1.getText().isEmpty() && this.newColumnPositionTextField1.getText() != null &&
        	!this.newRowPositionTextField1.getText().isEmpty() && this.newRowPositionTextField1.getText() != null)
        	{
        		int iOldRow = Integer.valueOf(this.oldRowPositionTextField.getText());
        		int iOldColumn = Integer.valueOf(this.oldColumnPositionTextField.getText());
        		int iNewRow = Integer.valueOf(this.newRowPositionTextField1.getText());
        		int iNewColumn = Integer.valueOf(this.newColumnPositionTextField1.getText());
        		if(iOldColumn < this.model.getLists().size() && iOldRow < this.model.getLists().get(iOldColumn).getCards().size())
        		{
        			//this.model.getLists().get(iColumn).removeCard(this.model.getLists().get(iColumn).getCards().get(iRow));
            		//this.controller.editorDoneDisplaying(pane);
        			if(iNewColumn < this.model.getLists().size() && iNewRow < this.model.getLists().get(iOldColumn).getCards().size())
        			{
        				Card toMove = this.model.getLists().get(iOldColumn).getCards().get(iOldRow);
        				
        				this.model.getLists().get(iNewColumn).getCards().add(iNewRow, toMove);
        				
        				this.model.getLists().get(iOldColumn).getCards().remove(iOldRow);
        				
        				this.controller.editorDoneDisplaying(pane);
        			}
        			
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


