package Trello;

import java.util.ArrayList;

public interface List
{
	boolean addCard(String name, User member);
	boolean moveCard(ArrayList<Card> list, Card card);
	boolean updateName(String newName);
	boolean reorderCard(int a, int b);
	ArrayList<Card> getCards();
	String getName();
	boolean removeCard(Card card);
}
