package com.prjctr.hsa.stresstest.repository;

import com.prjctr.hsa.stresstest.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    
}
