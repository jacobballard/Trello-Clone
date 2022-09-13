package Trello;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.rmi.RemoteException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import View.BoardController;
import View.BoardSelectionController;
import View.BoardSelectionView;
import View.BoardView;
import View.LoginController;
import View.LoginView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class BoardUITestCases
{
	
	static TrelloServer server;
	
	static TrelloClient client;
	
	BoardController controller;
	
	BoardConcrete board;
	
	@BeforeAll
	static void startServerAndClient()
	{
		
		try
		{
			server = new TrelloServer(2014);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client = new TrelloClient(2014);
	}
	
	
	@Start
	private void start(Stage stage) {
		
		
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(BoardView.class.getResource("/View/board_view.fxml"));
		
		BorderPane pane = null;
		try
		{
			pane = loader.load();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BoardController cont = loader.getController();
		this.controller = cont;
		StackPane sp = new StackPane(pane);
		
		this.board = new BoardConcrete("Jacob", new UserConcrete());
		
		cont.setModel(this.board, stage, sp, pane, client);
		
		cont.generateBoard();
		
		System.out.println("starting");
		System.out.println(cont.getModel().getName());
		
		
		Scene s = new Scene(sp);
		
		stage.setScene(s);
		
		stage.show();
		
	}
	
	
	@Test
	public void testRenameBoardInputWorks(FxRobot robot)
	{
		
		
		assertEquals(this.controller.getModel().getBoardName().equals("Jacob"), true);
		robot.clickOn("#fileMenu");
		robot.clickOn("#renameBoardMenuItem");
		FxAssert.verifyThat("#inputMessageLabel", LabeledMatchers.hasText("Enter the name:"));
		robot.clickOn("#inputTextField").write("L");
		robot.clickOn("#inputButton");
		
		assertEquals(this.controller.getModel().getBoardName().equals("L"), true);
		
	}
	
	@Test
	public void testListsWork(FxRobot robot)
	{
		
		
		assertEquals(this.controller.getModel().addList("test"), true);
		
		List imRemoving = this.controller.getModel().getLists().get(0);
		
		
		
		assertEquals(this.controller.getModel().getLists().remove(0), imRemoving);
		
		robot.clickOn("#listMenu");
		robot.clickOn("#addListMenuItem");
		assertEquals(this.controller.getModel().getLists().size(), 1);
		
		robot.clickOn("#listMenu");
		robot.clickOn("#deleteListMenuItem");
		robot.clickOn("#columnPositionTextField").write("0");
		robot.clickOn("#inputButton");
		
		assertEquals(this.controller.getModel().getLists().size(), 0);
		
		robot.clickOn("#listMenu");
		robot.clickOn("#addListMenuItem");
		
		String oldListName = this.controller.getModel().getLists().get(0).getName();
		robot.clickOn("#listMenu");
		robot.clickOn("#renameListMenuItem");
		robot.clickOn("#positionTextField").write("0");
		robot.clickOn("#nameTextField").write("newName");
		robot.clickOn("#inputButton");
		
		assertEquals(this.controller.getModel().getLists().get(0).getName().equals(oldListName), false);
		assertEquals(this.controller.getModel().getLists().get(0).getName().equals("newName"), true);
		
	}
	
	@Test
	public void testCardsWork(FxRobot robot)
	{
		assertEquals(this.controller.getModel().getLists().size(), 0);
		robot.clickOn("#cardMenu");
		robot.clickOn("#addCardMenuItem");
		
		assertEquals(this.controller.getModel().getLists().size(), 1);
		
		String oldCardName = this.controller.getModel().getLists().get(0).getCards().get(0).getName();
		
		robot.clickOn("#cardMenu");
		robot.clickOn("#renameCardMenuItem");
		
		robot.clickOn("#rowPositionTextField").write("0");
		robot.clickOn("#columnPositionTextField").write("0");
		robot.clickOn("#nameTextField").write("jacob");
		robot.clickOn("#inputButton");
		
		assertEquals(this.controller.getModel().getLists().get(0).getCards().get(0).getName().equals(oldCardName), false);
		
		
		//add two cards to a new adjacent list
		robot.clickOn("#listMenu");
		robot.clickOn("#addListMenuItem");
		robot.clickOn("#cardMenu");
		robot.clickOn("#addCardMenuItem");
		robot.clickOn("#cardMenu");
		robot.clickOn("#addCardMenuItem");
		
		//rename card in second list at second position 
		robot.clickOn("#cardMenu");
		robot.clickOn("#renameCardMenuItem");
		robot.clickOn("#rowPositionTextField").write("1");
		robot.clickOn("#columnPositionTextField").write("1");
		robot.clickOn("#nameTextField").write("ballard");
		robot.clickOn("#inputButton");
		
		assertEquals(this.controller.getModel().getLists().get(1).getCards().get(1).getName().equals("ballard"), true);
		
		
		//not properly working
//		//reorder card from 0,0 to 1,1
//		robot.clickOn("#cardMenu");
//		robot.clickOn("#reorderCardMenuItem");
//		robot.clickOn("#oldRowPositionTextField").write("0");
//		robot.clickOn("#oldColumnPositionTextField").write("0");
//		robot.clickOn("#newRowPositionTextField1").write("1");
//		robot.clickOn("#newColumnPositionTextField1").write("1");
//		robot.clickOn("#inputButton");
//		
//		System.out.println(this.controller.getModel().getLists().get(1).getCards().get(1).getName() + "1");
//		
//		assertEquals(this.controller.getModel().getLists().get(1).getCards().get(1).getName().equals("jacob"), true);
//		
		
		
		
		
	}

}
