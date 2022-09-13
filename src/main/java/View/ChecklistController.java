package View;

import Trello.ComponentChecklist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class ChecklistController {

    @FXML
    private Button addCheckboxButton;

    @FXML
    private TextField editCheckboxNameField;

    @FXML
    private RadioButton editCheckboxSelectionRadioButton;

    @FXML
    private TextField editCheckboxPositioning;

    @FXML
    private Button editCheckboxButton;

    @FXML
    private TextField deleteCheckboxPositionField;

    @FXML
    private Button deleteCheckboxButton;

    @FXML
    private Button exitEditorButton;
    
    ComponentChecklist model;
    
    CardController cardController;
    
    BorderPane editor;
    
    void setModel(ComponentChecklist cl, CardController cc, BorderPane editor)
    {
    	this.model = cl;
    	this.cardController = cc;
    	this.editor = editor;
    }

    @FXML
    void addCheckboxTapped(ActionEvent event) {
    	model.getChecklist().add("new checkbox");
    	model.getIsSelected().add(false);
    	
    }

    @FXML
    void deleteCheckboxButtonTapped(ActionEvent event) {
    	if(!this.deleteCheckboxPositionField.getText().isEmpty() && this.deleteCheckboxPositionField.getText() != null)
    	{
    		//https://stackoverflow.com/questions/16908029/converting-string-to-integers-the-safe-way
    		int i = Integer.valueOf(this.deleteCheckboxPositionField.getText());
    		if(i >= model.getChecklist().size())
    		{
    			//too big
    		} else {
    			model.getChecklist().remove(i);
    			model.getIsSelected().remove(i);
    		}
    	}
    }

    @FXML
    void editCheckboxButtonTapped(ActionEvent event) {
    	if(!this.editCheckboxNameField.getText().isEmpty() && this.editCheckboxNameField.getText() != null &&
    	   !this.editCheckboxPositioning.getText().isEmpty() && this.editCheckboxPositioning.getText() != null)
    	{
    		System.out.println("edit checkbox");
    		int i = Integer.valueOf(this.editCheckboxPositioning.getText());
    		if(i >= model.getChecklist().size())
    		{
    			//too big
    		} else {
    			System.out.println("size");
    			System.out.println(Integer.toString(model.getChecklist().size()));
    			System.out.println(model.getChecklist().get(i));
    			model.getChecklist().set(i, this.editCheckboxNameField.getText());
    			
    			System.out.println(model.getChecklist().get(i));
    			
    			if(this.editCheckboxSelectionRadioButton.isSelected())
    			{
    				model.getIsSelected().set(i, true);
    			} else 
    			{
    				model.getIsSelected().set(i, false);
    			}
    		}
    	}
    }

    @FXML
    void exitEditorButtonTapped(ActionEvent event) {
    	this.cardController.editorDoneDisplaying(editor);
    }

}
