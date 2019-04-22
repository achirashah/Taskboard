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

@Controller
public class CompanyController {

	@Autowired
	ProjectDao projectDao;

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	CompanyDao companyDao;

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public String loadCompany(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("employee") == null) {
			return "redirect:login";
		} else {
			Employee employee = (Employee) session.getAttribute("employee");

			List<Employee> employees = companyDao.getEmployees(employee.getCompany().getId());
			List<Project> projects = companyDao.getProjects(employee.getCompany().getId());

			request.setAttribute("employees", employees);
			request.setAttribute("projects", projects);

			return "company";
		}
	}

	@RequestMapping(value = "/company", method = RequestMethod.POST)
	public String updateCompany(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		Integer idProject = Integer.parseInt(request.getParameter("idProject"));
		if (idProject == 0) {
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			Project project = new Project();
			project.setName(name);
			project.setDescription(description);
			project.setCompany(employee.getCompany());
			if (projectDao.insertProject(project)) {
				System.out.println("Inserted project!!");
				return "redirect:company";
			} else {
				request.setAttribute("infoProject", "The project wasn't created.");
				return "company";
			}
		} else {
			Project project = projectDao.getProjectForEmployee(idProject, employee.getCompany().getId());
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
