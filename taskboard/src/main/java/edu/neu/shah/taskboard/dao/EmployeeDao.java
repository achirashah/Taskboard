package edu.neu.shah.taskboard.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.shah.taskboard.pojo.Company;
import edu.neu.shah.taskboard.pojo.Employee;
import edu.neu.shah.taskboard.pojo.Task;

@Repository
@Transactional
public class EmployeeDao {
	@Autowired
	private EntityManager entityManager;

	public boolean insertEmployee(Employee employee) {
		try {
			employee.setPassword(this.encodePassword(employee.getPassword()));
			entityManager.persist(employee);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String encodePassword(String passwordToHash) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(passwordToHash.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++)
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public Employee getEmployeeByEmail(final String email) {
		try {
			return (Employee) entityManager.createQuery("SELECT e from Employee e WHERE e.email = :email")
					.setParameter("email", email).getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Employee getEmployeeByNick(final String nick) {
		try {
			return (Employee) entityManager.createQuery("SELECT e from Employee e WHERE e.nick = :nick")
					.setParameter("nick", nick).getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Employee getEmployeeByEmailAndPassword(final String email, final String password) {
		return (Employee) entityManager
				.createQuery("SELECT e from Employee e WHERE e.email = :email AND e.password = " + ":password")
				.setParameter("email", email).setParameter("password", password).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Task> getTasksByEmployee(final Integer employeeId) {
		return entityManager.createQuery("SELECT t from Task t WHERE t.employee.id = :employeeId")
				.setParameter("employeeId", employeeId).getResultList();
	}

	public List<Task> getTasksByCategory(List<Task> tasks, final String category) {
		List<Task> taskByCategory = new ArrayList<Task>();
		for (Task task : tasks) {
			if (category.equals(task.getCategory())) {
				taskByCategory.add(task);
			}
		}
		return taskByCategory;
	}

	@SuppressWarnings("unchecked")
	public List<Company> getCompany(final String name) {
		return entityManager.createQuery("SELECT c from Company c WHERE c.name = :name").setParameter("name", name)
				.getResultList();
	}
}
