package com.gestaoCash.servicesImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestaoCash.model.Expense;
import com.gestaoCash.repositories.ExpenseRespository;
import com.gestaoCash.services.ExpenseService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  @Autowired
  private ExpenseRespository expenseRespository;

  @Override
  public void saveExpense(Expense expense) {
    this.expenseRespository.save(expense);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Expense> findAllExpense() {
    return this.expenseRespository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Expense findExpenseById(Long id) {
    var optionalExpense = this.expenseRespository.findById(id);

    if (optionalExpense.isPresent()) {
      var expense = optionalExpense.get();
      return expense;
    } else {
      throw new EntityNotFoundException();
    }
  }
  
  
@Transactional
  @Override
public List<Expense> findExpenseAndUser(Long id) {
	
	return expenseRespository.findExpenseAndUser(id);
}

@Override
  public void updateExpenseById(Long id, Expense updatedExpense) {
    this.expenseRespository.save(updatedExpense);
  }

  @Override
  public void deleteExpenseById(Long id) {
    this.expenseRespository.deleteById(id);
  }

@Override
public Stream<Expense> findExpenseFilterDate(LocalDate date, Long id) {
	return this.findExpenseAndUser(id).stream().filter(expense -> expense.getData().getMonth() == date.getMonth() && expense.getData().getYear() == date.getYear() );
}

@Override
public double calcTotalExpenses(List<Expense> expense) {
	double value = 0;
	for (Expense exp : expense) {
		value += exp.getValor();
	}
	return value;
}
  
  
}
