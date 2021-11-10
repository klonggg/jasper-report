package com.spring.jrrepo.repository;

import com.spring.jrrepo.model.Reports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Reports, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM reports " +
            "WHERE code=:code AND from_date=:fromDate AND to_date=:toDate")
    Reports getReport(@Param("code") String code,
                      @Param("fromDate") String fromDate,
                      @Param("toDate") String toDate);
}
