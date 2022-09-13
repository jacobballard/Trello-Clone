//Jacob Ballard
//CSC 360
//Abstract implementation of Component that holds checklist text and whether they are
//selected

package Trello;

import java.io.Serializable;
import java.util.ArrayList;

public class ComponentChecklist implements Component, Serializable
{
	private static final long serialVersionUID = 2962724056756885385L;
	ArrayList<String> checklist;
	ArrayList<Boolean> isSelected;
	
	ComponentChecklist(String name)
	{
		this.checklist = new ArrayList<String>();
		this.isSelected = new ArrayList<Boolean>();
	}
	
	public ComponentChecklist()
	{
		this("name");
	}
	
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checklist == null) ? 0 : checklist.hashCode());
		result = prime * result + ((isSelected == null) ? 0 : isSelected.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o)
	{
		//https://www.geeksforgeeks.org/overriding-equals-method-in-java/
		//I learned how to do this when looking up how to override the equals method
		if (!(o instanceof ComponentChecklist)) { 
            return false; 
        }
		
		//Cast o as ComponentChecklist object
		ComponentChecklist that = (ComponentChecklist) o;
		
		//Check that the ArrayList<String> checklist are not null
		if(this.getChecklist() == null || that.getChecklist() == null) {
			return false;
		}
		
		//Check that the ArrayList<String> checklist are the same size
		if(this.getChecklist().size() != that.getChecklist().size()) {
			return false;
		}
		
		//Check that the ArrayList<String> checklist are equal
		for(int i = 0; i < this.getChecklist().size(); i++) {
			if(!this.getChecklist().get(i).equals(that.getChecklist().get(i))) {
				return false;
			}
		}
		
		//Check that the ArrayList<Boolean> isSelected are not null
		if(this.getIsSelected() == null || that.getIsSelected() == null) {
			return false;
		}
		
		//Check that the ArrayList<Boolean> isSelected are the same size
		if(this.getIsSelected().size() != that.getIsSelected().size()) {
			return false;
		}
		
		//Check that the ArrayList<Boolean> isSelected are equal
		for(int i = 0; i < this.getIsSelected().size(); i++) {
			if(this.getIsSelected().get(i) != that.getIsSelected().get(i)) {
				return false;
			}
		}
		
		return true;
	}

	public void editData()
	{
		// TODO Auto-generated method stub

	}

	public ArrayList<String> getChecklist()
	{
		return checklist;
	}

	public void setChecklist(ArrayList<String> checklist)
	{
		this.checklist = checklist;
	}

	public ArrayList<Boolean> getIsSelected()
	{
		return isSelected;
	}

	public void setIsSelected(ArrayList<Boolean> isSelected)
	{
		this.isSelected = isSelected;
	}
	
	
	

}
