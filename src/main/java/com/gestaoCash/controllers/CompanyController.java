package com.gestaoCash.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.type.descriptor.java.LocalDateJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.DatatypeFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gestaoCash.configs.ListSaleTypeAdapter;
import com.gestaoCash.enums.StateEnum;
import com.gestaoCash.model.AddressCompany;
import com.gestaoCash.model.Client;
import com.gestaoCash.model.Company;
import com.gestaoCash.model.Product;
import com.gestaoCash.model.Sale;
import com.gestaoCash.model.Users;
import com.gestaoCash.repositories.ProductRepository;
import com.gestaoCash.repositories.SaleRepository;
import com.gestaoCash.services.ClientService;
import com.gestaoCash.services.CompanyService;
import com.gestaoCash.services.ProductService;
import com.gestaoCash.services.SaleService;
import com.gestaoCash.services.UserService;
import com.gestaoCash.utils.DataUserAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("usuario/area-cliente")
public class CompanyController {

	@Autowired
	private CompanyService compService;
	@Autowired
	private UserService userService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ProductService prodService;
	@Autowired
	private SaleService saleService;
	@Autowired
	private SaleRepository saleRepo;
	DataUserAuth data = new DataUserAuth();

	@GetMapping("/empresa")
	public ModelAndView getAllCompany(
			@RequestParam(name = "filtro", defaultValue = "mes") String filter,
			@RequestParam(name = "tipo", required = false, defaultValue = "nenhum") String type,
			@RequestParam(required = false, name = "size", defaultValue = "10") String size,
			@RequestParam(required = false, name = "page", defaultValue = "0") String page)
			throws JsonProcessingException {

		ModelAndView view = new ModelAndView("/empresa/empresa");
		Long id = data.DataUser().getId();
		Users user = userService.findUserById(id);

		view.addObject("companyEdit", user.getEmpresa());

		List<Client> clients = clientService.finAllClientByCompany(user.getEmpresa(),PageRequest.of(0, 10, Sort.by("nome")), filter);
		view.addObject("clients", clients);
		view.addObject("countClients", clientService.countClient(user.getEmpresa(),filter));

		//retorna dados de vendas/sales
		// tratamento de dados das vendas
				List<Sale> salesFilterPagination = saleService.findAllSalesByCompanyAndFilter(data.DataUser().getEmpresa(),PageRequest.of(0, 10, Sort.by("id")), filter);
				List<Sale> salesFilter = saleService.findAllSalesByCompany(data.DataUser().getEmpresa(), filter);
				view.addObject("salesFilter", salesFilter);
				view.addObject("countSales", saleService.countSales(user.getEmpresa(), filter));
		
		
		
		List<Product> products;

		if (type.equalsIgnoreCase("estoque")) {
			products = prodService.findAllByEmpresa(user.getEmpresa(),
					PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), Sort.by("estoque")));
		} else {
			products = prodService.findAllByEmpresa(user.getEmpresa(), PageRequest.of(0, 10, Sort.by("estoque")));
		}

		List<Product> productsTopSale;
		if (type.equalsIgnoreCase("mais-vendido")) {
			productsTopSale = prodService.findAllByEmpresa(user.getEmpresa(),
					PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), Sort.by("vendido").descending()));
		} else {
			productsTopSale = prodService.findAllByEmpresa(user.getEmpresa(),
					PageRequest.of(0, 10, Sort.by("vendido").descending()));
		}

//		List<Sale> salesFilter = saleService.findAllSalesByCompany(data.DataUser().getEmpresa(), null, filter);
//		List<Sale> salesPagination;
		Pageable paginationSales = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), Sort.by("cliente"));

		//if (type.equalsIgnoreCase("venda")) {
		
//		} else {
//			salesPagination = saleService.findAllSalesByCompanyAndFilter(data.DataUser().getEmpresa(),
//					PageRequest.of(0, 10, Sort.by("cliente")), filter);
//		}
		

		// dados de receita
		double revenue = 0;
		for (Sale sale : salesFilter) {
			revenue += sale.getValorTotal();
		}

		// string de localização
		String filterString = "";

		if (filter.equalsIgnoreCase("mes")) {
			filterString = "Este mês";
		} else if (filter.equalsIgnoreCase("hoje")) {
			filterString = "Hoje";
		} else if (filter.equalsIgnoreCase("ano")) {
			filterString = "Este ano";
		}	

		view.addObject("jsonSale", saleService.converterListToJson(salesFilter, filter));
		view.addObject("page", page);
		view.addObject("finances", saleService.controlFinance(salesFilter));
		view.addObject("filterString", filterString);
		view.addObject("revenue", revenue);
		view.addObject("salesPagination", salesFilterPagination);
		view.addObject("countProduct", prodService.contTotalProduct());
		view.addObject("products", products);
		view.addObject("productsTopSale", productsTopSale);
		view.addObject("product", new Product());
		view.addObject("states", StateEnum.values());
		view.addObject("user", user);
		view.addObject("client", new Client());
		view.addObject("company", new Company());
		return view;
	}

	@PostMapping("/cadastrar-empresa")
	public String registerCompany(@ModelAttribute("company") Company company) {

		long id = data.DataUser().getId();
		Users usuario = userService.findUserById(id);

		company.setUsuario(usuario);

		usuario.setEmpresa(compService.saveCompany(company));
		userService.saveUser(usuario);

		return "redirect:/usuario/area-cliente/empresa";
	}

	@PostMapping("/editar-empresa")
	public String editCompany(@ModelAttribute("company") Company company) {

		long id = data.DataUser().getId();
		Users usuario = userService.findUserById(id);
		compService.findCompanyById(usuario.getEmpresa().getIdEmpresa());
		company.setIdEmpresa(usuario.getEmpresa().getIdEmpresa());
		company.setUsuario(usuario);

		compService.saveCompany(company);
		userService.saveUser(usuario);
		return "redirect:/usuario/area-cliente/empresa";
	}

}
