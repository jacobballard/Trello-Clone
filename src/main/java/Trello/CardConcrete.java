//Jacob Ballard
//CSC 360
//CardConcrete holds a name, a set of labels, a set of User, and a set of concrete Component


package Trello;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CardConcrete implements Card, Serializable
{

	private static final long serialVersionUID = 3750475725225106150L;
	private String cardName;
	private Set<String> labels;
	private Set<User> members;
	private Set<Component> components;
	
	public CardConcrete(String name, User member)
	{
		this.cardName = name;
		this.members = new HashSet<User>();
		this.labels = new HashSet<String>();
		this.components = new HashSet<Component>();
		this.members.add(member);

	}
	
	public CardConcrete()
	{
		this("name", new UserConcrete());
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardName == null) ? 0 : cardName.hashCode());
		result = prime * result + ((components == null) ? 0 : components.hashCode());
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		
		 //https://www.geeksforgeeks.org/overriding-equals-method-in-java/
		//I learned how to do this when looking up how to override the equals method
		if (!(o instanceof CardConcrete)) { 
            return false; 
        }
		
		//Cast o as CardConcrete object
		CardConcrete that = (CardConcrete) o;
		
		//Check that the String cardName are not null
		if(this.getCardName() == null || that.getCardName() == null) {
			return false;
		}
		
		//Check that the String cardName are equals
		if(!this.getCardName().equals(that.getCardName())) {
			return false;
		}
		
		//Check that the Set<String> labels are not null
		if(this.getLabels() == null || that.getLabels() == null) {
			return false;
		}
		
		//Check that the Set<String> labels are the same size
		if(this.getLabels().size() != that.getLabels().size()) {
			return false;
		}
		
		//Check that the Set<String> labels are equal
		for(String l : this.getLabels()) {
			if(!that.getLabels().contains(l)) {
				return false;
			}
		}
		
		//CHeck that Set<User> members are not null
		if(this.getMembers() == null || that.getMembers() == null) {
			return false;
		}
		
		//CHeck that Set<User> members are the same size
		if(this.getMembers().size() != that.getMembers().size()) {
			return false;
		}
		
		//CHeck that Set<User> members are equal
		for(User m : this.getMembers()) {
			if(!that.getMembers().contains(m)) {
				return false;
			}
		}
		//Check that the Set<Component> are not null
		if(this.getComponents() == null || that.getComponents() == null) {
			return false;
		}
		
		//Check that the Set<Component> are the same size
		if(this.getComponents().size() != that.getComponents().size()) {
			return false;
		}
		
		//Check that the Set<Component> are equal
		for(Component c : this.getComponents()) {
			if(!that.getComponents().contains(c)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean createLabel(String name)
	{
		return this.labels.add(name);
	}

	public boolean deleteLabel(String name)
	{
		return this.labels.remove(name);
	}

	public boolean addComponent(Component component)
	{
		return this.components.add(component);
	}

	public boolean updateName(String newName)
	{
		this.cardName = newName;
		return true;
	}

	public String getName()
	{
		return this.cardName;
	}

	public Set<String> getLabels()
	{
		return this.labels;
	}

	public Set<User> getMembers()
	{
		return this.members;
	}

	public Set<Component> getComponents()
	{
		return this.components;
	}

	public boolean addMember(User member)
	{
		return this.members.add(member);
	}
	
	public boolean removeMember(User member)
	{
		return this.members.remove(member);
	}

	public String getCardName()
	{
		return cardName;
	}

	public void setCardName(String cardName)
	{
		this.cardName = cardName;
	}

	public void setLabels(Set<String> labels)
	{
		this.labels = labels;
	}

	public void setMembers(Set<User> members)
	{
		this.members = members;
	}

	public void setComponents(Set<Component> components)
	{
		this.components = components;
	}

	
	
	
}
