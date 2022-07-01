package com.j32bit.backend.repository;

import com.j32bit.backend.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepositroy extends JpaRepository<Component, Integer> {
}
