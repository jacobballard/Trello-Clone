package Trello;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import View.BoardController;
import View.BoardView;
import View.LoginController;
import View.LoginView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class LoginUITestCases
{
	
		
	LoginController controller;
	
	static TrelloServer server;
	
	static TrelloClient client;
	
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
		
		System.out.println("is this thing on?");
		client.setStage(stage);
		
		
		FXMLLoader loader = new FXMLLoader();
		
		
		
		loader.setLocation(LoginView.class.getResource("/View/loginView.fxml"));
		
		
		
		
		AnchorPane pane = null;
		try
		{
			pane = loader.load();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

		LoginController cont = loader.getController();
		
		this.controller = cont;
		
		cont.setup();
		
		
		cont.setClient(client);
		
		Scene s = new Scene(pane);
		
		stage.setScene(s);
		stage.show();
		
	}
	
	
	@Test
	public void testLoginWorks(FxRobot robot)
	{
		System.out.println("Test?");
		
		robot.clickOn("#serverSelectionDropdown");
		robot.type(KeyCode.DOWN);
		robot.type(KeyCode.ENTER);
		robot.clickOn("#usernameInput").write("bacob");
		robot.clickOn("#passwordInput").write("ballard");
		robot.clickOn("#loginButton");
		
		assertEquals(this.controller.isLoggedIn, false);
		
		for(int i = 0; i < 7; i++)
		{
			robot.clickOn("#usernameInput").type(KeyCode.BACK_SPACE);
			robot.clickOn("#passwordInput").type(KeyCode.BACK_SPACE);
		}
		
		
		robot.clickOn("#serverSelectionDropdown");
		robot.type(KeyCode.DOWN);
		robot.type(KeyCode.ENTER);
		robot.clickOn("#usernameInput").write("jacob");
		robot.clickOn("#passwordInput").write("ballard");
		robot.clickOn("#loginButton");
		
		assertEquals(this.controller.isLoggedIn, true);
		
	}
	
	

	

}
