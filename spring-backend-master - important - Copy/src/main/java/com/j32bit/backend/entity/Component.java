package com.j32bit.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
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
@NamedQuery(name = "Component.findAll", query = "SELECT u FROM Component u")
public class Component implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "type")
    private String type;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "update_by")
    private String updatedBy;

    public Component(String type){
        String createdByName = SecurityContextHolder.getContext().getAuthentication().getName();
        this.createdAt = Timestamp.from(Instant.now());
        this.createdBy = createdByName;
        this.type = type;
        this.updatedAt = null;
        this.updatedBy = "null";
    }
}
