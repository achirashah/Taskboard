package edu.neu.shah.taskboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.neu.shah.taskboard.dao.CompanyDao;
import edu.neu.shah.taskboard.dao.ProjectDao;
import edu.neu.shah.taskboard.dao.UserDao;
import edu.neu.shah.taskboard.pojo.Project;
import edu.neu.shah.taskboard.pojo.Task;
import edu.neu.shah.taskboard.pojo.User;

@Controller
public class ProjectController {

	@Autowired
	ProjectDao projectDao;

	@Autowired
	UserDao userDao;

	@Autowired
	CompanyDao companyDao;

	@RequestMapping(value = "/project", method = RequestMethod.POST)
	public String loadProject(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			return "redirect:login";
		} else {
			User user = (User) session.getAttribute("user");

			Integer idProject = Integer.parseInt(request.getParameter("idProject"));
			Project project = projectDao.getProjectForUser(idProject, user.getCompany().getId());
			request.setAttribute("project", project);

			List<Task> allTasks = projectDao.getAllTasks(idProject);
			List<Task> todoTasks = projectDao.getTasksByState(allTasks, "todo");
			List<Task> doingTasks = projectDao.getTasksByState(allTasks, "doing");
			List<Task> doneTasks = projectDao.getTasksByState(allTasks, "done");

			request.setAttribute("allTasks", allTasks);
			request.setAttribute("todoTasks", todoTasks);
			request.setAttribute("doingTasks", doingTasks);
			request.setAttribute("doneTasks", doneTasks);

			return "project";
		}
	}
}
