package edu.neu.shah.taskboard.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.neu.shah.taskboard.dao.CompanyDao;
import edu.neu.shah.taskboard.dao.ProjectDao;
import edu.neu.shah.taskboard.dao.TaskDao;
import edu.neu.shah.taskboard.dao.UserDao;
import edu.neu.shah.taskboard.pojo.Project;
import edu.neu.shah.taskboard.pojo.Task;
import edu.neu.shah.taskboard.pojo.User;

@Controller
public class AddTaskController {

	public final static Logger LOGGER = LogManager.getLogger(AddTaskController.class);

	@Autowired
	ProjectDao projectDao;

	@Autowired
	UserDao userDao;

	@Autowired
	CompanyDao companyDao;

	@Autowired
	TaskDao taskDao;

	@GetMapping("/addtask")
	public String loadAddTaskPage(HttpServletRequest request) {
		System.out.println("inside addTask load!!");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			return "redirect:login";
		} else if ("ADMIN".equalsIgnoreCase(user.getRole())) {
			return "error";
		} else {
			System.out.println("inside addTask load!!");
			List<User> users = new ArrayList<>();
			Integer idProject = Integer.parseInt(request.getParameter("idProject"));
			Project project = projectDao.getProjectForUser(idProject, user.getCompany().getId());
			if ("LEAD".equalsIgnoreCase(user.getRole())) {
				users.addAll(companyDao.getUsers(user.getCompany().getId()));
			} else {
				users.add(user);
			}
			request.setAttribute("users", users);
			request.setAttribute("project", project);

			return "addtask";
		}
	}

	@PostMapping("/addtask")
	public String addTask(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String state = request.getParameter("state");
		String content = request.getParameter("content");
		String email = request.getParameter("email");

		User user = userDao.getUserByEmail(email);
		User userFromSession = (User) session.getAttribute("user");
		Integer idProject = Integer.parseInt(request.getParameter("idProject"));
		Project project = projectDao.getProjectForUser(idProject, userFromSession.getCompany().getId());

		Task task = new Task();
		task.setState(state);
		task.setContent(content);
		task.setUser(user);
		task.setProject(project);

		if (taskDao.insertTask(task)) {
			if ("LEAD".equalsIgnoreCase(userFromSession.getRole())) {
				LOGGER.info("lead associated!!, user={}", userFromSession);
				project.addUser(userFromSession);
			}
			session.setAttribute("idProject", idProject);
			return "redirect:addtask?idProject=" + idProject;
		} else {
			request.setAttribute("infoTask", "The task wasn't added.");
			return "addtask";
		}
	}
}
