package edu.neu.shah.taskboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.neu.shah.taskboard.dao.CompanyDao;
import edu.neu.shah.taskboard.dao.UserDao;
import edu.neu.shah.taskboard.pojo.Company;
import edu.neu.shah.taskboard.pojo.User;

@Controller
public class RegistrationController {

	@Autowired
	UserDao userDao;

	@Autowired
	CompanyDao companyDao;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registrationPost(HttpServletRequest request, @ModelAttribute("user") User user) {
		System.out.println(user.toString());
		System.out.println("I am here");
		System.out.println("userDao==" + userDao);
		if (userDao.getUserByEmail(user.getEmail()) != null) {
			request.setAttribute("infoRegistration", "Email: " + user.getEmail() + " already exist.");
		} else {
			Company companyFound = userDao.getCompany(user.getCompany().getName());
			if (companyFound == null) {
				System.out.println("in the if block (NO exception!!) before inserting company!");
				Company company = user.getCompany();
				companyDao.insertCompany(company);
				System.out.println("company inserted: " + company.toString());
			} else {
				System.out.println("else block: ");
				System.out.println("found company: " + companyFound);
				user.setCompany(companyFound);
			}
			if (userDao.insertUser(user)) {
				request.setAttribute("infoRegistration", "Registration Completed!!! Welcome to Taskboard.");
			} else {
				request.setAttribute("infoRegistration", "Registration failed.");
			}
		}

		return "registration";
	}

}
