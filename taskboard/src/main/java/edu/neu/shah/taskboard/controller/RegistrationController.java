package edu.neu.shah.taskboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.neu.shah.taskboard.dao.CompanyDao;
import edu.neu.shah.taskboard.dao.EmployeeDao;
import edu.neu.shah.taskboard.pojo.Company;
import edu.neu.shah.taskboard.pojo.Employee;

@Controller
public class RegistrationController {

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	CompanyDao companyDao;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registrationPost(HttpServletRequest request, @ModelAttribute("employee") Employee employee) {
		System.out.println(employee.toString());
		System.out.println("I am here");
		System.out.println("employeeDao==" + employeeDao);
		if (employeeDao.getEmployeeByEmail(employee.getEmail()) != null) {
			request.setAttribute("infoRegistration", "Email: " + employee.getEmail() + " already exist.");
		} else {
			List<Company> companyList = employeeDao.getCompany(employee.getCompany().getName());
			if (companyList.size() == 0) {
				System.out.println("in the if block (NO exception!!) before inserting company!");
				Company company = employee.getCompany();
				companyDao.insertCompany(company);
				System.out.println("company inserted: " + company.toString());
			} else {
				System.out.println("else block: ");
				Company foundCompany = companyList.get(0);
				System.out.println("found company: " + foundCompany);
				employee.setCompany(foundCompany);
			}
			if (employeeDao.insertEmployee(employee)) {
				request.setAttribute("infoRegistration", "Congratulations! You have just joined in the Taskboard.");
			} else {
				request.setAttribute("infoRegistration", "Registration failed.");
			}
		}

		return "registration";
	}

}
