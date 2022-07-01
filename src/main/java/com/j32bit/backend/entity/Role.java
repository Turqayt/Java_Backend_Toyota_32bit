/**
 * 
 */
package com.j32bit.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**

 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
@Table(name="role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//otomatik artan deger
	private Integer id;
	
	private String roleName;
	
	@Column(name="short_name")//veritabanindaki isim
	private String shortName;

}
