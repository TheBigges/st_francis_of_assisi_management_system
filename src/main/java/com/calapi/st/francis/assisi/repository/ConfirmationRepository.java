package com.calapi.st.francis.assisi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.calapi.st.francis.assisi.model.ConfirmationRecord;

public interface ConfirmationRepository extends JpaRepository<ConfirmationRecord, Long> {

	Optional<ConfirmationRecord> findByRecordUuid(String recordUuid);
	
	@Query("""
	        SELECT COUNT(b)
	        FROM ConfirmationRecord b
	        WHERE b.confirmationDate BETWEEN :startDate AND :endDate
	    """)
	    long countByYear(
	            @Param("startDate") LocalDate startDate,
	            @Param("endDate") LocalDate endDate
	    );
	
	@Query("""
		    SELECT MONTH(b.confirmationDate), COUNT(b)
		    FROM ConfirmationRecord b
		    WHERE YEAR(b.confirmationDate) = :year
		    GROUP BY MONTH(b.confirmationDate)
		    ORDER BY MONTH(b.confirmationDate)
		""")
		List<Object[]> countConfirmationPerMonth(@Param("year") int year);
}