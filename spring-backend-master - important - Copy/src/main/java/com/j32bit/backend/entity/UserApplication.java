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

@Log4j2
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "UserApplication.findAll", query = "SELECT u FROM UserApplication u")
@Table(name = "user_application")
@SQLDelete(sql = "UPDATE mobile.user_application SET deleted = true WHERE  id=?")
@Where(clause = "deleted=false")
public class UserApplication implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "application_id")
    private int applicationId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "status")
    private byte status;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "updated_by")
    private String updatedBy;
    private boolean deleted = Boolean.FALSE ; //False = not deleted , True = deleted


    public UserApplication(int applicationId, int userId, Timestamp updatedAt, String updatedBy) {

        String createdByName = SecurityContextHolder.getContext().getAuthentication().getName();
        this.applicationId = applicationId;
        this.userId = userId;
        this.createdAt = Timestamp.from(Instant.now());
        this.createdBy = createdByName;
        this.status = 1;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;


        log.info("Creating User_Application");

    }


}

