package edu.neu.shah.taskboard.controller;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.neu.shah.taskboard.dao.UserDao;
import edu.neu.shah.taskboard.pojo.User;

@Controller
public class LoginController {
	@Autowired
	UserDao userDao;

	@Value("${user.session.max.inactive:1800}")
	int maxInactiveInterval;

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		System.out.println("email+password===\"" + email + "\"+\"" + password + "\"");
		password = userDao.encodePassword(password);
		System.out.println("encoded --> email+password===\"" + email + "\"+\"" + password + "\"");
		try {
			User user = userDao.getUserByEmailAndPassword(email, password);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			session.setMaxInactiveInterval(maxInactiveInterval);
			return "redirect:profile";
		} catch (NoResultException e) {
			request.setAttribute("infoLogin", "Data in login form was invalid, RETRY.");
			return "login";
		}
	}

}
