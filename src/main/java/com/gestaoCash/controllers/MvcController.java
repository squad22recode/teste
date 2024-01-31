package com.gestaoCash.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MvcController {

	@GetMapping("/sobre")
	public String getSobre() {
		
		return"sobre";
	}
	
	@GetMapping("/duvidas")
	public String getHelp() {
		
		return"duvidas";
	}
	
	@GetMapping("/contato")
	public String getContact() {
		
		return"contato";
	}
	
	
}
