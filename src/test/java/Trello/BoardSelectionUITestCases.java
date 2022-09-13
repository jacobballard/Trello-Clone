package Trello;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.rmi.RemoteException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import View.BoardSelectionController;
import View.BoardSelectionView;
import View.LoginController;
import View.LoginView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class BoardSelectionUITestCases
{
	
	static TrelloServer server;
	
	static TrelloClient client;
	
	BoardSelectionController controller;
	
	@BeforeAll
	static void startServerAndClient()
	{
		System.out.println("working?");
		try
		{
			server = new TrelloServer(2013);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client = new TrelloClient(2013);
	}
	
	
	@Start
	private void start(Stage stage) {
		
		
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(BoardSelectionView.class.getResource("/View/boardSelection.fxml"));
		
		BorderPane pane = null;
		try
		{
			pane = loader.load();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BoardSelectionController cont = loader.getController();
		
		this.controller = cont;
		
		client.checkUsernamePassword("jacob", "ballard");
		
		cont.setModel((UserConcrete) client.getLoggedInUser(), client);

		Scene s = new Scene(pane);
		
		stage.setScene(s);
		
		stage.show();
		
	}
	
	
	@Test
	public void testCreateButtonWorks(FxRobot robot)
	{
		robot.clickOn("#createButton");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(this.controller.loadTappedCount, 1);
		
	}
	
//	@Test
//	public void testLoadButtonWorks(FxRobot robot)
//	{
//		robot.clickOn("#loadButton");
//		assertEquals(this.controller.loadTappedCount, 1);
//	}
//	
}
