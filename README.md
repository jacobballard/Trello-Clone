Sprint II :
I believe everything is working.  The only thing I feel may be questionable is, "a client may request any board they are a member of."  There is no code in my client
or server to prevent a user from grabbing another board if they aren't a member of it; they are prevented from this solely by working within the bounds of what
the User Interface will show them (i.e. they could get another random board if they fished around enough but the User Interface itself will only display boards they
are associated with).

To run the tests: 
Launch Eclipse and clone this git repository.  Under the 'src/test/java' is the Trello package and inside is TrelloTestCases.java. 
Having the newly-cloned project selected, click run and the test cases will run automatically.  

Issues fixed! 
XML test cases work now!  Test cases are all in TrelloTestCases.java; they are split up by class but in some cases the class is broken down into more test methods.




~~Every test case works except for testing the XML.  
The XML test, 'testXML()', does not compare the ListConcrete object stored in the BoardConcrete in the ArrayList 'lists' properly.  Further debugging is necessary to fix this. ~~ 