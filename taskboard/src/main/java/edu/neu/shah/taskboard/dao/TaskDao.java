package edu.neu.shah.taskboard.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.shah.taskboard.pojo.Task;

@Repository
public class TaskDao {
	@Autowired
	private EntityManager entityManager;

	@Transactional
	public boolean insertTask(Task task) {
		try {
			entityManager.persist(task);
			task.getProject().addTask(task);
			task.getUser().addTask(task);
			task.getProject().addUser(task.getUser());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public Task getTaskById(Integer taskId) {
		return (Task) entityManager.createQuery("SELECT t from Task t where t.id = :taskId")
				.setParameter("taskId", taskId).getSingleResult();
	}

	@Transactional
	public void changeState(Integer taskId) {
		Task task = this.getTaskById(taskId);
		int changedTasks = -1;
		switch (task.getState()) {
		case "todo":
			changedTasks = entityManager.createQuery("UPDATE Task t SET t.state = 'doing' WHERE t.id = :id")
					.setParameter("id", taskId).executeUpdate();
			break;
		case "doing":
			changedTasks = entityManager.createQuery("UPDATE Task t SET t.state = 'done' WHERE t.id = :id")
					.setParameter("id", taskId).executeUpdate();
			break;
		case "done":
			changedTasks = entityManager.createQuery("UPDATE Task t SET t.state = 'closed' WHERE t.id = :id")
					.setParameter("id", taskId).executeUpdate();
			break;
		default:
			System.out.println("Task should not have been listed!!");
			break;
		}
		if (changedTasks != 1) {
			System.out.println("task update did not work right!!");
		}

	}
}
