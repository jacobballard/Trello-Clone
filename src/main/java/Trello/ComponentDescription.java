//Jacob Ballard
//CSC 360
//Abstract implementation of Component that holds a description

package Trello;

import java.io.Serializable;

public class ComponentDescription implements Component, Serializable
{
	private static final long serialVersionUID = 3871754328774935389L;
	String description;
	
	ComponentDescription(String desc)
	{
		this.description = desc;
	}
	
	public ComponentDescription()
	{
		this("desc");
	}
	
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o)
	{
		
		//https://www.geeksforgeeks.org/overriding-equals-method-in-java/
		//I learned how to do this when looking up how to override the equals method
		if (!(o instanceof ComponentDescription)) { 
            return false; 
        }
		
		//Cast o as ComponentDescription object
		ComponentDescription that = (ComponentDescription) o;
		
		//Check that the String description are not null
		if(this.getDescription() == null || that.getDescription() == null) {
			return false;
		}
		
		//Check that the String description are equal
		if(!this.getDescription().equals(that.getDescription())) {
			return false;
		}
		
		return true;
	}

	public void editData()
	{
		// TODO Auto-generated method stub

	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	
	
	

}
