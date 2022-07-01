package com.j32bit.backend.repository;

import com.j32bit.backend.entity.ApplicationPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationPageRepository extends JpaRepository<ApplicationPage, Integer> {
}
