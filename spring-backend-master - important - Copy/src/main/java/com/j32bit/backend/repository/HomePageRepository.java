package com.j32bit.backend.repository;


import com.j32bit.backend.entity.HomePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;

public interface HomePageRepository extends JpaRepository<HomePage, Integer>, JpaSpecificationExecutor<HomePage> {
    public ArrayList<HomePage> findAll();
}
