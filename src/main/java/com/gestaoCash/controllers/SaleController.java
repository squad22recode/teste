package com.gestaoCash.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestaoCash.model.Client;
import com.gestaoCash.model.ItemSale;
import com.gestaoCash.model.Product;
import com.gestaoCash.model.Sale;
import com.gestaoCash.model.Users;
import com.gestaoCash.repositories.ItemSaleRepository;
import com.gestaoCash.repositories.SaleRepository;
import com.gestaoCash.services.ClientService;
import com.gestaoCash.services.ProductService;
import com.gestaoCash.services.SaleService;
import com.gestaoCash.services.UserService;
import com.gestaoCash.utils.DataUserAuth;

import dto.SaleDto;

@Controller
@RequestMapping("/usuario/area-cliente")
public class SaleController {

	@Autowired
	private SaleService saleService;

	@Autowired
	private ProductService prodService;
	@Autowired
	private UserService userService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ItemSaleRepository itemRepo;

	private DataUserAuth data = new DataUserAuth();

	@PostMapping("/cadastrar-venda")
	public String saveSales(@ModelAttribute("sale") Sale sale, Model model) {
		sale.setEmpresa(data.DataUser().getEmpresa());
		sale.getItemSale().removeIf(Objects::isNull);

		double totalValue = calculateTotalValue(sale) ;
		double desconto = (double) sale.getDesconto()/100;
		sale.setValorTotal(totalValue-(desconto*totalValue));
		updateProductQuantities(sale);

		Sale savedSale = saleService.saveSale(sale);

		saveItemSales(savedSale);

		return "redirect:/usuario/area-cliente/empresa/vendas";

	}

	@GetMapping("/cadastrar-venda")
	public ModelAndView getRegisteSale() {
		ModelAndView view = new ModelAndView("/empresa/add-vendas");
		view.addObject("sale", new Sale());
		Users user = userService.findUserById(data.DataUser().getId());

		List<Product> products = prodService.findAllProducts().stream()
				.filter(product -> product.getEmpresa().getIdEmpresa() == user.getEmpresa().getIdEmpresa())
				.collect(Collectors.toList());
		List<Client> clients = clientService.findAllClient().stream()
				.filter(client -> client.getEmpresa().getIdEmpresa() == user.getEmpresa().getIdEmpresa())
				.collect(Collectors.toList());

		view.addObject("itemSale", new ArrayList<ItemSale>());
		view.addObject("product", new Product());
		view.addObject("clients", clients);
		view.addObject("products", products);
		return view;

	}
	@GetMapping("empresa/vendas")
	public ModelAndView getSales(@RequestParam(name = "filtro", defaultValue = "mes") String filter,
			@RequestParam(name = "filtro", defaultValue = "id") String sort,
			@RequestParam(required = false, name = "size", defaultValue = "10") String size,
			@RequestParam(required = false, name = "page", defaultValue = "0") String page) {
		ModelAndView view = new ModelAndView("/empresa/vendas");
		Pageable pagination = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), Sort.by(sort));
		List<Sale> sales = saleService.findAllSalesByCompanyAndFilter(data.DataUser().getEmpresa(), pagination, filter);
		
		view.addObject("sales", sales);
		view.addObject("page", Integer.parseInt(page));
	
		return view;
	}
	
	@GetMapping("empresa/editar-venda/{id}")
	public String editSale(@PathVariable long id, Model model) {
		Sale sale = saleService.findSaleById(id);
		List<Product> products = prodService.findAllProducts().stream()
				.filter(product -> product.getEmpresa().getIdEmpresa() == data.DataUser().getEmpresa().getIdEmpresa())
				.collect(Collectors.toList());
		List<Client> clients = clientService.findAllClient().stream()
				.filter(client -> client.getEmpresa().getIdEmpresa() == data.DataUser().getEmpresa().getIdEmpresa())
				.collect(Collectors.toList());
		
		model.addAttribute("sale", sale);
		model.addAttribute("products", products);
		model.addAttribute("clients", clients);
		
		return"/empresa/editar-venda";
	}
	
	@GetMapping("empresa/delete/{id}")
	public String deleteBySale(@PathVariable long id) {
		
		saleService.deleteSale(id);
		return"redirect:../vendas";
	}

	private void updateProductQuantities(Sale sale) {
		for (ItemSale item : sale.getItemSale()) {
				Product product = item.getProduto();
				product.setEstoque(item.getProduto().getEstoque() - item.getQuantidade());
				product.setVendido(item.getProduto().getVendido() + item.getQuantidade());
				prodService.saveProduct(product);
		}
	}

	private void saveItemSales(Sale sale) {
		for (ItemSale item : sale.getItemSale()) {
			ItemSale itemSale = item;
			itemSale.setVenda(sale);
			itemRepo.save(itemSale);
		}
	}

	private double calculateTotalValue(Sale sale) {
		double totalValue = 0;

		for (ItemSale item : sale.getItemSale()) {
			double subtotal = item.getQuantidade() * item.getProduto().getPreco();
			item.setSubtotal(subtotal);
			totalValue += subtotal;
		}
	
			return totalValue;
		
	}
}
