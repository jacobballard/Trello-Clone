//Jacob Ballard
//CSC 360
//Sprint II - Implementation

package Trello;

import java.io.File;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

public class TrelloServer extends UnicastRemoteObject implements TrelloServerInterface
{
	

	private static final long serialVersionUID = 1496158845636674987L;
	Hashtable<String, User> users;
	Hashtable<Integer, Board> boards;
	private Registry registry;
	private int newBoardID;

	
	//
	public TrelloServer(int port) throws RemoteException
	{
		super();
//		System.out.println("TrelloServer>..<");
		users = new Hashtable<String, User>();
		boards = new Hashtable<Integer, Board>();
		
		for(User u : users.values())
		{
			System.out.println(u.getUsername()+ " u");
		}
		
		UserConcrete testUser = new UserConcrete("jacob", "ballard", UUID.randomUUID().toString());
		users.put(testUser.getUniqueID(), testUser);
		startServer(port);
		getBoards();
		
//		System.out.println("TrelloServer ante setup");
//		System.out.println(this.users.size());
//		System.out.println(this.boards.size());
	}
	
	//Starts the RMI server
	public void startServer(int port)
	{
		try
		{
			registry = LocateRegistry.createRegistry(port); 
			TrelloServer s = this;
			
			
			String name = "TRELLOSERVER";
			registry.rebind(name, s);
			
//			StringInterface mp = (StringInterface) registry.lookup(name);
//			
//			String answer = mp.concatenate("Jacob", "Ballard");
//			
//			System.out.println(answer);
			
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public int getNewBoardID()
	{
		return this.newBoardID;
	}
	
	//Keep the port from messing up while running the tests multiple times in
	//a short period of time.
	public void stopServer()
	{
		try
		{
			registry.unbind("TRELLOSERVER");
		} catch (AccessException e)
		{
			e.printStackTrace();
		} catch (RemoteException e)
		{
			e.printStackTrace();
		} catch (NotBoundException e)
		{
			e.printStackTrace();
		}
	}
	
	//This loads all boards and its associated members from disk
	private void getBoards()
	{
		
		//https://stackoverflow.com/questions/3154488/how-do-i-iterate-through-the-files-in-a-directory-in-java
		//This was used to figure out getting the XML names from a directory
		
		BoardConcrete b = new BoardConcrete();
		
		
		File f = new File("./");
		File[] cd = f.listFiles();
		
		for(File xml : cd) 
		{
			System.out.println("get boards ---");
			System.out.println(xml.getName());
			if(xml.getName().contains(".xml") && !xml.getName().equals("pom.xml") && !xml.getName().equals("123456789.xml"))
			{
				System.out.println("Why do I got a board!" + xml.getName());
				b = (BoardConcrete) b.load(xml.getName());
				if(!this.boards.containsKey(b.getUniqueID()))
				{
					if(b.getUniqueID() > this.newBoardID)
					{
						this.newBoardID = b.getUniqueID();
					}
					System.out.println("adding board" + b.getName());
					this.boards.put(b.getUniqueID(), b);
					this.addUsersToBoardsHashtable(b);
				} else {
					System.out.println("Duplicate unique board ids???");
				}
			}
		}
	}
	
	//Add all User to the Hashtable<String, User> users.
	private void addUsersToBoardsHashtable(Board b)
	{
		//goes through all members on a board and adds them
		//Right now it just adds a board's owner if it doesn't exist
		UserConcrete owner = (UserConcrete) b.getOwner();
		if(!this.users.containsKey(owner.getUniqueID()))
		{
			System.out.println("adding to owner" + b.getOwner().getUsername());
			this.users.put(owner.getUniqueID(), owner);
			
		}
		
		for(User wrong : b.getUsers())
		{
			UserConcrete u = (UserConcrete) wrong;
			if(!this.users.containsKey(u.getUniqueID()))
			{
				this.users.put(u.getUniqueID(), u);
			}
		}
		
		for(User wrong : b.getUsers())
		{
			UserConcrete u = (UserConcrete) wrong;
			if(!this.users.containsKey(u.getUniqueID()))
			{
				this.users.put(u.getUniqueID(), u);
			}
		}
	}
	
	//Authenticate a username and password.  Returns the matched User if authenicated
	//null if the username and password don't match anything.
	public User checkUsernamePassword(String username, String password) throws RemoteException
	{
		System.out.println("checkUsernamePassword");
		for(User u : this.users.values())
		{
			
			if(u.login(username, password))
			{
				return u;
			}
		}
		return null;
	}
	
	//Get a Board based on its ID.  
	public Board getBoard(Integer boardID) throws RemoteException
	{
		return this.boards.get(boardID);
	}
	
	//Update a Board based on a modified copy of the Board.
	public void updateBoard(Board board) throws RemoteException
	{
		//get board w same id in hash table
		BoardConcrete b = (BoardConcrete) board;
		
		//before replacing check if any members were added or removed
		
		this.boards.replace(b.getUniqueID(), b);
		
		UserConcrete userCast = (UserConcrete) board.getOwner();
		
		System.out.println("update board for username: " + userCast.getUsername());
		
		System.out.println("update board for user UID: " + userCast.getUniqueID());
		
		this.replaceBoardFromUser(b);
		
		b.save();
		
		//check if members in that board were updated
	}
	
	private void manageBoardMembers(BoardConcrete b)
	{
		for(String username : b.getUsernames())
		{
			for(User u : this.users.values())
			{
				if(u.getUsername().equals(username))
				{
					u.getBoards().add(b);
					b.getUsers().add(u);
				}
			}
		}
		
//		for(String username : b.getUsernames())
//		{
//			boolean flag = false;
//			for(User u : b.getUsers())
//			{
//				if(u.getUsername().equals(username))
//				{
//					flag = true;
//				}
//			}
//			
//			if(!flag)
//			{
//				
//			}
//		}
		
		for(User u : this.users.values())
		{
			boolean flag = false;
			for(User isAMember : b.getUsers())
			{
				
				if(isAMember.equals(u))
				{
					flag = true;
				}
			}
			
			if(!flag)
			{
				u.getBoards().remove(b);
			}
		}
		
		
		
		
	}
	
	private void replaceBoardFromUser(Board b)
	{
		
		System.out.println("Go to replaceBoardFromUser");
		BoardConcrete boardReplacingCast = (BoardConcrete) b;
		UserConcrete userCast = (UserConcrete) b.getOwner();
		
		System.out.println("UID: " + userCast.getUniqueID());
		
		int i = 0;
		for(Board boardsInUser : this.users.get(userCast.getUniqueID()).getBoards())
		{
			BoardConcrete boardsInUserCast = (BoardConcrete) boardsInUser;
			
			System.out.println(Integer.toString(i));
			
			System.out.println(boardsInUserCast.getBoardName());
			System.out.println(boardReplacingCast.getBoardName());
			
			
			System.out.println(Integer.toString(boardsInUserCast.getUniqueID()));
			System.out.println(Integer.toString(boardReplacingCast.getUniqueID()));
			
			if(boardsInUserCast.getUniqueID() == boardReplacingCast.getUniqueID())
			{
				
				System.out.println("replacing 1 board for owner!");
				this.users.get(userCast.getUniqueID()).getBoards().remove(boardsInUser);
				this.users.get(userCast.getUniqueID()).getBoards().add(boardReplacingCast);
			}
			
			if(boardsInUserCast.getUniqueID().equals(boardReplacingCast.getUniqueID()))
			{
				
				System.out.println("replacing 1 board for owner! .equals");
				this.users.get(userCast.getUniqueID()).getBoards().remove(boardsInUser);
				this.users.get(userCast.getUniqueID()).getBoards().add(boardReplacingCast);
			}
			
			i++;
		}
		
		for(User u : this.users.values())
		{
			UserConcrete allUsersCast = (UserConcrete) u;
			
			for(Board boardsInUser : allUsersCast.getBoards())
			{
				BoardConcrete boardsInUserCast = (BoardConcrete) boardsInUser;
				
				if(boardsInUserCast.getUniqueID().equals(boardReplacingCast.getUniqueID()))
				{
					
					System.out.println("replacing 1 board for various members!");
					this.users.get(userCast.getUniqueID()).getBoards().remove(boardsInUser);
					this.users.get(userCast.getUniqueID()).getBoards().add(boardReplacingCast);
				}
			}
			
		}
	}
	
	//Creates a Board and adds a User to Hashtable<String, User> users if not already 
	//contained in it (this is for the sake of making testing easier).
	//Returns the newly created Board.
	public Board createBoard(String boardName, User owner) throws RemoteException
	{
		System.out.println("Create board?");
		BoardConcrete newBoard = owner.createBoard(boardName, this.newBoardID);
		this.boards.put(this.newBoardID, newBoard);
		this.newBoardID++;
		newBoard.save();
		UserConcrete ownerCast = (UserConcrete) owner;
		if(!this.users.containsKey(ownerCast.getUniqueID()))
		{
			this.users.put(ownerCast.getUniqueID(), ownerCast);
		} else {
			this.users.replace(ownerCast.getUniqueID(), this.users.get(ownerCast.getUniqueID()), ownerCast);
		}
		return newBoard;
	}

	//Get all Board associated with a User.  
	//This may be helpful later on if multiple users are online and
	//the parametized user becomes/is no longer a member of a new board
	public ArrayList<Board> getBoardsForUser(User u)
	{
		UserConcrete userCast = (UserConcrete) u;
		return this.users.get(userCast.getUniqueID()).getBoards();
	}

	
	public void deleteBoard(Board board) throws RemoteException
	{
		this.boards.remove(((BoardConcrete)board).getUniqueID());
		UserConcrete userCast = (UserConcrete) board.getOwner();
		this.users.get(userCast.getUniqueID()).getBoards().remove(board);
		
	}

		
		
		

	
}

