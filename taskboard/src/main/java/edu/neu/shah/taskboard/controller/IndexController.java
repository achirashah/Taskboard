package edu.neu.shah.taskboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = { "/" })
public class IndexController {

	@RequestMapping(value = { "/", "/index*" })
	public String index(Model model) {
		return "index";
	}
}
