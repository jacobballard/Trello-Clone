//Jacob Ballard
//CSC 360
//Describes any user on a Trello board
//User have a username, password, and hold boards

package Trello;

import java.io.Serializable;
import java.util.ArrayList;

public class UserConcrete implements User, Serializable
{

	private static final long serialVersionUID = 3066984369662971657L;
	private String username;
	private String password;
	private ArrayList<Board> boards;
	private String uniqueID;
	
	UserConcrete(String username, String password)
	{
		this.username = username;
		this.password = password;
		this.boards = new ArrayList<Board>();
	}
	
	UserConcrete(String username, String password, String uniqueID)
	{
		this.username = username;
		this.password = password;
		this.boards = new ArrayList<Board>();
		this.uniqueID = uniqueID;
	}
	
	

	public UserConcrete()
	{
		this("user", "pass");
	}
	
	
	

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}



	
	@Override
	public boolean equals(Object o)
	{
		//This does not check that boards are equals because it would cycle forever
		//https://www.geeksforgeeks.org/overriding-equals-method-in-java/
		//I learned how to do this when looking up how to override the equals method
		if (!(o instanceof UserConcrete)) { 
            return false; 
        }
		
		//Cast o as UserConcrete object
		UserConcrete that = (UserConcrete) o;
		
		//Check that the String username are not null
		if(this.getUsername() == null || that.getUsername() == null) {
			return false;
		}
		
		//Check that the String username are equal
		if(!this.username.equals(that.username)) {
			return false;
		}
		
		//Check that the String password are not null
		if(this.getPassword() == null || that.getPassword() == null) {
			return false;
		}
		
		//Check that the String password are equal
		if(!this.getPassword().equals(that.getPassword())) {
			return false;
		}
		
		return true;
	}



	public BoardConcrete createBoard(String name, Integer UID)
	{
		// TODO Auto-generated method stub
		BoardConcrete newBoard = new BoardConcrete(name, this, UID);
		this.boards.add(newBoard);
		return newBoard;
	}

	public Boolean login(String username, String password)
	{
		Boolean didLogin = false;
		if(this.username.equals(username) && this.password.equals(password))
		{
			didLogin = true;
		}
		return didLogin;
	}

	public String getUsername()
	{
		return this.username;
	}
	

	public ArrayList<Board> getBoards()
	{
		return this.boards;
	}
	
	@Override
	public String toString()
	{
		return "Username : " + this.username + ", Password : " + this.password;
	}
	
	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setBoards(ArrayList<Board> boards)
	{
		this.boards = boards;
	}
	
	public void setUniqueID(String i)
	{
		this.uniqueID = i;
	}
	
	public String getUniqueID()
	{
		return this.uniqueID;
	}

}
