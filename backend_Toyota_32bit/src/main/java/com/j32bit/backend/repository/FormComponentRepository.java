package com.j32bit.backend.repository;

import com.j32bit.backend.entity.FormComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormComponentRepository extends JpaRepository<FormComponent, Integer> {
}
