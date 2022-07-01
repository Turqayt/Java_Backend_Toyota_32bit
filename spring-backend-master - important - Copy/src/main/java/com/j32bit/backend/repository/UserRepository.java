/**
 * 
 */
package com.j32bit.backend.repository;


import com.j32bit.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**

 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {


	public User findByUserName(String userName);


	public ArrayList<User> findAll();

	@Query("SELECT t FROM User t WHERE " +
			"LOWER(t.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
			"LOWER(t.surname) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	List<User> search(@Param("searchTerm") String searchTerm);
}
