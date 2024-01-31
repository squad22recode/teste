package com.gestaoCash.servicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestaoCash.model.Revenue;
import com.gestaoCash.repositories.RevenueRepository;
import com.gestaoCash.services.RevenueService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RevenueServiceImpl implements RevenueService {


@Autowired
  private RevenueRepository revenueRepository;

@Override
@Transactional(readOnly = true)
  public List<Revenue> findRevenueAndUser(Long id) {
	  
	  return revenueRepository.findRevenueAndUser(id);
  }
  @Override
  
  public void saveRevenue(Revenue revenue) {
    this.revenueRepository.save(revenue);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Revenue> findAllRevenues() {
    return this.revenueRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Revenue findRevenueById(Long id) {
    var optionalRevenue = this.revenueRepository.findById(id);

    if (optionalRevenue.isPresent()) {
      var revenue = optionalRevenue.get();
      return revenue;
    } else {
      throw new EntityNotFoundException();
    }
  }

  @Override
  public void updateRevenueById(Long id, Revenue updatedRevenue) {
    this.saveRevenue(updatedRevenue);
  }

  @Override
  public void deleteRevenueById(Long id) {
    this.revenueRepository.deleteById(id);
  }
  
@Override
public Stream<Revenue> findRevenueFilterDate(LocalDate date, Long id) {
	return this.findRevenueAndUser(id).stream().filter(revenue -> revenue.getData().getMonth().equals(date.getMonth()));
	
}

@Override
public String calcTotalMonth(List<Revenue> revenue, int ano) {
	 List<Revenue> totaisPorMes = new ArrayList<>();
	 
	 for (int mes = 1; mes <= 12; mes++) {
		Revenue totalPorMes = new Revenue(); 
       double totalDoMes = calcTotalForMonth(revenue, mes);
       totalPorMes.setData(LocalDate.of(ano, mes, 1)); 
       totalPorMes.setValor(totalDoMes);       
       totaisPorMes.add(totalPorMes);
        
     }

     return transformarEmJSON(totaisPorMes);
	
}

private static double calcTotalForMonth(List<Revenue> revenue, int mes) {
    double total = 0.0;
    for (Revenue rev : revenue) {
        // Verifique se a transação está no mês e ano desejados
        if (rev.getData().getYear() == LocalDate.now().getYear() && rev.getData().getMonthValue() == mes) {
            total += rev.getValor();
        }
    }
    return total;
}

// Método para transformar a lista em JSON
private static String transformarEmJSON(List<Revenue> revenue) {
	StringBuilder jsonBuilder = new StringBuilder("[");

	 for (Revenue rev : revenue) {
         jsonBuilder.append("{\"data\":").append("\""+rev.getData()+"\"")
                 .append(",\"valor\":").append("\""+rev.getValor()+"\"").append("},");
     }
     if (!revenue.isEmpty()) {
         // Remova a última vírgula
         jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
     }
     jsonBuilder.append("]");

     return jsonBuilder.toString();
}
@Override
public double calcTotalRevenue(List<Revenue> revenue) {
	double value = 0;
	
	for (Revenue rev : revenue) {
		value += rev.getValor();
	}
	
	return value;
}
  
  
}
