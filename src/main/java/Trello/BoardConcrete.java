//Jacob Ballard
//CSC 360
//BoardConcrete holds a name, owner, a set of User, and all the List contained by it

package Trello;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BoardConcrete implements Board, Serializable
{
	private static final long serialVersionUID = -7077163527663927981L;
	private String boardName;
	private User owner;
	private Set<User> users;
	private Set<String> usernames;
	private ArrayList<List> lists;
	private Integer uniqueID;
	
	
	//This constructor will only be applicable during testing
	BoardConcrete(String name, User owner)
	{
		this.boardName = name;
		this.owner = owner;
		this.users = new HashSet<User>();
		this.usernames = new HashSet<String>();
		this.lists = new ArrayList<List>();
		this.users.add(owner);
	}

	//This is the real constructor that is used with TrelloServer
	BoardConcrete(String name, User owner, Integer uniqueID)
	{
		this.boardName = name;
		this.owner = owner;
		this.users = new HashSet<User>();
		this.usernames = new HashSet<String>();
		this.lists = new ArrayList<List>();
		this.users.add(owner);
		this.uniqueID = uniqueID;
	}
	
	public BoardConcrete()
	{
		this("name", new UserConcrete(), 10);
	}
	
	
	
	
	
	

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardName == null) ? 0 : boardName.hashCode());
		result = prime * result + ((lists == null) ? 0 : lists.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o)
	{
		
		
		//https://www.geeksforgeeks.org/overriding-equals-method-in-java/
		//I learned how to do this when looking up how to override the equals method
		if (!(o instanceof BoardConcrete)) { 
			System.out.println("here??");
            return false; 
        }
		
		//Cast o as BoardConcrete
		BoardConcrete that = (BoardConcrete) o;
		
		//Check that the String boardName are not null
		if(this.getBoardName() == null || that.getBoardName() == null) {
			return false;
		}
				
		//Check that the String boardName are equal
		if(!this.getBoardName().equals(that.getBoardName())) {
			return false;
		}
		
		//Check that the User owner are not null
		if(this.owner == null || that.owner == null) {
			return false;
		}
		
		//Check that the User owner are equal
		if(!this.owner.equals(that.owner)) {
			return false;
		}
		
		//Check that the ArrayList<List> lists are not null
		if(this.getLists() == null || that.getLists() == null) {
			return false;
		}
		
		//Check that the ArrayList<List> lists are the same size
		if(this.getLists().size() != that.getLists().size()) {
			return false;
		}
		
		//Check that the ArrayList<List> lists are equal
		for(int i = 0; i < this.getLists().size(); i++) {
			if(!this.getLists().get(i).equals(that.getLists().get(i))) {
				return false;
			}
		}
		
		//Check that the Set<User> users are not null
		if(this.getUsers() == null || that.getUsers() == null) {
			return false;
		}
		
		//Check that the Set<User> users are equal
		for(User u : this.getUsers()) {
			if(!that.getUsers().contains(u)) {
				return false;
			}
		}
		
		//Check the size of the Set<User> are the same size
		if(this.getUsers().size() != that.getUsers().size()) {
			return false;
		}

		
		return true;
	}

	
	public boolean addMember(User whoIsAdding, String member)
	{
		if(this.owner == whoIsAdding)
		{
//			return this.users.add(member);
			return this.usernames.add(member);
			
		} else 
		{
			return false;
		}

	}
	
	public Set<String> getUsernames()
	{
		return this.usernames;
	}

	public boolean removeMember(User whoIsRemoving, String member)
	{
		if(this.owner == whoIsRemoving)
		{
//			return this.users.remove(member);
			return this.usernames.remove(member);
		} else
		{
			return false;
		}

	}

	public boolean addList(String name)
	{
		List listToAdd = new ListConcrete(name);
		return this.lists.add(listToAdd);
	}

	public boolean deleteList(List list)
	{
		return this.lists.remove(list);

	}

	public boolean swapList(int a, int b)
	{
		List aList = this.lists.get(a);
		List bList = this.lists.get(b);
		this.lists.set(a, bList);
		this.lists.set(b, aList);
		return true;

	}

	//Saves to board.xml
	public boolean save()
	{
		XMLEncoder encoder=null;
		try{
		encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(this.getUniqueID().toString() + ".xml")));
		}catch(FileNotFoundException fileNotFound){
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(this);
		encoder.close();
		
		return true;

	}
	
	//Load board.xml
	public Board load(String fileName)
	{
			XMLDecoder decoder=null;
			try {
				decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: File" + fileName + ".xml not found");
			}
			Board board =(Board)decoder.readObject();
			System.out.println(board);
			
			return board;
	}

	public boolean updateBoardName(String newName)
	{
		this.boardName = newName;
		return true;

	}

	public String getName()
	{
		return this.boardName;
	}

	public ArrayList<List> getLists()
	{
		return this.lists;
	}

	public Set<User> getUsers()
	{
		return this.users;
	}

	public User getOwner()
	{
		return this.owner;
	}
	
	@Override
	public String toString()
	{
		return this.boardName;
	}
	
	/**
	 * @return the boardName
	 */
	public String getBoardName()
	{
		return boardName;
	}

	/**
	 * @param boardName the boardName to set
	 */
	public void setBoardName(String boardName)
	{
		this.boardName = boardName;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner)
	{
		this.owner = owner;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<User> users)
	{
		this.users = users;
	}

	/**
	 * @param lists the lists to set
	 */
	public void setLists(ArrayList<List> lists)
	{
		this.lists = lists;
	}
	
	public void setUniqueID(Integer i)
	{
		this.uniqueID = i;
	}
	
	public Integer getUniqueID()
	{
		return this.uniqueID;
	}


	
	

}
