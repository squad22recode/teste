package com.gestaoCash.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.repository.query.Param;

import com.gestaoCash.model.Expense;
import com.gestaoCash.model.Revenue;

public interface ExpenseService {
  void saveExpense(Expense expense);
  
  List<Expense> findExpenseAndUser(Long id);
  Stream<Expense> findExpenseFilterDate(LocalDate date, Long id);
  double calcTotalExpenses(List<Expense> expense);

  List<Expense> findAllExpense();

  Expense findExpenseById(Long id);

  void updateExpenseById(Long id, Expense updatedExpense);

  void deleteExpenseById(Long id);
}
