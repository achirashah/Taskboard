package edu.neu.shah.taskboard.pojo;

import java.util.Arrays;
import java.util.List;

public class ReviewModel {

	private List<Project> projects;
	private List<User> users;
	private List<String> taskStates = Arrays.asList("todo", "doing", "done", "closed");
	private int pageCount;

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<String> getTaskStates() {
		return taskStates;
	}

	public void setTaskStates(List<String> taskStates) {
		this.taskStates = taskStates;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReviewModel [pageCount=");
		builder.append(pageCount);
		builder.append(", projects=");
		builder.append(projects);
		builder.append(", users=");
		builder.append(users);
		builder.append(", taskstates=");
		builder.append(taskStates);
		builder.append("]");
		return builder.toString();
	}

}
