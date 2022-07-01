package com.j32bit.backend.repository;

import com.j32bit.backend.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionRepository extends JpaRepository<Version,Integer> {

}
