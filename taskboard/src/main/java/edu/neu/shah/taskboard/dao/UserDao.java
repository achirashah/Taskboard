package edu.neu.shah.taskboard.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.shah.taskboard.pojo.Company;
import edu.neu.shah.taskboard.pojo.Task;
import edu.neu.shah.taskboard.pojo.User;

@Repository
@Transactional
public class UserDao {

	public static Logger LOGGER = LogManager.getLogger(UserDao.class);

	@Autowired
	private EntityManager entityManager;

	public boolean insertUser(User user) {
		try {
			user.setPassword(this.encodePassword(user.getPassword()));
			entityManager.persist(user);
			user.getCompany().addUser(user);
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

	public User getUserByEmail(final String email) {
		try {
			return (User) entityManager.createQuery("SELECT u from User u WHERE u.email = :email")
					.setParameter("email", email).getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public User getUserByUserId(final String userId) {
		try {
			return (User) entityManager.createQuery("SELECT u from User u WHERE u.userId = :userId")
					.setParameter("userId", userId).getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public User getUserByEmailAndPassword(final String email, final String password) {
		return (User) entityManager
				.createQuery("SELECT u from User u WHERE u.email = :email AND u.password = " + ":password")
				.setParameter("email", email).setParameter("password", password).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Task> getTasksByUser(final Integer userId) {
		return entityManager.createQuery("SELECT t from Task t WHERE t.user.id = :userId")
				.setParameter("userId", userId).getResultList();
	}

	public List<Task> getTasksByState(List<Task> tasks, final String state) {
		List<Task> taskByState = new ArrayList<Task>();
		for (Task task : tasks) {
			if (state.equals(task.getState())) {
				taskByState.add(task);
			}
		}
		return taskByState;
	}

	public Company getCompany(final String name) {
		try {
			return (Company) entityManager.createQuery("SELECT c from Company c WHERE c.name = :name")
					.setParameter("name", name).getSingleResult();
		} catch (Exception e) {
			LOGGER.warn("Company by name={} does not exist. Exception raised={}", name, e.getMessage());
			return null;
		}
	}

}
