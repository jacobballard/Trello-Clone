//Jacob Ballard
//CSC 360
//Sprint II - Implementation

package Trello;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface TrelloServerInterface extends Remote
{
//	public void startServer();
//	private void getBoards();
	public ArrayList<Board> getBoardsForUser(User u) throws RemoteException;
	public User checkUsernamePassword(String username, String password) throws RemoteException;
	public Board getBoard(Integer boardID) throws RemoteException;
	public void updateBoard(Board board) throws RemoteException;
//	private boolean checkIfMembersWereUpdated(Board clientBoard, Board serverBoard);
	public Board createBoard(String boardName, User owner) throws RemoteException;
	public void deleteBoard(Board board) throws RemoteException;
}

