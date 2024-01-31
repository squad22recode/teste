package com.gestaoCash.servicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestaoCash.model.Client;
import com.gestaoCash.model.Company;
import com.gestaoCash.model.ItemSale;
import com.gestaoCash.model.Product;
import com.gestaoCash.model.Revenue;
import com.gestaoCash.model.Sale;
import com.gestaoCash.repositories.ItemSaleRepository;
import com.gestaoCash.repositories.SaleRepository;
import com.gestaoCash.services.ClientService;
import com.gestaoCash.services.ProductService;
import com.gestaoCash.services.SaleService;

import dto.FinanceDto;
import dto.SaleDto;
import dto.SaleJsondto;
import dto.teste;
import jakarta.transaction.Transactional;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleRepository saleRepo;
	@Autowired
	private ProductService prodService;

	@Autowired
	private ItemSaleRepository itemRepo;
	@Autowired
	private ClientService clientServcie;

	@Override
	public Sale saveSale(Sale sale) {
		Sale saleSaved = saleRepo.save(sale);

		return saleSaved;
	}

	@Transactional
	@Override
	public List<Sale> findAllSaleByClienteByCompany(Client cliente, Company company) {

		List<Sale> sales = saleRepo.findAllSaleByCliente(cliente).stream()
				.filter(client -> client.getCliente().getEmpresa().getIdEmpresa() == company.getIdEmpresa())
				.collect(Collectors.toList());

		return sales;
	}

	@Override
	public Sale findSaleById(long id) {
		return saleRepo.findById(id).get();
	}


	@Override
	public List<Sale> findAllSalesByCompany(Company company, String filterDate) {

		List<Sale> sales = saleRepo.findAllSaleByEmpresa(company);

//		if (filterDate.equalsIgnoreCase("hoje")) {
//			sales = sales.stream().filter(sale -> sale.getData().equals(LocalDate.now())).collect(Collectors.toList());
//		} else if (filterDate.equalsIgnoreCase("ano")) {
//			sales = sales.stream().filter(sale -> sale.getData().getYear() == LocalDate.now().getYear())
//					.collect(Collectors.toList());
//
//		} else if (filterDate.equalsIgnoreCase("mes")) {
//			sales = sales.stream().filter(sale -> sale.getData().getMonth() == LocalDate.now().getMonth())
//					.collect(Collectors.toList());
//
//		} else {
//			sales = sales.stream().filter(sale -> sale.getData().getMonth() == LocalDate.now().getMonth())
//					.collect(Collectors.toList());
//		}

//		if (pageable == null) {
//			sales = saleRepo.findAllSaleByEmpresa(company);
//		} else {
//			sales = saleRepo.findAllSaleByEmpresa(company, pageable);
//		}

		return this.filter(sales, filterDate);
	}
	
	@Override
	public List<Sale> findAllSalesByCompanyAndFilter(Company company, Pageable pageable, String filterDate) {

		List<Sale> sales = saleRepo.findAllSaleByEmpresa(company, pageable);
		sales = this.filter(sales, filterDate);

//		if (pageable == null) {
//			sales = saleRepo.findAllSaleByEmpresa(company);
//		} else {
//			sales = saleRepo.findAllSaleByEmpresa(company, pageable);
//		}

		return sales;
	}
	

	@Override
	public int calcPercentDate(List<Sale> sales, String dateType) {
		int valor;

		for (Sale sale : sales) {

			if (dateType == "mes") {

			} else if (dateType == "ano") {

			} else if (dateType == "hoje") {

			}

		}

		return 0;
	}

	@Override
	public String converterListToJson(List<Sale> sales, String dataFilter) {

		StringBuilder jsonBuilder = new StringBuilder("[");
		List<Client> clients = clientServcie.findAllClient();		

		for (Sale sale : sales) {
			
			if (dataFilter.equalsIgnoreCase("hoje")) {
				clients = clients.stream().filter(client -> client.getCreatedAt() == sale.getData())
						.collect(Collectors.toList());

			} else if (dataFilter.equalsIgnoreCase("mes")) {
				clients = clients.stream()
						.filter(client -> client.getCreatedAt().getMonthValue() == sale.getData().getMonthValue())
						.collect(Collectors.toList());
				
	
			} else if (dataFilter.equalsIgnoreCase("ano")) {
				clients = clients.stream().filter(client -> client.getCreatedAt().getYear() == sale.getData().getYear())
						.collect(Collectors.toList());
			} else {
				clients = clients.stream()
						.filter(client -> client.getCreatedAt().getMonthValue() == sale.getData().getMonthValue())
						.collect(Collectors.toList());
			}
		}
		
		List<Sale> sale2 = this.processSales(sales);
		
		for (Sale sale : sale2) {
			jsonBuilder.append("{\"dataVenda\":").append("\"" + sale.getData().toString() + "\"")
			.append(",\"venda\":").append("\"" + sales.size() + "\"")
			.append(",\"receita\":").append("\"" + sale.getValorTotal() + "\"")
			.append(",\"cliente\":").append("\"" + clients.size() + "\"").append("},");
		}

		if (!sales.isEmpty()) {
			// Remova a última vírgula
			jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
		}
		jsonBuilder.append("]");

		return jsonBuilder.toString();
	}
	
	public List<Sale> processSales(List<Sale> sales) {
        Map<LocalDate, Sale> saleMap = new HashMap<>();

        for (Sale sale : sales) {
            LocalDate dateKey = sale.getData();
            Sale existingSale = saleMap.get(dateKey);

            if (existingSale == null) {
                saleMap.put(dateKey, sale);
            } else {
                existingSale.setValorTotal(existingSale.getValorTotal() + sale.getValorTotal());
            
                
                
            }
        }

        return new ArrayList<>(saleMap.values());
    }
	
	public List<FinanceDto> processFinance(List<Sale> sales) {
		List<FinanceDto> finance = new ArrayList<>();
		
		for (Sale sale : sales) {
			FinanceDto  f = new FinanceDto();
			f.setId(sale.getId());
			f.setExpense(calcExpense(sale.getItemSale()));
			f.setRevenue(sale.getValorTotal());
			finance.add(f);
		}
		
		return finance;
    }

	public double calcExpense(List<ItemSale> itemSale) {
		double value = 0;
		for (ItemSale item : itemSale) {
			value += item.getProduto().getCusto();
		}
		return value;
	}
	
	@Override
	public List<FinanceDto> controlFinance(List<Sale> sales) {
		
		return this.processFinance(sales);
	}

	@Override
	public int countSales(Company company, String filter) {
		List<Sale> sales = saleRepo.findAllSaleByEmpresa(company);
		return this.filter(sales, filter).size();
	}
	
	public List<Sale> filter(List<Sale> sales, String filter){
		
		if (filter.equalsIgnoreCase("hoje")) {
			sales = sales.stream().filter(sale -> sale.getData().isEqual(LocalDate.now())).collect(Collectors.toList());
		} else if (filter.equalsIgnoreCase("ano")) {
			sales = sales.stream().filter(sale -> sale.getData().getYear()== LocalDate.now().getYear()).collect(Collectors.toList());
		} else if (filter.equalsIgnoreCase("mes")) {
			sales = sales.stream().filter(sale -> sale.getData().getMonth() == LocalDate.now().getMonth() && sale.getData().getYear() == LocalDate.now().getYear())
					.collect(Collectors.toList());
		} else {
			sales = sales.stream().filter(sale -> sale.getData().getMonth() == LocalDate.now().getMonth())
					.collect(Collectors.toList());
		}
		return sales;
	}

	@Override
	public void deleteSale(long id) {
		saleRepo.deleteById(id);
		
	}

}
