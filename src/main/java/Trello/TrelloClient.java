//Jacob Ballard
//CSC 360
//Sprint II - Implementation

package Trello;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import View.BoardSelectionView;
import View.BoardView;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class TrelloClient
{
	TrelloServerInterface si;
	
	private static TrelloClient shared = null;
	
	User logged_in_user;
	
	Stage primaryStage;
	
	public static TrelloClient getInstance()
	{
		if(shared == null)
		{
			shared = new TrelloClient(2010);
		}
		
		return shared;
	}
	
	public User getLoggedInUser()
	{
		User check = this.checkUsernamePassword(this.logged_in_user.getUsername(), this.logged_in_user.getPassword());
		
		System.out.println("getLoggedInUser" + this.logged_in_user.getUsername() + this.logged_in_user.getPassword());
		return check;
	}
	
	public TrelloClient(int port)
	{
		Registry registry;
		try
		{
			registry = LocateRegistry.getRegistry(port);
			si = (TrelloServerInterface) registry.lookup("TRELLOSERVER");
			System.out.println("Server interface?");
		} catch (RemoteException e)
		{
			e.printStackTrace();
		} catch (NotBoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public void hideStage()
	{
		primaryStage.hide();
	}
	
	public void setStage(Stage stage)
	{
		this.primaryStage = stage;
	}
	
	public void loadNextStep(String step, Board b) throws Exception
	{
		if(step.equals("Selector"))
		{
			this.primaryStage = new Stage();
			BoardSelectionView bsv = new BoardSelectionView(primaryStage, this);
		} else if(step.equals("Board"))
		{
			if(b != null)
			{
				//load specific board
				this.primaryStage = new Stage();
				BoardView bv = new BoardView(primaryStage, (BoardConcrete) b, this);
			}
		} 
	}
	
	
	public User checkUsernamePassword(String username, String password)
	{
		try
		{
			
			this.logged_in_user = si.checkUsernamePassword(username, password);
			
			
			return logged_in_user;
		} catch (RemoteException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public Board getBoard(Integer boardID)
	{
		try
		{
			return si.getBoard(boardID);
		} catch (RemoteException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public void updateBoard(Board board)
	{
		try
		{
			System.out.println("updateBoard");
			si.updateBoard(board);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
	
	public void deleteBoard(Board board)
	{
		try
		{
			si.deleteBoard(board);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
	
	public Board createBoard(String boardName, User owner)
	{
		try
		{
			return si.createBoard(boardName, owner);
		} catch (RemoteException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Board> getBoardsForUser(User u)
	{
		try
		{
			return si.getBoardsForUser(u);
		} catch (RemoteException e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
}
