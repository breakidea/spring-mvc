package com.breakidea.noah.web.module;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@RequestMapping("/welcome")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("/welcome");

		mv.addObject("status", 0);
		mv.addObject("statusInfo", "OK");

		return mv;
	}
}