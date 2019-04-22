package edu.neu.shah.taskboard.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@Column(name = "nick", nullable = false)
	private String nick;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "avatar", nullable = false)
	private String avatar;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@ManyToOne
	@JoinColumn(name = "companies_id")
	private Company company;

	@ManyToMany
	private List<Project> listOfProjects;

	@OneToMany(mappedBy = "employee")
	private List<Task> listOfTask;

	public Employee() {
	}

	public Employee(String name, String surname, String nick, String password, String avatar, String email,
			Company company) {
		this.name = name;
		this.surname = surname;
		this.nick = nick;
		this.password = password;
		this.avatar = avatar;
		this.email = email;
		this.company = company;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", surname=" + surname + ", nick=" + nick + ", password="
				+ password + ", avatar=" + avatar + ", email=" + email + ", company=" + company + "]";
	}

}
