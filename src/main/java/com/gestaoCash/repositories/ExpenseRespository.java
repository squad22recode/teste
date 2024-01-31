package com.gestaoCash.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestaoCash.model.Expense;
import com.gestaoCash.model.Revenue;

@Repository
public interface ExpenseRespository extends JpaRepository<Expense, Long> {
	@Query(nativeQuery= true,
			value = "SELECT DISTINCT d.*"
			+ " FROM despesa_usuario as d"
			+ " INNER JOIN usuarios as u"
			+ " ON d.usuario_id = :id")
	List<Expense> findExpenseAndUser(@Param("id") Long id);
}
