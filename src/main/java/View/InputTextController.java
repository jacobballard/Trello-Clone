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

public class InputTextController {

    @FXML
    private Label inputMessageLabel;

    @FXML
    private TextField inputTextField;

    @FXML
    private Button inputButton;
    Object model;
    Object controller;
    BorderPane pane;
    String desc;

    //this is lazy but gets the job done without setting up a new pattern right now.
    @FXML
    void inputButtonTapped(ActionEvent event) {
    	if(!this.inputTextField.getText().isEmpty() && this.inputTextField.getText() != null)
    	{
    		if(desc.equals("edit_card_title"))
        	{
    			CardController cont = (CardController) this.controller;
    			CardConcrete card = (CardConcrete) this.model;
    			card.setCardName(this.inputTextField.getText());
    			cont.editorDoneDisplaying(this.pane);
        	} else if(desc.equals("edit_card_desc"))
        	{
    			CardController cont = (CardController) this.controller;
    			CardConcrete card = (CardConcrete) this.model;
    			for(Component c : card.getComponents())
    			{
    				if(c instanceof ComponentDescription)
    				{
    					((ComponentDescription)c).setDescription(this.inputTextField.getText());
    				}
    			}
    			cont.editorDoneDisplaying(this.pane);
        	} else if(desc.equals("edit_board_name"))
        	{
    			BoardController cont = (BoardController) this.controller;
    			BoardConcrete board = (BoardConcrete) this.model;
    			board.setBoardName(this.inputTextField.getText());
    			System.out.println("this.board test");
    			System.out.println(board);
//    			card.setCardName(this.inputTextField.getText());
    			cont.editorDoneDisplaying(this.pane);
        	} else if(desc.equals("remove_board_member"))
        	{
    			BoardController cont = (BoardController) this.controller;
    			BoardConcrete board = (BoardConcrete) this.model;
//    			board.setBoardName(this.inputTextField.getText());
    			board.removeMember(TrelloClient.getInstance().getLoggedInUser(), this.inputTextField.getText());
    			
//    			card.setCardName(this.inputTextField.getText());
    			cont.editorDoneDisplaying(this.pane);
        	} else if(desc.equals("add_board_member"))
        	{
    			BoardController cont = (BoardController) this.controller;
    			BoardConcrete board = (BoardConcrete) this.model;
//    			board.setBoardName(this.inputTextField.getText());
    			board.addMember(TrelloClient.getInstance().getLoggedInUser(), this.inputTextField.getText());
    			
    			
//    			card.setCardName(this.inputTextField.getText());
    			cont.editorDoneDisplaying(this.pane);
        	} 
    	}
    }
    
    
    
    void setModel(Object model, Object controller, BorderPane pane, String desc)
    {
    	this.model = model;
    	this.controller = controller;
    	this.pane = pane;
    	this.desc = desc;
    	if(desc.equals("edit_card_title"))
    	{
    		this.inputMessageLabel.setText("Enter the title:");
    	} else if(desc.equals("edit_card_desc"))
    	{
    		this.inputMessageLabel.setText("Enter the description:");
    	} else if(desc.equals("edit_board_name"))
    	{
    		this.inputMessageLabel.setText("Enter the name:");
    	} else if(desc.equals("add_board_member"))
    	{
    		this.inputMessageLabel.setText("Enter the username:");
    	} else if(desc.equals("remove_board_member"))
    	{
    		this.inputMessageLabel.setText("Enter the username:");
    	}
    	
    	
    	
    }
    
    

}
