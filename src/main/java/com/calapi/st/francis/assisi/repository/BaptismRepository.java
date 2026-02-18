package com.calapi.st.francis.assisi.repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.calapi.st.francis.assisi.model.BaptismRecord;
import com.calapi.st.francis.assisi.model.dto.BaptismRecordDto;

public interface BaptismRepository extends JpaRepository<BaptismRecord, Long> {
	
	@Query("SELECT b FROM BaptismRecord b WHERE LOWER(b.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<BaptismRecord> findByLastNameContainingIngnoreCase(String keyword);

	Optional<BaptismRecord> findByChildUuid(String id);
	
	@Query("""
		    SELECT COUNT(b) > 0
		    FROM BaptismRecord b
		    WHERE b.dateOfBirth = :#{#r.dateOfBirth}
		      AND b.firstName = :#{#r.firstName}
		      AND b.middleName = :#{#r.middleName}
		      AND b.lastName = :#{#r.lastName}
		      AND b.fatherFirstName = :#{#r.fatherFirstName}
		      AND b.fatherMiddleName = :#{#r.fatherMiddleName}
		      AND b.fatherLastName = :#{#r.fatherLastName}
		      AND b.motherFirstName = :#{#r.motherFirstName}
		      AND b.motherMiddleName = :#{#r.motherMiddleName}
		      AND b.motherLastName = :#{#r.motherLastName}
		""")
		boolean existsDuplicate(@Param("r") BaptismRecordDto r);

	@Query("""
	        SELECT COUNT(b)
	        FROM BaptismRecord b
	        WHERE b.dateOfBaptism BETWEEN :startDate AND :endDate
	    """)
	    long countByYear(
	            @Param("startDate") LocalDate startDate,
	            @Param("endDate") LocalDate endDate
	    );

}
