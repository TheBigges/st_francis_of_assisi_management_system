package com.calapi.st.francis.assisi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.calapi.st.francis.assisi.model.WeddingRecord;

public interface WeddingRepository extends JpaRepository<WeddingRecord, Long> {

	Optional<WeddingRecord> findByCoupleUuid(String coupleUuid);

	@Query("""
			    SELECT COUNT(w)
			    FROM WeddingRecord w
			    WHERE w.dateOfWedding BETWEEN :startDate AND :endDate
			""")
	long countByYear(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query("""
			    SELECT w
			    FROM WeddingRecord w
			    WHERE LOWER(w.brideLastName) LIKE LOWER(CONCAT('%', :param, '%'))
			       OR LOWER(w.groomLastName) LIKE LOWER(CONCAT('%', :param, '%'))
			""")
	List<WeddingRecord> searchByLastNameContains(@Param("param") String param);

}
