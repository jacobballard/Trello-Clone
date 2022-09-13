////https://stackoverflow.com/questions/18929161/how-to-move-items-with-in-vboxchange-order-by-dragging-in-javafx
////https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
////https://docs.oracle.com/javafx/2/drag_drop/HelloDragAndDrop.java.html
////https://examples.javacodegeeks.com/desktop-java/javafx/event-javafx/javafx-drag-drop-example/#:~:text=This%20is%20a%20JavaFX%20Drag,a%20Scene%20or%20a%20Node.
////Drag and drop was learned online through a couple articles.
//
//
//package View;
//
//import java.util.ArrayList;
//
//import Trello.Card;
//import Trello.CardConcrete;
//import Trello.UserConcrete;
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
//import javafx.geometry.Insets;
//import javafx.geometry.Orientation;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
//import javafx.scene.control.SelectionMode;
//import javafx.scene.input.ClipboardContent;
//import javafx.scene.input.DataFormat;
//import javafx.scene.input.DragEvent;
//import javafx.scene.input.Dragboard;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.input.TransferMode;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.CornerRadii;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.RowConstraints;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//
//public class DragAndDrop extends Application
//{
//	Card[][] card_storage;
//	Node initial_pos;
//	@Override
//	public void start(Stage stage) throws Exception
//	{
//		stage.setTitle("Hello Drag and Drop!");
//		
//		System.out.println("starting");
//		
//		FXMLLoader loader = new FXMLLoader();
//		
//		loader.setLocation(DragAndDrop.class.getResource("/View/board_view.fxml"));
//		
//		BorderPane pane = loader.load();
//		
//		HBox hbox = new HBox();
//		
//		GridPane boardView = new GridPane();
//		boardView.setGridLinesVisible(false);
//		
//		boardView.setAlignment(Pos.CENTER);
//		
//		boardView.setHgap(20);
//		boardView.setVgap(5);
////		boardView.setPadding(25, 25, 25, 25);
//		boardView.setPadding(new Insets(25, 25, 25, 25));
////		boardView.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
//		
////		boardView.setPrefHeight(200);
////		boardView.setPrefWidth(400);
//		
//		DataFormat card_format = new DataFormat("cardformat");
//		
//		int rows = 10;
//		int columns = 5;
//		for (int i = 0; i < columns; i++) {
//            ColumnConstraints colConst = new ColumnConstraints();
//            colConst.setPercentWidth(100.0 / columns);
//            boardView.getColumnConstraints().add(colConst);
//        }
//        for (int i = 0; i < rows; i++) {
//            RowConstraints rowConst = new RowConstraints();
//            rowConst.setPercentHeight(100.0 / rows);
//            boardView.getRowConstraints().add(rowConst);         
//        }
//        
//        
//		
//		Pane p;
//		
//		card_storage = new Card[5][10];
////		pane.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
//		System.out.println("here");
//		for(int i = 0; i < 5; i++)
//		{
//			
//			for(int j = 0; j < 10; j++)
//			{
//				p = new Pane();
//				
//				final Pane ref = p;
//				System.out.println("new pane");
//				if(j < 3)
//				{
//					System.out.println("pre?");
//					card_storage[i][j] = new CardConcrete();
//					System.out.println("new cardconcrete");
//					card_storage[i][j].updateName("PINK");// + Integer.toString(i) + Integer.toString(j));
//					System.out.println("pink");
//					System.out.println("new card_concrete");
//					p.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
//				} else 
//				{
//					card_storage[i][j] = new CardConcrete();
//					card_storage[i][j].updateName("BLACK"); //+ Integer.toString(i) + Integer.toString(j));
//					p.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
//				}
////				p.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
//				p.setOnDragDetected(new EventHandler <MouseEvent>() 
//				{
//					public void handle(MouseEvent event) 
//					{
////						if(lv.getSelectionModel().getSelectedIndices().size() == 0)
////						{
////							event.consume();
////							return;
////						}
//						
////						
//						
//						System.out.println("drag detected");
//						Node node = event.getPickResult().getIntersectedNode();
//						
//						initial_pos = node;
//						Integer column_index = GridPane.getColumnIndex(node);
//						Integer row_index = GridPane.getRowIndex(node);
//						
//						int x = column_index == null ? 0 : column_index;
//				        int y = row_index == null ? 0 : row_index;
//				        
//						if(node != boardView)
//						{
//							
//							Dragboard dragboard = boardView.startDragAndDrop(TransferMode.MOVE);
//							
//							ClipboardContent content = new ClipboardContent();
//							
//							content.put(card_format, card_storage[x][y]);
//							
//							dragboard.setContent(content);
//							event.consume();
//					
//						} else 
//						{
//							event.consume();
//						}
//						
//						
//					}
//				});
//				
//				p.setOnDragOver(new EventHandler <DragEvent>() 
//				{
//					@Override
//					public void handle(DragEvent event) 
//					{
//						Dragboard dragboard = event.getDragboard();
//						if(dragboard.hasContent(card_format) && event.getGestureSource() != ref)
//						{
//							
////							System.out.println("drag over");
//							event.acceptTransferModes(TransferMode.MOVE);
//						}
//						
//						event.consume();
//					}
//				});
//				
//				p.setOnDragEntered(new EventHandler<DragEvent>() {
//			        public void handle(DragEvent event) {
//			            //The drag-and-drop gesture entered the target
//			            //show the user that it is an actual gesture target
//			            if(event.getGestureSource() != boardView && event.getDragboard().hasContent(card_format)){
////			                source.setVisible(false);
////			                target.setOpacity(0.7);
//			                System.out.println("Drag entered");
//			            }
//			            event.consume();
//			        }
//			    });
//				
//				p.setOnDragDropped(new EventHandler <DragEvent>()
//				{
//					
//					@SuppressWarnings("unchecked")
//					@Override
//					public void handle(DragEvent event)
//					{
//						boolean dragCompleted = false;
//						
//						Dragboard dragboard = event.getDragboard();
//						
//						Node node = event.getPickResult().getIntersectedNode();
//						
//						Pane pe = (Pane)node;
//						
////						pe.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
//						
//						
//						
//						if(dragboard.hasContent(card_format))
//						{
//							
//							Integer column_index = GridPane.getColumnIndex(node);
//							Integer row_index = GridPane.getRowIndex(node);
//							
//							int x = column_index == null ? 0 : column_index;
//					        int y = row_index == null ? 0 : row_index;
//					        
//					        System.out.println("located: " + Integer.toString(x) + Integer.toString(y));
//						
////							ArrayList<Card> list = (ArrayList<Card>) dragboard.getContent(card_format);
////							lv.getItems().addAll(list);
//					        
//					        
//					        //column, row
////					        boardView.add(node, x, y);
//					        
//					        //column, row
//					        moveCardsDown(x, y, boardView, (Pane) node);
//					        
////					        boardView.add(pe, x, y);
//					  
//							
//							dragCompleted = true;
//						}
//						
//						event.setDropCompleted(dragCompleted);
//						event.consume();
//						
//					}
//			
//				});
//				
//				p.setOnDragDone(new EventHandler <DragEvent>()
//				{
//
//					@Override
//					public void handle(DragEvent event)
//					{
//						ArrayList<Card> selectedList = new ArrayList<>();
//						
////						for(Card c : lv.getSelectionModel().getSelectedItems())
////						{
////							selectedList.add(c);
////						}
//						
////						lv.getSelectionModel().clearSelection();
//						
////						lv.getItems().removeAll(selectedList);
//						initial_pos.setVisible(false);
//					}
//					
//				});
//				
//				System.out.println("past if");
//				Label l = new Label(card_storage[i][j].getName());
//				l.setAlignment(Pos.CENTER);
//				l.setTextFill(Color.web("#0076a3"));
//				
//				
//				p.getChildren().add(l);
//				
//				boardView.add(p, i, j, 1, 1);
//			}
//		}
//		hbox.setPrefWidth(600);
//		hbox.setPrefHeight(300);
//		
//		loader.setLocation(DragAndDrop.class.getResource("View/card_view.fxml"));
//		
////		addCardsToBoard(pane);
//		
//		
//		
//		
//		
//		for(int i = 0; i < 5; i++)
//		{
//			
////			ListView<Pane> lv = new ListView<>();
//			
//			ListView<Card> lv = new ListView<>();
//			
//			
//			
//			lv.setOrientation(Orientation.VERTICAL);
//			lv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//			
//			ObservableList<Card> cards = FXCollections.<Card>observableArrayList();
//			
//			//Pane card;
//			
//			for(int j = 0; j < 5; j++)
//			{
////				cards.add(loader.load());
//				cards.add(new CardConcrete());
//			}
//			
//			for(Card c : cards)
//			{
//				System.out.println(c.getName() + c.hashCode());
//			}
//			
////			for(Pane p : cards)
////			{
////				if(i % 2 == 0)
////				{
////					p.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
////				} else {
////					p.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
////				}
////			}
//			
//			lv.getItems().addAll(cards);
//			
//			hbox.getChildren().add(lv);
//			
//				//this will be unique id associated w/ card.
////				card.setId(Integer.toString(i));
//				//add cards to ListView
////				v.getChildren().add(card);
//				
//				
//				
////			hbox.getChildren().add(lv);
////			}
//		}
//		
//		
//		
//		
////		pane.getChildren().add(hbox);
//		
////		pane.getChildren().add(boardView);
//		
//		pane.setCenter(boardView);
//		
////		pane.setCenter(boardView);
//		
////		pane.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
//		
//		Scene scene = new Scene(pane, 600, 400);
//		stage.setScene(scene);
//		
//		stage.show();
//
//		
//	}
//	
//	void moveCardsDown(int column, int row, GridPane boardView, Pane newCard)
//	{
//		//internally shift cards
//		
//		//shift views
//		
//		System.out.println("moveCardsDown");
//		
//		//Used this link to figure how to get the max row inside a lambda.
//		//https://stackoverflow.com/questions/28790784/java-8-preferred-way-to-count-iterations-of-a-lambda
//		final int[] maxRow = {0};
//		boardView.getChildren().forEach((n) -> {
//			if(GridPane.getRowIndex(n) > maxRow[0])
//			{
//				maxRow[0] = GridPane.getRowIndex(n);
//			}
//		});
//		
//		Node[] nodes_to_shift = new Node[maxRow[0] + 1];
//	
//		boardView.getChildren().forEach((n) -> {
//			System.out.println("foreach");
////			Pane card = (Pane) n;
//			if(GridPane.getColumnIndex(n) == column)
//			{
//				if(GridPane.getRowIndex(n) >= row)
//				{
//					if(GridPane.getRowIndex(n) == maxRow[0] - 1)
//					{
//						boardView.addRow(maxRow[0] + 1);
//					} 
//					nodes_to_shift[GridPane.getRowIndex(n) - row] = n;
//					
////					if(boardView.getChildren().remove(n)) {
////						System.out.println("true");
////					}
////					
//					
//					
//				}
//			}
//		});
//		
//		System.out.println("add");
//		
////		boardView.add(newCard, column, row);
//		Pane p = new Pane();
//		p.setBackground(((Pane)initial_pos).getBackground());
//		p.setOnDragDetected(((Pane)initial_pos).getOnDragDetected());
//		p.setOnDragOver(((Pane)initial_pos).getOnDragOver());
//		p.setOnDragEntered(((Pane)initial_pos).getOnDragEntered());
//		p.setOnDragDropped(((Pane)initial_pos).getOnDragDropped());
//		boardView.add(p, column, row);
//		
//		System.out.println("add1?");
//		
//		for(int i = 0; i < nodes_to_shift.length; i++)
//		{
//			if(nodes_to_shift[i] != null)
//			{
//				boardView.getChildren().remove(nodes_to_shift[i]);
//				p = new Pane();
//				p.setOnDragDetected(nodes_to_shift[i].getOnDragDetected());
//				p.setOnDragOver(nodes_to_shift[i].getOnDragOver());
//				p.setOnDragEntered(nodes_to_shift[i].getOnDragEntered());
//				p.setOnDragDropped(nodes_to_shift[i].getOnDragDropped());
//				p.setBackground(((Pane)nodes_to_shift[i]).getBackground());
//				boardView.add(p, column, row + i + 1);
//				nodes_to_shift[i].setVisible(true);
//			}
//		}
//		
//		
//		int iRow = GridPane.getRowIndex(initial_pos);
//		int iColumn = GridPane.getColumnIndex(initial_pos);
//		
//		Node[] nodes = new Node[maxRow[0]];
//		boardView.getChildren().remove(initial_pos);
//		System.out.println(Integer.toString(iRow) + Integer.toString(iColumn));
//		System.out.println("remote init?");
//
//		boardView.getChildren().forEach((n) -> {
//			if(GridPane.getColumnIndex(n) == iColumn)
//			{
//				if(GridPane.getRowIndex(n) >= iRow)
//				{
//					nodes[GridPane.getRowIndex(n) - iRow] = n;
//					
//					System.out.println("Shift down??");
//				}
//			}
//		});
//		
//		
//		for(int i = 0; i < nodes.length; i++)
//		{
//			if(nodes[i] != null)
//			{
//				boardView.getChildren().remove(nodes[i]);
//				
//				//p is all the cards after the deleted one in the column
//				p = new Pane();
//				p.setOnDragDetected(nodes[i].getOnDragDetected());
//				p.setOnDragOver(nodes[i].getOnDragOver());
//				p.setOnDragEntered(nodes[i].getOnDragEntered());
//				p.setOnDragDropped(nodes[i].getOnDragDropped());
//				p.setBackground(((Pane)nodes[i]).getBackground());
//				boardView.add(p, iColumn, iRow + i - 1);
//			}
//		}
//		
//		
//		System.out.println("added?");
//	}
//	
//	Card getAssociatedData(Node n)
//	{
//		return null;
//	}
//	
//	
//	
//	public static void main(String[] args)
//	{
//		
//		launch(args);
//		
//	}
//	
//	
//	
//}
