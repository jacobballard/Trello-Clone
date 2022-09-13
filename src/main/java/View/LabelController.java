package View;

import java.util.ArrayList;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class LabelController {

    @FXML
    private TextField deleteLabelTextField;

    @FXML
    private Button deleteLabelButton;

    @FXML
    private Button addLabelButton;

    @FXML
    private TextField addLabelTextField;

    
    Set<String> model;
    CardController cardController;
    BorderPane editor;

    @FXML
    void addLabelButtonTapped(ActionEvent event) {
    	if(!this.addLabelTextField.getText().isEmpty() && this.addLabelTextField.getText() != null)
    	{
    		model.add(this.addLabelTextField.getText());
    	}
    }

    @FXML
    void deleteLabelButtonTapped(ActionEvent event) {
    	if(!this.deleteLabelTextField.getText().isEmpty() && this.deleteLabelTextField.getText() != null)
    	{
    		model.remove(this.deleteLabelTextField.getText());
    		System.out.println("removed?");
    		model.forEach((e)-> {
    			System.out.println(e);
    		});
    	}
    }


    @FXML
    void exitEditorButtonTapped(ActionEvent event) {
    	this.cardController.editorDoneDisplaying(editor);
    }
    
    void setModel(Set<String> labels, CardController cc, BorderPane editor)
    {
    	this.model = labels;
    	this.cardController = cc;
    	this.editor = editor;
    	
    }

}
