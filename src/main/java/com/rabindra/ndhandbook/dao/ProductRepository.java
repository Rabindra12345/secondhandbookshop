package com.rabindra.ndhandbook.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import com.rabindra.ndhandbook.entity.Product;

@CrossOrigin("http://localhost:4200")
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryId(@RequestParam("id") Long id, Pageable pageable);	
    
    //Below method sets query as
    //select * from `full-stack-ecommerce`.product p  where p.name like concat('%', 'java', '%');
    //Here java is the keyword that you search in a search box.
    Page<Product> findByNameContaining(@RequestParam("name") String name, Pageable pageable);
}
