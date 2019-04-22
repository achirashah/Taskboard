package edu.neu.shah.taskboard.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.shah.taskboard.pojo.Project;
import edu.neu.shah.taskboard.pojo.Task;

@Repository
public class ProjectDao {

	@Autowired
	private EntityManager entityManager;

	@Transactional
	public boolean insertProject(Project project) {
		try {
			entityManager.persist(project);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean deleteProject(Project project) {
		try {
			entityManager.remove(project);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Project getProjectForEmployee(final Integer projectId, final Integer companyId) {
		try {
			return (Project) entityManager
					.createQuery("SELECT p from Project p WHERE p.id = :projectId AND p.company.id" + " = :companyId")
					.setParameter("projectId", projectId).setParameter("companyId", companyId).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Task> getAllTasks(final Integer projectId) {
		return entityManager.createQuery("SELECT t from Task t WHERE t.project.id = :projectId")
				.setParameter("projectId", projectId).getResultList();
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
}
