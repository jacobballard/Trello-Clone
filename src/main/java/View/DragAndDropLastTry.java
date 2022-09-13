package View;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.IsInstanceOf;

import Trello.Card;
import Trello.CardConcrete;
import Trello.UserConcrete;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DragAndDropLastTry extends Application
{
	
	private static final ObservableList<CardConcrete> cards = FXCollections.observableArrayList(
				new CardConcrete("Jacob", new UserConcrete()), new CardConcrete(), new CardConcrete(), new CardConcrete(), new CardConcrete());
	
	DataFormat card_format = new DataFormat("cardmat");
			
	
	@Override
	public void start(Stage stage) throws Exception
	{
		ListView<CardConcrete> cardList = new ListView<>(cards);
		cardList.setCellFactory(param -> new CardCell());
		
		
		
		VBox layout = new VBox(cardList);
        layout.setPadding(new Insets(10));

        stage.setScene(new Scene(layout));
        stage.show();
	}
	
	
	
	

	private class CardCell extends ListCell<CardConcrete>
	{
		private CardConcrete associated_card;
		
		
		//https://stackoverflow.com/questions/32708250/how-snapshot-the-entire-scene-in-javafx
		private WritableImage associated_image;
		
		public CardCell()
		{
			ListCell thisCell = this;
			
			
			associated_image = thisCell.snapshot(new SnapshotParameters(), null);
			
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			
			
			
			setOnDragDetected(event -> {
	            if (getItem() == null) {
	                return;
	            }
	            
	            System.out.println("drag detected/");
	
	            ObservableList<CardConcrete> items = getListView().getItems();
	
	            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
	            ClipboardContent content = new ClipboardContent();
//	            content.putString(getItem())
	            if(getItem() instanceof CardConcrete) {
	            	System.out.println("is card concrete");
	            }
	            content.put(card_format, getItem());
//	            dragboard.setDragView(associated_image);
	            dragboard.setContent(content);
	            
	            event.consume();
	        });
	
	        setOnDragOver(event -> {
	            if (event.getGestureSource() != thisCell &&
	                   event.getDragboard().hasContent(card_format)) {
	                event.acceptTransferModes(TransferMode.MOVE);
	            }
	            System.out.println("drag over/");
	            event.consume();
	        });
	
	        setOnDragEntered(event -> {
	            if (event.getGestureSource() != thisCell &&
	                    event.getDragboard().hasContent(card_format)) {
	                setOpacity(0.3);
	            }
	            
	            System.out.println("drag entered/");
	        });
	
	        setOnDragExited(event -> {
	            if (event.getGestureSource() != thisCell &&
	                    event.getDragboard().hasContent(card_format)) {
	                setOpacity(1);
	            }
	        });
	
	        setOnDragDropped(event -> {
	        	System.out.println("drag dropped?");
	            if (getItem() == null) {
	            	System.out.println("drag dropped null?");
	                return;
	            }
	            
	            
	
	            Dragboard db = event.getDragboard();
	            boolean success = false;
	
	            if (db.hasContent(card_format)) {
	                ObservableList<CardConcrete> items = getListView().getItems();
	                int draggedIdx = items.indexOf(db.getContent(card_format));
	                
	                
	                int thisIdx = items.indexOf(getItem());
	                
	                System.out.println("draggedIdx: " + Integer.toString(draggedIdx)+ Integer.toString(thisIdx));
	                
	                CardConcrete temp = cards.get(draggedIdx);
	                System.out.println(temp.getCardName() + " card name");
	                cards.set(draggedIdx, cards.get(thisIdx));
	                cards.set(thisIdx, temp);
	
	                items.set(draggedIdx, getItem());
	                items.set(thisIdx, (CardConcrete) db.getContent(card_format));
	
	                List<CardConcrete> itemscopy = new ArrayList<>(getListView().getItems());
	                getListView().getItems().setAll(itemscopy);
	
	                success = true;
	            }
	            event.setDropCompleted(success);
	
	            event.consume();
	        });
	
	        setOnDragDone(DragEvent::consume);
	    }
	
		@Override
	    protected void updateItem(CardConcrete item, boolean empty) {
	        super.updateItem(item, empty);
	
	        if (empty || item == null) {
	            setGraphic(null);
	        } else {
//	            imageView.setImage(
//	                birdImages.get(
//	                    getListView().getItems().indexOf(item)
////	                )
//	            );
	        	
	        	associated_card = cards.get(getListView().getItems().indexOf(item));
	            
	            Pane p = new Pane();
	            
	            Label l = new Label();
	            
	            l.setText("Update Item" + associated_card.getCardName());
	            
	            p.getChildren().add(l);
	            
	            
	            //set the card graphic
	            setGraphic(p);
	            
	        }
	    }
		
		
		
	}
}