package Trello;

import java.util.ArrayList;

public interface User
{
	BoardConcrete createBoard(String name, Integer UID);
	Boolean login(String username, String password);
	String getUsername();
	String getPassword();
	ArrayList<Board> getBoards();
}
