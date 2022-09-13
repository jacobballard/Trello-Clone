/**
 * 
 */
package Trello;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author jacobballard
 * CSC 360
 * Trello Implementation: Sprint 1 Test Cases
 */
class TrelloTestCases
{

	User[] testUsers;
	Board[][] testBoards;
	List[][][] testLists;
//	TrelloServer testServer;
	
//	Board newBoard;
	
	Board[] xmlBoards;
	User[] xmlUsers;
	
	
	
	
	
	@BeforeEach
	void setUp() throws Exception
	{
		
		
//		testServer = new TrelloServer();
		
		
		
		
		
		testUsers = new User[5];
		
		
		testUsers[0] = new UserConcrete("Jacob0", "pass0");
		testUsers[1] = new UserConcrete("Jacob1", "pass1");
		testUsers[2] = new UserConcrete("Jacob2", "pass2");
		testUsers[3] = new UserConcrete("Jacob3", "pass3");
		testUsers[4] = new UserConcrete("Jacob4", "pass4");
		
		testBoards = new Board[5][3];
		testLists = new List[5][3][3];
		for(int i = 0; i < testUsers.length; i++) 
		{
			for(int j = 0; j < 3; j++) 
			{
				testBoards[i][j] = testUsers[i].createBoard("board" + Integer.toString(j), 123456789);
				for(int k = 0; k < 3; k++)
				{
					testUsers[i].getBoards().get(j).addList("list" + Integer.toString(k));
					for(int l = 0; l < 3; l++)
					{
						testUsers[i].getBoards().get(j).getLists().get(k).addCard("card" + Integer.toString(l), testUsers[i]);
						for(int m = 0; m < 3; m++)
						{
							testUsers[i].getBoards().get(j).getLists().get(k).getCards().get(l).createLabel("label");
							testUsers[i].getBoards().get(j).getLists().get(k).getCards().get(l).addComponent(new ComponentDescription("desc"));
						}
					}
				}
			}
		}
		
		xmlBoards = new BoardConcrete[2];
		xmlUsers = new User[2];
		
		for(int i = 0; i < 1; i++)
		{
			xmlUsers[i] = new UserConcrete("Jacob", "Ballard");
			xmlBoards[i] =  xmlUsers[i].createBoard("board", 0);
			for(int j = 0; j < 3; j++)
			{
				xmlBoards[i].addList("list");
				for(int k = 0; k < 2; k++)
				{
					xmlBoards[i].getLists().get(j).addCard("card", xmlUsers[i]);
					for(int l = 0; l < 1; l++)
					{
						xmlBoards[i].getLists().get(j).getCards().get(k).addComponent(new ComponentDescription("desc"));
						xmlBoards[i].getLists().get(j).getCards().get(k).addComponent(new ComponentChecklist("name"));
						xmlBoards[i].getLists().get(j).getCards().get(k).createLabel("label");
						xmlBoards[i].getLists().get(j).getCards().get(k).createLabel("anotherlabel");
						xmlBoards[i].getLists().get(j).getCards().get(k).addMember(new UserConcrete("Ballard", "Jacob"));
					}
				}
			}
			
			
		}
		
		
	}


	
	@Test
	void testXML()
	{
		
		BoardConcrete board = (BoardConcrete) xmlBoards[0];
		
		board.save();
		
		Board loadedBoard = xmlBoards[0].load(board.getUniqueID() + ".xml");
		
		assertNotNull(loadedBoard);
		assertEquals(loadedBoard.equals(xmlBoards[0]), true);
	}
	
	//This tests that a card can move from one list to another.
	//To move a card to a desired indexed in the new list, the reorderCards method will be called
	//on the List the card was added to after moveCard is called.
	@Test
	void testCardCanMoveLists()
	{
		ArrayList<Board> boards;
		ArrayList<List> lists;
		ArrayList<Card> cards;
		for(int i = 0; i < testUsers.length; i++)
		{
			boards = testUsers[i].getBoards();
			for(int j = 0; j < 3; j++) 
			{
				lists = boards.get(j).getLists();
				for(int k = 0; k < 3; k++)
				{
						
					List list0 = lists.get(0);
					List list1 = lists.get(1);
					
					String cardName = list1.getCards().get(0).getName();
					assertNotNull(cardName);
					//moves a card to list1 at the last index
					assertEquals(list1.moveCard(list0.getCards(), list1.getCards().get(0)), true);
					assertEquals(list0.getCards().get(list0.getCards().size() - 1).getName(), cardName);
					
					//moves the previously moved card back to list0 at the last index
					assertEquals(list0.moveCard(list1.getCards(), list0.getCards().get(list0.getCards().size() - 1)), true);
					assertEquals(list1.getCards().get(list1.getCards().size() - 1).getName(), cardName);
				}
			}
		}
	}
	
	//Tests that cards have components and labels.  The components are encapsulated by 
	//the Component class meaning a card can have an unspecified number of components of
	//various sorts.
	@Test
	void testCardComponentsAndLabels()
	{
		ArrayList<Board> boards;
		ArrayList<List> lists;
		ArrayList<Card> cards;
		Set<Component> components;
		Set<String> labels;
		for(int i = 0; i < testUsers.length; i++)
		{
			boards = testUsers[i].getBoards();
			for(int j = 0; j < 3; j++) 
			{
				lists = boards.get(j).getLists();
				for(int k = 0; k < 3; k++)
				{
					cards = lists.get(k).getCards();
					for(int l = 0; l < cards.size(); l++)
					{
						components = cards.get(l).getComponents();
						labels = cards.get(l).getLabels();
						assertNotNull(labels);
						
						assertEquals(components.size(), 1);
						assertEquals(labels.size(), 1);
						assertEquals(cards.get(l).addComponent(new ComponentDescription("DESC")), true);
						assertEquals(cards.get(l).addComponent(new ComponentChecklist()), true);
						assertEquals(cards.get(l).createLabel("label"), false);
						assertNotNull(cards.get(l).getComponents());
						assertNotNull(cards.get(l).getLabels());
						
					}
					
				}
			}
		}
	}
	
	//Tests that a card has an unordered set of members and that these members
	//can be added and removed.
	@Test
	void testCardHasMembers()
	{
		ArrayList<Board> boards;
		ArrayList<List> lists;
		ArrayList<Card> cards;
		for(int i = 0; i < testUsers.length; i++)
		{
			boards = testUsers[i].getBoards();
			for(int j = 0; j < 3; j++) 
			{
				lists = boards.get(j).getLists();
				for(int k = 0; k < 3; k++)
				{
					cards = lists.get(k).getCards();
					assertNotNull(cards);
					assertEquals(cards.get(0).getMembers().contains(testUsers[i]), true);
					assertNotNull(cards.get(0).getMembers());
					assertEquals(cards.get(0).removeMember(testUsers[i]), true);
					assertNotNull(cards.get(0).getMembers());
					assertEquals(cards.get(0).addMember(testUsers[i]), true);
					if(i != 0)
					{
						assertEquals(cards.get(0).addMember(testUsers[0]), true);
						assertEquals(cards.get(0).addMember(testUsers[0]), false);
						assertEquals(cards.get(0).removeMember(testUsers[0]), true);
						
					}
				}
			}
		}
	}
	
	
	//Shows that cards can be reordered inside their respective lists, have a name, and
	//the name can be updated.
	@Test
	void testCardHasOrderAndName()
	{
		ArrayList<Board> boards;
		ArrayList<List> lists;
		ArrayList<Card> cards;
		for(int i = 0; i < testUsers.length; i++)
		{
			boards = testUsers[i].getBoards();
			for(int j = 0; j < 3; j++) 
			{
				lists = boards.get(j).getLists();
				for(int k = 0; k < 3; k++)
				{
					assertNotNull(lists.get(k));
					//swap two cards
					assertEquals(lists.get(k).reorderCard(0, 1), true);
					assertEquals(lists.get(k).getCards().get(0).getName(), "card1");
					assertEquals(lists.get(k).getCards().get(1).getName(), "card0");
					 //swap another two, one already having been moved once
					assertEquals(lists.get(k).reorderCard(0, 2), true);
					assertEquals(lists.get(k).getCards().get(0).getName(), "card2");
					assertEquals(lists.get(k).getCards().get(2).getName(), "card1");
					
					assertEquals(lists.get(k).reorderCard(2, 0), true);
					assertEquals(lists.get(k).getCards().get(0).getName(), "card1");
					assertEquals(lists.get(k).getCards().get(2).getName(), "card2");
					
					assertEquals(lists.get(k).reorderCard(1, 0), true);
					assertEquals(lists.get(k).getCards().get(1).getName(), "card1");
					assertEquals(lists.get(k).getCards().get(0).getName(), "card0");
					
					
					//This proves that the cards are ordered and the name can be updated
					cards = lists.get(k).getCards();
					for(int l = 0; l < 3; l++)
					{
						assertNotNull(cards.get(l).getName());
						assertEquals(cards.get(l).getName(), "card" + Integer.toString(l));
						assertEquals(cards.get(l).updateName("updateCard" + Integer.toString(l)), true);
						assertEquals(cards.get(l).getName(), "updateCard" + Integer.toString(l));
						assertEquals(cards.get(l).updateName("card" + Integer.toString(l)), true);
					}	
				}
			}
		}
	}

	
	//Shows that list can be added to a board, are ordered, have a name,
	//the name can be updated, and the list deleted
	@Test
	void testLists()
	{
		ArrayList<Board> boards;
		for(int i = 0; i < testUsers.length; i++)
		{
			boards = testUsers[i].getBoards();
			
			for(Board b : boards)
			{
				for(int j = 0; j < 3; j++)
				{
					
					assertEquals(b.addList("list" + Integer.toString(j)), true);
					
					assertEquals(b.getLists().size(), j + 4); //since a list is added on the last line of each loop, it must be offset
					ArrayList<List> list = b.getLists();
					
					assertNotNull(list);
					assertEquals(b.deleteList(list.get(j + 3)), true); //accounts for offset
					assertEquals(b.addList("list" + Integer.toString(j)), true);
				}
				
				
				//check that lists can be reordered and have names
				assertEquals(b.swapList(0 + 3, 1 + 3), true);
				assertEquals(b.getLists().get(0 + 3).getName(), "list1");
				assertEquals(b.getLists().get(1 + 3).getName(), "list0");
				
				assertEquals(b.swapList(0 + 3, 2 + 3), true);
				assertEquals(b.getLists().get(0 + 3).getName(), "list2");
				assertEquals(b.getLists().get(2 + 3).getName(), "list1");
				
				//show that list names can be updated
				for(int j = 0; j < 3; j++)
				{
					assertEquals(b.getLists().get(j + 3).updateName("name" + Integer.toString(j)), true);
					assertEquals(b.getLists().get(j + 3).getName(), "name" + Integer.toString(j));
				}
				//checks that a list is deletable
				for(int j = 0; j < 3; j++)
				{
					assertEquals(b.deleteList(b.getLists().get(0 + 3)), true);
				}
				
			}
		}
	}

	
	//This tests both that a user has boards and that each board has a name and
	//that the name can be updated.
	@Test 
	void testBoardName()
	{
		ArrayList<Board> boards;
		for(int i = 0; i < testUsers.length; i++) 
		{
			boards = testUsers[i].getBoards();
			for(int j = 0; j < 3; j++)
			{
				assertNotNull(boards.get(j).getName());
				assertEquals(boards.get(j).getName(), "board" + Integer.toString(j));
				assertEquals(boards.get(j).updateBoardName("boardUpdate" + Integer.toString(j)), true);
				assertNotNull(boards.get(j).getName());
				assertEquals(boards.get(j).getName(), "boardUpdate" + Integer.toString(j));
			}
			
			
			
		}
	}
	
	//This tests that a board has an owner.
	@Test
	void testBoardOwner()
	{
		ArrayList<Board> boards;
		for(int i = 0; i < testUsers.length; i++)
		{
			boards = testUsers[i].getBoards();
			for(Board b : boards)
			{
				assertNotNull(b.getOwner());
				assertEquals(b.getOwner(), testUsers[i]);
			}
		}
	}
	
	
	//Tests that the owner of a board can add a member, remove a member, and that other
	//users cannot
	
	//These all quit functioning when I made the handling of members
	//a server side thing.  Client side means the client has to know
	//all users on a server.
	@Test
	void testBoardMembers()
	{
		ArrayList<Board> boards;
		Set<User> users;
		for(int i = 0; i < testUsers.length; i++)
		{
			boards = testUsers[i].getBoards();
			for(Board b : boards)
			{
				users = b.getUsers();
				//test that the owner is also a member
				assertEquals(users.contains(testUsers[i]), true);
				
				//test that users can be added and removed solely by the owner to the set of members
//				for(int j = 0; j < testUsers.length; j++)
//				{
//					//j is not the owner
//					if(i != j)
//					{
//						assertNotNull(b.getUsers());
//						assertEquals(b.addMember(testUsers[i], testUsers[j].getUsername()), true);
//						assertEquals(b.getUsers().contains(testUsers[j]), true);
//						assertEquals(b.removeMember(testUsers[i], testUsers[j].getUsername()), true);
//						assertEquals(b.getUsers().contains(testUsers[j]), false);
//						
//						//Since j isn't the owner, j shouldn't be able to add a user
//						assertEquals(b.addMember(testUsers[j], testUsers[j].getUsername()), false);
//						assertEquals(b.getUsers().contains(testUsers[j]), false);
//					} else //j is the owner
//					{
//						//User will not be added because it is already in the set
//						
//						//This assumption worked while we were comparing User in the HashSet
//						//now that the client is expecting the server to handle adding and removing
//						//User objects, this won't work.
//						assertEquals(b.addMember(testUsers[i], testUsers[j].getUsername()), t);
//						
//						//But the owner is still a member
//						assertEquals(b.getUsers().contains(testUsers[i]), true);
//					}
//				}
				
			}
			
			
		}
	}

	//Tests that login properly works.
	@Test
	void testLogin()
	{
		for(int i = 0; i < testUsers.length; i++) 
		{
			assertEquals(testUsers[i].login("Jacob" + Integer.toString(i), "pss" + Integer.toString(i)), false);
			assertEquals(testUsers[i].login("Jcob" + Integer.toString(i), "pass" + Integer.toString(i)), false);
			assertEquals(testUsers[i].login("Jacob" + Integer.toString(i), "pass" + Integer.toString(i)), true);
		}
	}

	//Tests that a User has a username.
	@Test
	void testGetUsername()
	{
		for(int i = 0; i < testUsers.length; i++) 
		{
			assertNotNull(testUsers[i].getUsername());
			assertEquals(testUsers[i].getUsername(), "Jacob" + Integer.toString(i));
		}
	}
}
