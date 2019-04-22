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
import edu.neu.shah.taskboard.dao.TaskDao;
import edu.neu.shah.taskboard.pojo.Employee;
import edu.neu.shah.taskboard.pojo.Project;
import edu.neu.shah.taskboard.pojo.Task;

@Controller
public class AddTaskController {

	@Autowired
	ProjectDao projectDao;

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	CompanyDao companyDao;

	@Autowired
	TaskDao taskDao;

	@RequestMapping(value = "/addtask", method = RequestMethod.GET)
	public String loadAddTaskPage(HttpServletRequest request) {
		System.out.println("inside addTask load!!");
		HttpSession session = request.getSession();
		if (session.getAttribute("employee") == null) {
			return "redirect:login";
		} else {
			System.out.println("inside addTask load!!");
			Employee employee = (Employee) session.getAttribute("employee");

			Integer idProject = Integer.parseInt(request.getParameter("idProject"));
			Project project = projectDao.getProjectForEmployee(idProject, employee.getCompany().getId());
			List<Employee> employees = companyDao.getEmployees(employee.getCompany().getId());

			request.setAttribute("employees", employees);
			request.setAttribute("project", project);

			return "addtask";
		}
	}

	@RequestMapping(value = "/addtask", method = RequestMethod.POST)
	public String addTask(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String category = request.getParameter("category");
		String content = request.getParameter("content");
		String email = request.getParameter("email");

		Employee employee = employeeDao.getEmployeeByEmail(email);
		Employee employeeFromSession = (Employee) session.getAttribute("employee");
		Integer idProject = Integer.parseInt(request.getParameter("idProject"));
		Project project = projectDao.getProjectForEmployee(idProject, employeeFromSession.getCompany().getId());

		Task task = new Task();
		task.setCategory(category);
		task.setContent(content);
		task.setEmployee(employee);
		task.setProject(project);

		if (taskDao.insertTask(task)) {
			session.setAttribute("idProject", idProject);
			return "redirect:addtask?idProject=" + idProject;
		} else {
			request.setAttribute("infoTask", "The task wasn't added.");
			return "addtask";
		}
	}
}
