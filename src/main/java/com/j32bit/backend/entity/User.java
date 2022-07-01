/**
 * 
 */
package com.j32bit.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**

 *
 */
@Log4j2
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = "user_name"),
		@UniqueConstraint(columnNames = "email") })
@SQLDelete(sql = "UPDATE mobile.user SET deleted = true WHERE  id=?")
@Where(clause = "deleted=false")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String address;
	@Column(name = "created_at")
	private Timestamp createdAt;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "tc_number")
	private String tcNumber;
	@Column(name = "company_name")
	private String companyName;
	@Column(name = "occupation")
	private String occupation;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "status")
	private byte status;
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	private String name;
	private String surname;
	@Column(name = "user_name", unique = true, nullable = false)
	private String userName;
	@Column(name = "password")
	private String password;
	@Column(name = "reset_key")
	private String resetKey;
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "is_admin")
	private byte isAdmin;
	private boolean deleted = Boolean.FALSE ; //False = not deleted , True = deleted
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "userrole", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	public User(String address,String companyName, String email, String name,String occupation,String phoneNumber,
			    String surname, String tcNumber , String userName, Timestamp updatedAt, String updatedBy) {
		String createdByName = SecurityContextHolder.getContext().getAuthentication().getName();
		this.address =address;
		this.companyName = companyName;
		this.createdAt =Timestamp.from(Instant.now());
		this.createdBy = createdByName;
		this.email = email;
		this.name = name;
		this.occupation = occupation;
		this.password = "c4ca4238a0b923820dcc509a6f75849b";
		this.phoneNumber = phoneNumber;
		this.status = 0;
		this.surname = surname;
		this.tcNumber = tcNumber;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.resetKey = null;
		this.isAdmin = 0;
		this.roles = getRoles();
		this.userName = userName;
		log.info("Creating User");

	}

}
