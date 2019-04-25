package edu.neu.shah.taskboard.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.neu.shah.taskboard.dao.CompanyDao;
import edu.neu.shah.taskboard.dao.ProjectDao;
import edu.neu.shah.taskboard.dao.ReviewDao;
import edu.neu.shah.taskboard.dao.TaskDao;
import edu.neu.shah.taskboard.dao.UserDao;
import edu.neu.shah.taskboard.pojo.Project;
import edu.neu.shah.taskboard.pojo.ReviewModel;
import edu.neu.shah.taskboard.pojo.Task;
import edu.neu.shah.taskboard.pojo.User;

@Controller
public class ReviewFormController {

	public static Logger LOGGER = LogManager.getLogger(ReviewFormController.class);

	@Value("${page.size:5}")
	private int pageSize;

	@Autowired
	ProjectDao projectDao;

	@Autowired
	UserDao userDao;

	@Autowired
	CompanyDao companyDao;

	@Autowired
	TaskDao taskDao;

	@Autowired
	ReviewDao reviewDao;

	@GetMapping("/review")
	public String loadReviewOptionsForm(HttpServletRequest request, Model model) {
		User user = (User) request.getSession().getAttribute("user");
		request.getSession().setAttribute("reviewModel", null);
		request.setAttribute("currentPage", 0);
		request.setAttribute("pageCount", 0);
		request.setAttribute("tasks", null);

		// model.addAttribute("reviewModel", this.fetchReviewModel(user));
		request.setAttribute("reviewModel", this.fetchReviewModel(user));

		return "review";
	}

	@RequestMapping(value = "/review", method = RequestMethod.POST)
	public String processReviewRequest(HttpServletRequest request,
			@ModelAttribute("reviewModel") ReviewModel reviewModel) {

		LOGGER.info("inside review POST!!!");

		User user = (User) request.getSession().getAttribute("user");
		if ("DEV".equalsIgnoreCase(user.getRole())) {
			List<User> defaultUserList = new ArrayList<User>();
			defaultUserList.add(user);
			reviewModel.setUsers(defaultUserList);
		}

		int resultCount = reviewDao.getAllTasksCount(reviewModel);
		int pageCount = (int) Math.ceil((double) resultCount / pageSize);
		reviewModel.setPageCount(pageCount);
		List<Task> fetchedTasks = reviewDao.getAllTasksPaginated(reviewModel, 0);

		request.getSession().setAttribute("reviewModel", reviewModel);
		request.setAttribute("currentPage", 1);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("tasks", fetchedTasks);

		return "reviewresults";
	}

	@GetMapping("/reviewresults")
	public String loadResults(HttpServletRequest request, Model model) {
		ReviewModel reviewModel = (ReviewModel) request.getSession().getAttribute("reviewModel");
		if (reviewModel == null) {
			LOGGER.error("something is up!!!");
			return "error";
		}

		int requestedPage = (int) request.getAttribute("currentPage");

		int firstResult = requestedPage * pageSize;

		List<Task> fetchedTasks = reviewDao.getAllTasksPaginated(reviewModel, firstResult);

		request.setAttribute("currentPage", requestedPage);
		request.setAttribute("pageCount", reviewModel.getPageCount());
		request.setAttribute("tasks", fetchedTasks);

		return "reviewresults";
	}

	public ReviewModel fetchReviewModel(User user) {

		ReviewModel reviewModel = new ReviewModel();

		String role = user.getRole();
		// dev access to projects and tasks only
		if ("DEV".equalsIgnoreCase(role)) {
			reviewModel.setProjects(reviewDao.getAllProjectsForUser(user));
		}

		if ("LEAD".equalsIgnoreCase(role)) {
			List<Project> projects = reviewDao.getAllProjectsForUser(user);
			reviewModel.setProjects(projects);

			Set<User> users = new HashSet<>();
			for (Project project : projects) {
				users.addAll(project.getUsers());
			}
			LOGGER.info("Users found = {}", users.size());
			reviewModel.setUsers(new ArrayList<>(users));
		}

		// admin has cross cutting access (all access)
		if ("ADMIN".equalsIgnoreCase(role)) {
			reviewModel.setUsers(reviewDao.getAllUsers());
			// reviewModel.setCompanies(reviewDao.getAllCompanies());
			reviewModel.setProjects(reviewDao.getAllProjects());
		}
		LOGGER.info(reviewModel.toString());
		return reviewModel;
	}
}
