package edu.neu.shah.taskboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.neu.shah.taskboard.dao.EmployeeDao;
import edu.neu.shah.taskboard.dao.TaskDao;
import edu.neu.shah.taskboard.pojo.Employee;
import edu.neu.shah.taskboard.pojo.Task;

@Controller
public class ProfileController {
	@Autowired
	TaskDao taskDao;

	@Autowired
	EmployeeDao employeeDao;

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String loadProfile(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("employee") == null) {
			return "redirect:login";
		} else {
			Employee employee = (Employee) session.getAttribute("employee");

			List<Task> allTasks = employeeDao.getTasksByEmployee(employee.getId());
			List<Task> todoTasks = employeeDao.getTasksByCategory(allTasks, "todo");
			List<Task> doingTasks = employeeDao.getTasksByCategory(allTasks, "doing");
			List<Task> doneTasks = employeeDao.getTasksByCategory(allTasks, "done");

			int todoPercent = 0;
			int doingPercent = 0;
			int donePercent = 0;

			if (allTasks.size() != 0) {
				todoPercent = 100 * todoTasks.size() / allTasks.size();
				doingPercent = 100 * doingTasks.size() / allTasks.size();
				donePercent = 100 * doneTasks.size() / allTasks.size();
			}

			request.setAttribute("allTasks", allTasks);
			request.setAttribute("todoTasks", todoTasks);
			request.setAttribute("doingTasks", doingTasks);
			request.setAttribute("doneTasks", doneTasks);

			request.setAttribute("todoPercent", todoPercent);
			request.setAttribute("doingPercent", doingPercent);
			request.setAttribute("donePercent", donePercent);

			return "profile";
		}
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String updateProfile(HttpServletRequest request) {
		Integer idTask = Integer.valueOf(request.getParameter("idTask"));
		taskDao.changeCategory(idTask);
		return "redirect:profile";
	}

}