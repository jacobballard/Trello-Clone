package Trello;

import java.util.ArrayList;
import java.util.Set;

public interface Board
{
	boolean addMember(User whoIsAdding, String member);
	boolean removeMember(User whoIsRemoving, String member);
	boolean addList(String name);
	boolean deleteList(List list);
	boolean swapList(int a, int b);
	boolean save();
	public Board load(String fileName);
	boolean updateBoardName(String newName);
	String getName();
	ArrayList<List> getLists();
	Set<User> getUsers();
	User getOwner();

}
