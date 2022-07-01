package com.j32bit.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "FormComponent.findAll", query = "SELECT u FROM FormComponent u")
@Table(name = "form_component")
@SQLDelete(sql = "UPDATE mobile.form_component SET deleted = true WHERE  id=?")
@Where(clause = "deleted=false")
public class FormComponent {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "updated_at")
    private  Timestamp updatedAt;
    @Column(name = "updated_by")
    private  String updatedBy;
    @Column(name = "version")
    private  String version;
    @Column(name = "component_id")
    private  int componentId;
    @Column(name = "form_id")
    private int formId;
    @Column(name = "version_id")
    private int versionId;
    @Column(name = "col_size")
    private String colSize;
    @Column(name = "col_number")
    private  int colNumber;
    @Column(name = "row_number")
    private  int rowNumber;
    @Column(name = "description")
    private  String description;
    @Column(name = "col_align")
    private  String colAlign;
    @Column(name = "datasource_type")
    private boolean datasourceType;
    private boolean deleted = Boolean.FALSE ; //False = not deleted , True = deleted

    public FormComponent(int componentId, int formId, int versionId , int rowNumber){
        String createdByName = SecurityContextHolder.getContext().getAuthentication().getName();
        this.createdAt = Timestamp.from(Instant.now());
        this.createdBy = createdByName;
        this.shortName = "";
        this.updatedAt = null;
        this.updatedBy = "null";
        this.version = "1.0";
        this.componentId = componentId;
        this.formId = formId;
        this.versionId = versionId;
        this.colSize = "";
        this.colNumber = 0;
        this.rowNumber = rowNumber;
        this.description = "";
        this.colAlign = "";
        this.datasourceType = false;
    }
}

