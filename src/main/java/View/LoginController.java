package View;

import Trello.TrelloClient;
import Trello.User;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;


public class LoginController
{
	
	TrelloClient client;
	
	public boolean isLoggedIn;
	
	@FXML
    private ChoiceBox<String> serverSelectionDropdown;

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    void loginTapped(ActionEvent event) {
    	errorLabel.setOpacity(0);
    	
    	if(usernameInput.getText() != null && !usernameInput.getText().isEmpty() && 
    	   passwordInput.getText() != null && !passwordInput.getText().isEmpty())
    	{
    		
    		if(serverSelectionDropdown.getValue().equals("localhost"))
    		{
    			User userLoggedIn = client.checkUsernamePassword(usernameInput.getText(), passwordInput.getText());
        		
        		
        		if(userLoggedIn != null)
        		{
        			
        			client.hideStage();
        			try
					{
        				this.isLoggedIn = true;
						client.loadNextStep("Selector", null);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
        			
        			
        		} else {
        			errorLabel.setOpacity(100);
        		}
    		}
    		
    	}
    }
    
//    LoginController()
//    {
//    	
//    	
//    }
    
    public void setup()
    {
    	errorLabel.setOpacity(0);
    	
    	this.isLoggedIn = false;
//    	
    	
    	serverSelectionDropdown.getItems().add("localhost");
    }
//	
	public void setClient(TrelloClient client) 
	{
		this.client = client;	
		
	}

}
