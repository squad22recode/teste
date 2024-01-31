package com.gestaoCash.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestaoCash.model.Revenue;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {
	
	
	@Query(nativeQuery= true,
			value = "SELECT DISTINCT r.*"
			+ " FROM receita_usuario as r"
			+ " INNER JOIN usuarios as u"
			+ " ON r.usuario_id = :id")
	List<Revenue> findRevenueAndUser(@Param("id") Long id);
}
