package com.knitting.diary.repository;

import com.knitting.diary.domain.Knitting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KnittingRepository extends JpaRepository<Knitting, Long> {
    @Query(
            "SELECT k FROM Knitting k " +
            "ORDER BY " +
            "CASE WHEN k.endDate IS NULL THEN 0 ELSE 1 END, " +
            "k.endDate DESC, k.startDate DESC"
    )
    Page<Knitting> findAllSorted(Pageable pageable);
}
