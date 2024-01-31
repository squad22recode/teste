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
import com.gestaoCash.model.Expense;
import com.gestaoCash.services.ExpenseService;
import com.gestaoCash.utils.DataUserAuth;


@Controller
@RequestMapping("/usuario/area-cliente")
public class ExpenseController {
	@Autowired
	private ExpenseService expenseService;
	DataUserAuth data = new DataUserAuth();
	
	
	@GetMapping("/despesa/delete/{id}")
	public ModelAndView deleteExpense(@PathVariable long id) {
		ModelAndView model = new ModelAndView("redirect:/usuario/area-cliente");
			expenseService.deleteExpenseById(id);					
		return model;
	}
	
	@GetMapping("despesa/editar/{id}")
	public String editExpense(@PathVariable long id,Model model){
		CategoryEnum[] cats = CategoryEnum.values();
		model.addAttribute("cats",cats);
		model.addAttribute("expense", expenseService.findExpenseById(id));
		return "/usuario/despesa/edit-despesa";
	}
	
	@PostMapping("despesa/editar/{id}")
	public ModelAndView editExpense(@PathVariable long id, @ModelAttribute Expense expense) {
		ModelAndView model = new ModelAndView("redirect:/usuario/area-cliente");
		expenseService.updateExpenseById(id, expense);
		return model;
	}

}
