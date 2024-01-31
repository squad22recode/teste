package com.gestaoCash.controllers;

	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

	import com.gestaoCash.enums.CategoryEnum;
import com.gestaoCash.model.Revenue;
import com.gestaoCash.services.RevenueService;
import com.gestaoCash.utils.DataUserAuth;


	@Controller
	@RequestMapping("/usuario/area-cliente")
	public class RevenueController {
	
		@Autowired
		private RevenueService revenueService;
		DataUserAuth data = new DataUserAuth();
		
		
		@GetMapping("/receita/delete/{id}")
		public ModelAndView deleteRevenue(@PathVariable long id) {
			ModelAndView model = new ModelAndView("redirect:/usuario/area-cliente");
				revenueService.deleteRevenueById(id);					
			return model;
		}
		
		@GetMapping("receita/editar/{id}")
		public String editRevenue(@PathVariable long id,Model model){
			CategoryEnum[] cats = CategoryEnum.values();
			model.addAttribute("cats",cats);
			model.addAttribute("revenue", revenueService.findRevenueById(id));
			return "/usuario/receita/edit-receita";
		}
		
		@PostMapping("receita/editar/{id}")
		public ModelAndView editRevenue(@PathVariable long id, @ModelAttribute Revenue revenue) {
			ModelAndView model = new ModelAndView("redirect:/usuario/area-cliente");
			revenueService.updateRevenueById(id, revenue);
			return model;
		}

	}

