package edu.neu.shah.taskboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.neu.shah.taskboard.dao.TaskDao;
import edu.neu.shah.taskboard.dao.UserDao;
import edu.neu.shah.taskboard.pojo.Task;
import edu.neu.shah.taskboard.pojo.User;

@Controller
public class ProfileController {
	@Autowired
	TaskDao taskDao;

	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String loadProfile(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			return "redirect:login";
		} else {
			User user = (User) session.getAttribute("user");

			List<Task> allTasks = userDao.getTasksByUser(user.getId());
			List<Task> todoTasks = userDao.getTasksByState(allTasks, "todo");
			List<Task> doingTasks = userDao.getTasksByState(allTasks, "doing");
			List<Task> doneTasks = userDao.getTasksByState(allTasks, "done");

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
		taskDao.changeState(idTask);
		return "redirect:profile";
	}

}