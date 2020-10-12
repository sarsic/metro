package com.metro.app.jpa.repository;

import com.metro.app.jpa.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

}
