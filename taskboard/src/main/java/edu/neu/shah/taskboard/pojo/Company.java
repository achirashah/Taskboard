package edu.neu.shah.taskboard.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "companies")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "company")
	private Set<Project> projects = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "company")
	private Set<User> users = new HashSet<>();

	public Company() {

	}

//	public Company(int id, String name, Set<Project> projects, Set<User> users) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.projects = projects;
//		this.users = users;
//	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Company(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Company [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", projects=");
		builder.append(projects.size());
		builder.append(", users=");
		builder.append(users.size());
		builder.append("]");
		return builder.toString();
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void addProject(Project project) {
		this.projects.add(project);
	}

	public void addUser(User user) {
		this.users.add(user);
	}

}
