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
	public void changeCategory(Integer taskId) {
		Task task = this.getTaskById(taskId);
		int changedTasks = -1;
		switch (task.getCategory()) {
		case "todo":
			changedTasks = entityManager.createQuery("UPDATE Task t SET t.category = 'doing' WHERE t.id = :id")
					.setParameter("id", taskId).executeUpdate();
			break;
		case "doing":
			changedTasks = entityManager.createQuery("UPDATE Task t SET t.category = 'done' WHERE t.id = :id")
					.setParameter("id", taskId).executeUpdate();
			break;
		case "done":
			changedTasks = entityManager.createQuery("DELETE FROM Task t WHERE t.id = :id").setParameter("id", taskId)
					.executeUpdate();
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
