//Jacob Ballard
//CSC 360
//List holds a name and cards

package Trello;

import java.io.Serializable;
import java.util.ArrayList;

public class ListConcrete implements List, Serializable
{
	private static final long serialVersionUID = 8735472149983924853L;
	private String listName;
	private ArrayList<Card> cards;
	
	ListConcrete(String name)
	{
		this.listName = name;
		this.cards = new ArrayList<Card>();
	}

	public ListConcrete()
	{
		this("name");
	}
	
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
		result = prime * result + ((listName == null) ? 0 : listName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o)
	{
		
		//https://www.geeksforgeeks.org/overriding-equals-method-in-java/
		//I learned how to do this when looking up how to override the equals method
		if (!(o instanceof ListConcrete)) { 
            return false; 
        }
		
		//Cast o as ListConcrete object
		ListConcrete that = (ListConcrete) o;
		
		//Check that the String listName are not null
		if(this.getName() == null || that.getName() == null) {
			return false;
		}
		
		//Check that the String listName are equal
		if(!this.getName().equals(that.getName())) {
			return false;
		}
		
		//Check that the ArrayList<Card> cards are not null
		if(this.getCards() == null || that.getCards() == null) {
			return false;
		}
		
		//Check that the ArrayList<Card> cards are the same size
		if(this.getCards().size() != that.getCards().size()) {
			return false;
		}

		//Check that the ArrayList<Card> cards are equal
		for(int i = 0; i < this.getCards().size(); i++) {
			if(!this.getCards().get(i).equals(that.getCards().get(i))) {
				return false;
				
			}
		}
		
		return true;
	}

	
	//Adds a card to the list
	public boolean addCard(String name, User member)
	{
		return this.cards.add(new CardConcrete(name, member));
	}
	
	//Move a card from this list to that list
	public boolean moveCard(ArrayList<Card> that, Card card)
	{
		boolean success = false;
		if(that.add(card) && this.cards.remove(card))
		{
			success = true;
		}
		return success;
	}

	public boolean updateName(String newName)
	{
		this.listName = newName;
		return true;
	}
	
	//Place a card at an index in place of another card
	public boolean reorderCard(int a, int b)
	{
		Card aCard = this.cards.get(a);
		Card bCard = this.cards.get(b);
		this.cards.set(a, bCard);
		this.cards.set(b, aCard);
		return true;
		
	}


	public ArrayList<Card> getCards()
	{
		return this.cards;
	}

	public String getName()
	{
		return this.listName;
	}

	public String getListName()
	{
		return listName;
	}

	public void setListName(String listName)
	{
		this.listName = listName;
	}

	public void setCards(ArrayList<Card> cards)
	{
		this.cards = cards;
	}

	@Override
	public boolean removeCard(Card card)
	{
		return this.cards.remove(card);
	}
	
	

}
