package com.metro.app.jpa.repository;

import com.metro.app.jpa.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    @Query("select o from Order o where o.dtc between :from AND :to")
    Page<Order> findWithinPeriod(@Param("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
                                 @Param("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to, Pageable pageable);
}
