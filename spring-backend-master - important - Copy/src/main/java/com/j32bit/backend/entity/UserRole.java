/**
 *
 */
package com.j32bit.backend.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.io.Serializable;


/**

 *
 */
@Log4j2
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u")
@Table(name = "userrole")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(name = "role_id")
    private Integer roleId;
    @NotNull
    @Column(name = "user_id")
    private Integer userId;

    public UserRole(Integer role_id, Integer user_id){
        this.roleId = role_id;
        this.userId = user_id;
        log.info("Creating User Role");
    }
}
