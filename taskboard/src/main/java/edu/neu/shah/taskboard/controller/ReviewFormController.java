package edu.neu.shah.taskboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReviewFormController {

	@RequestMapping(value = "/review", method = RequestMethod.GET)
	public String registration(Model model) {

		return "review";
	}
}
