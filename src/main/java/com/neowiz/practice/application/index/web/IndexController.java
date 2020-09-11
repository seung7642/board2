package com.neowiz.practice.application.index.web;

import com.neowiz.practice.application.index.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: SeungHo Kyoung (daygoods@neowiz.com)
 * Date: 2020.01.09
 * Time: 17:58
 */
@Controller
public class IndexController {

	private final IndexService indexService;

	@Autowired
	public IndexController(IndexService indexService) {
		this.indexService = indexService;
	}

	@GetMapping(value = "/")
	public ModelAndView index(ModelAndView mnv) {

		mnv.addObject("message", indexService.getMessage());
		mnv.setViewName("index");
		return mnv;
	}

	@GetMapping(value = "/editor")
	public ModelAndView editor(ModelAndView mnv) {

		mnv.setViewName("editor");
		return mnv;
	}

}
