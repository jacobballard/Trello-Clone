//Jacob Ballard
//CSC 360
//Sprint II - Implementation

package Trello;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TrelloServerTestCases
{
	static Registry registry;
	static TrelloServerInterface testServerInterface;
	static TrelloClient testClient;
	static TrelloServer testServer;
	User newUser;
	
	@BeforeAll
	static void setUpRemote() throws Exception
	{
		
		try
		{
			int port = 1088;
//			registry = LocateRegistry.createRegistry(port); 
			testServer = new TrelloServer(port);
			
//			
			testClient = new TrelloClient(port);
//			testServerInterface = (TrelloServerInterface) registry.lookup(name);
			
			
			
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
	
	@AfterAll
	static void unbindRemote() throws Exception
	{
		testServer.stopServer();
	}
	
	@Test
	void testTrelloServer() throws RemoteException
	{
		//https://stackoverflow.com/questions/1389736/how-do-i-create-a-unique-id-in-java#:~:text=We%20can%20create%20a%20unique,toString()%3B
		//UUID was used for the sake of easily testing this.  
		//If, like the board, servers handled User creation entirely this wouldn't be necessary.
		//There might be an easier way of doing this and I appreciate that there is a chance of
		//collision but with so few users and a 128 bit unique identifier lessens this risk.
		String randomUsername = UUID.randomUUID().toString();
		newUser = new UserConcrete(randomUsername, "Jacob", UUID.randomUUID().toString());
		
		//This newly created user shouldn't exist in any xml
		assertNull(testClient.checkUsernamePassword(randomUsername, "Jacob"));
		
		
		
		int boardID = testServer.getNewBoardID();
		BoardConcrete newBoard = (BoardConcrete) testClient.createBoard("Bubba", newUser);
		
		//The server should have returned the newly created board
		assertNotNull(newBoard);
		
		//Since the new board was made with the new user, the server now has record of the username
		//and password.
		assertNotNull(testClient.checkUsernamePassword(randomUsername, "Jacob"));
		
		BoardConcrete comparisonBoard = new BoardConcrete("Bubba", newUser, boardID);
		
		//Right now the local board and the one from the server should be copies
		assertEquals(newBoard.equals(comparisonBoard), true);
		
		UserConcrete member = new UserConcrete(randomUsername, "Ballard", UUID.randomUUID().toString());
		newBoard.addMember(newBoard.getOwner(), member.getUsername());
		testClient.updateBoard(newBoard);
		
		newBoard = (BoardConcrete) testClient.getBoard(newBoard.getUniqueID());
		//Make sure updating the board worked
		assertNotNull(newBoard);
		//Now that the server board was updated, it is not a copy of the local one
		
		//Note: The logic that previously made this work was based on the assumption that
		//clients know all users.  By adding users to a board via username, I'm not assuming
		//this anymore.  There is a chance for a username to not match a user in the server
		//which is what happens here.  Before these boards would not be equals after using
		//addMember but now they are because the 'member' variable is local.
		assertEquals(newBoard.equals(comparisonBoard), true); 
		
		comparisonBoard.addMember(comparisonBoard.getOwner(), member.getUsername());
		
		//Added the same member to the local board, now it is a copy of the remote board.
		assertEquals(newBoard.equals(comparisonBoard), true);
		
		System.out.println("working?");
		
		
		
		//The server should still have this board
		assertNotNull(testClient.getBoard(boardID));
		//The local version of the board from the server and the most recent server
		//version of the board should be copies.
		assertEquals(newBoard.equals(testClient.getBoard(boardID)), true);
		
		
		
		
		
		
		
	}
}
