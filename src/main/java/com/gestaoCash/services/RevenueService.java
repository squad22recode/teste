package com.gestaoCash.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import com.gestaoCash.model.Revenue;

public interface RevenueService {
	void saveRevenue(Revenue revenue);

	List<Revenue> findAllRevenues();

	List<Revenue> findRevenueAndUser(Long id);

	Stream<Revenue> findRevenueFilterDate(LocalDate date, Long id);
	double calcTotalRevenue(List<Revenue> revenue);

	String calcTotalMonth(List<Revenue> revenue, int ano);

	Revenue findRevenueById(Long id);

	void updateRevenueById(Long id, Revenue updatedRevenue);

	void deleteRevenueById(Long id);
}
