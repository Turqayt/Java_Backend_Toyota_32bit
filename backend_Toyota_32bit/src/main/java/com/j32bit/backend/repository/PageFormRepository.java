package com.j32bit.backend.repository;

import com.j32bit.backend.entity.PageForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageFormRepository extends JpaRepository<PageForm, Integer> {
}
