package com.gestaoCash.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestaoCash.model.Company;
import com.gestaoCash.model.Product;
import com.gestaoCash.services.ProductService;
import com.gestaoCash.utils.DataUserAuth;

import ch.qos.logback.classic.Logger;



@Controller
@RequestMapping("/usuario/area-cliente")
public class ProductController {

	@Autowired 
	private ProductService prodService;
	
	DataUserAuth userAuth = new DataUserAuth();
	
	@PostMapping("/cadastrar-produto")
	public String addProduct(@ModelAttribute("product") @Validated Product product,BindingResult bindingResult, Model model, Logger logger) {
		
	
			if (bindingResult.hasErrors()) {
			    
			    return "addProductModal :: modal-content";
			}
	
			try {
	            // Obter a empresa associada ao usuário autenticado
	            Company company = userAuth.DataUser().getEmpresa();

	            // Configurar a empresa no produto
	            product.setEmpresa(company);

	            // Salvar o produto no banco de dados
	            prodService.saveProduct(product);

	            // Mensagem de sucesso
	             model.addAttribute("successMessage", "Produto cadastrado com sucesso!");

	            return "redirect:/usuario/area-cliente/empresa";
	        } catch (DataAccessException e) {
	            // Tratamento de exceção - Pode ser um problema no banco de dados
	            model.addAttribute("errorMessage", "Erro ao cadastrar o produto. Por favor, tente novamente.");

	            // Log do erro (isso depende do seu logger)
	            logger.error("Erro ao cadastrar o produto", e.getMessage());
	            
	            return "addProductModal :: modal-content";
	        }
	}
}
