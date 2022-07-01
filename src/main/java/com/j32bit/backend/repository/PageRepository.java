package com.j32bit.backend.repository;

import com.j32bit.backend.entity.PageV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository <PageV, Integer> {

}
