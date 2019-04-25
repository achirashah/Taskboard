package edu.neu.shah.taskboard.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.shah.taskboard.pojo.Project;
import edu.neu.shah.taskboard.pojo.ReviewModel;
import edu.neu.shah.taskboard.pojo.Task;
import edu.neu.shah.taskboard.pojo.User;

@Repository
@Transactional
public class ReviewDao {

	public static final Logger LOGGER = LogManager.getLogger(ReviewDao.class);

	@Autowired
	private EntityManager entityManager;

	@Value("${page.size:5}")
	private int pageSize;

	@SuppressWarnings("unchecked")
	public List<Project> getAllProjects() {
		return entityManager.createQuery("SELECT p from Project p").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return entityManager.createQuery("SELECT u from User u").getResultList();
	}

	public List<Project> getAllProjectsForUser(User user) {
		return entityManager
				.createQuery("SELECT  p from Project p JOIN p.users u WHERE u.email = :email ", Project.class)
				.setParameter("email", user.getEmail()).getResultList();
	}

	public int getAllTasksCount(ReviewModel reviewModel) {
		long count = entityManager.createQuery(
				"SELECT  COUNT(t) from Task t WHERE t.user IN :users AND t.project IN :projects AND t.state IN :taskStates",
				Long.class).setParameter("users", reviewModel.getUsers())
				.setParameter("projects", reviewModel.getProjects())
				.setParameter("taskStates", reviewModel.getTaskStates()).getSingleResult();
		LOGGER.info("total results = {}", count);
		return (int) count;
	}

	public List<Task> getAllTasksPaginated(ReviewModel reviewModel, int firstResult) {
		TypedQuery<Task> paginatedQuery = entityManager.createQuery(
				"SELECT  t from Task t WHERE t.user IN :users AND t.project IN :projects AND t.state IN :taskStates",
				Task.class).setParameter("users", reviewModel.getUsers())
				.setParameter("projects", reviewModel.getProjects())
				.setParameter("taskStates", reviewModel.getTaskStates());

		paginatedQuery.setFirstResult(firstResult);
		paginatedQuery.setMaxResults(pageSize);
		return paginatedQuery.getResultList();
	}

}
