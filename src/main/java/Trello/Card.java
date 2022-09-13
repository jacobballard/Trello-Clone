package Trello;

import java.util.Set;

public interface Card
{
	boolean createLabel(String name);
	boolean deleteLabel(String name);
	boolean addComponent(Component component);
	boolean updateName(String newName);
	boolean addMember(User member);
	boolean removeMember(User member);
	String getName();
	Set<String> getLabels();
	Set<User> getMembers();
	Set<Component> getComponents();
}
