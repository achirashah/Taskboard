package edu.neu.shah.taskboard.controller;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.neu.shah.taskboard.dao.EmployeeDao;
import edu.neu.shah.taskboard.pojo.Employee;

@Controller
public class LoginController {
	@Autowired
	EmployeeDao employeeDao;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		System.out.println("email+password===\"" + email + "\"+\"" + password + "\"");
		password = employeeDao.encodePassword(password);
		System.out.println("encoded --> email+password===\"" + email + "\"+\"" + password + "\"");
		try {
			Employee employee = employeeDao.getEmployeeByEmailAndPassword(email, password);
			HttpSession session = request.getSession();
			session.setAttribute("employee", employee);
			return "redirect:profile";
		} catch (NoResultException e) {
			request.setAttribute("infoLogin", "Data in login form was invalid, RETRY.");
			return "login";
		}
	}

}
