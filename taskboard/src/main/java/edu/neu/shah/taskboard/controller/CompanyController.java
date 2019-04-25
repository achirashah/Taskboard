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
import edu.neu.shah.taskboard.pojo.User;

@Controller
public class CompanyController {

	@Autowired
	ProjectDao projectDao;

	@Autowired
	UserDao userDao;

	@Autowired
	CompanyDao companyDao;

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public String loadCompany(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			return "redirect:login";
		} else {
			User user = (User) session.getAttribute("user");

			List<User> users = companyDao.getUsers(user.getCompany().getId());
			List<Project> projects = companyDao.getProjects(user.getCompany().getId());

			request.setAttribute("users", users);
			request.setAttribute("projects", projects);

			return "company";
		}
	}

	@RequestMapping(value = "/company", method = RequestMethod.POST)
	public String updateCompany(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Integer idProject = Integer.parseInt(request.getParameter("idProject"));
		if (idProject == 0) {
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			Project project = new Project();
			project.setName(name);
			project.setDescription(description);
			project.setCompany(user.getCompany());
			if (projectDao.insertProject(project)) {
				System.out.println("Inserted project!!");
				return "redirect:company";
			} else {
				request.setAttribute("infoProject", "The project wasn't created.");
				return "company";
			}
		} else {
			Project project = projectDao.getProjectForUser(idProject, user.getCompany().getId());
			if (project == null) {
				request.setAttribute("infoProject", "The project was deleted before.");
				return "company";
			} else if (projectDao.deleteProject(project))
				return "redirect:company";
			else {
				request.setAttribute("infoProject", "The project wasn't deleted.");
				return "company";
			}
		}
	}

}
