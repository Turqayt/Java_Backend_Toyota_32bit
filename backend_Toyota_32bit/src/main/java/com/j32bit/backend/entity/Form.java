package com.j32bit.backend.entity;

import com.j32bit.backend.dto.form.FormComponentViewDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Log4j2
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "Form.findAll", query = "SELECT u FROM Form u")
public class Form {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "title")
    private String title;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "update_by")
    private String updateBy;
    @OneToMany(mappedBy = "rows",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FormComponentViewDTO> components;
    public Form(String title, Timestamp updatedAt, String updateBy){
        String createdByName = SecurityContextHolder.getContext().getAuthentication().getName();

        this.createdAt = Timestamp.from(Instant.now());
        this.createdBy = createdByName;
        this.title = title;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
        this.components = getComponents();
    }
}
