package View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Trello.Board;
import Trello.TrelloClient;
import Trello.UserConcrete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class BoardSelectionController {

	UserConcrete model;
	
	TrelloClient client;
	
	public int loadTappedCount;
	
	public int createTappedCount;
	
	@FXML
    private ChoiceBox<String> boardSelectionDropdown;

    @FXML
    private Button loadButton;

    @FXML
    private Button createButton;

    @FXML
    void createTapped(ActionEvent event) {
    	System.out.println("created tapped?");
    	this.createTappedCount++;
    	
    	System.out.println("CREATE TAPPED!!!!---------");
    	//Used this to make default board name the current time and date
    	//https://www.javatpoint.com/java-get-current-date
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    	LocalDateTime now = LocalDateTime.now();  
    	System.out.println(dtf.format(now));  
    	
    	
    	Board b = client.createBoard("Created " + dtf.format(now), model);
    	
    	System.out.println("Created for: " + model.getUsername());
    	
    	//show the board to the user
    	client.hideStage();
    	try
		{
			client.loadNextStep("Board", b);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
    	
    	
    }

    @FXML
    void loadTapped(ActionEvent event) {
    	
    	this.loadTappedCount++;
    	
    	for(Board b : model.getBoards())
    	{
    		if(b.getName().equals(boardSelectionDropdown.getValue()))
    		{
    			
    			client.hideStage();
    			try
				{
					client.loadNextStep("Board", b);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
    		}
    	}
    	
    }
    
    public void setModel(UserConcrete model, TrelloClient client)
    {
    	this.createTappedCount = 0;
    	
    	this.loadTappedCount = 0;
    	this.model = model;
    	System.out.println("model");
    	this.client = client;
    	System.out.println("BoardSelectionController SetModel");
    	for(Board b : model.getBoards())
    	{
    		System.out.println("board name: " + b.getName());
    		boardSelectionDropdown.getItems().add(b.getName());
    	}
    }

}
