package edu.neu.shah.taskboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.neu.shah.taskboard.dao.CompanyDao;
import edu.neu.shah.taskboard.dao.EmployeeDao;
import edu.neu.shah.taskboard.dao.ProjectDao;
import edu.neu.shah.taskboard.pojo.Employee;
import edu.neu.shah.taskboard.pojo.Project;
import edu.neu.shah.taskboard.pojo.Task;

@Controller
public class ProjectController {

	@Autowired
	ProjectDao projectDao;

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	CompanyDao companyDao;

	@RequestMapping(value = "/project", method = RequestMethod.POST)
	public String loadProject(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("employee") == null) {
			return "redirect:login";
		} else {
			Employee employee = (Employee) session.getAttribute("employee");

			Integer idProject = Integer.parseInt(request.getParameter("idProject"));
			Project project = projectDao.getProjectForEmployee(idProject, employee.getCompany().getId());
			request.setAttribute("project", project);

			List<Task> allTasks = projectDao.getAllTasks(idProject);
			List<Task> todoTasks = projectDao.getTasksByCategory(allTasks, "todo");
			List<Task> doingTasks = projectDao.getTasksByCategory(allTasks, "doing");
			List<Task> doneTasks = projectDao.getTasksByCategory(allTasks, "done");

			request.setAttribute("allTasks", allTasks);
			request.setAttribute("todoTasks", todoTasks);
			request.setAttribute("doingTasks", doingTasks);
			request.setAttribute("doneTasks", doneTasks);

			return "project";
		}
	}
}
