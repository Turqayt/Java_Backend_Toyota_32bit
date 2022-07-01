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
import java.sql.Timestamp;
import java.time.Instant;

@Log4j2
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "PageForm.findAll", query = "SELECT u FROM PageForm u")
@Table(name = "page_form")
@SQLDelete(sql = "UPDATE mobile.page_form SET deleted = true WHERE  id=?")
@Where(clause = "deleted=false")
public class PageForm {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "created_by")
    private  String createdBy;
    @Column(name = "order_no")
    private String orderNo;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "updated_at")
    private  Timestamp updatedAt;
    @Column(name = "updated_by")
    private  String updatedBy;
    @Column(name = "version")
    private  String version;
    @Column(name = "form_id")
    private  int formId;
    @Column(name = "page_id")
    private  int pageId;
    @Column(name = "version_id")
    private  int versionId;
    private boolean deleted = Boolean.FALSE ; //False = not deleted , True = deleted
    public PageForm(Timestamp updatedAt, String updatedBy, int formId, int pageId, int versionId){
        String createdByName = SecurityContextHolder.getContext().getAuthentication().getName();
        this.createdAt = Timestamp.from(Instant.now());
        this.createdBy = createdByName;
        this.orderNo = "0";
        this.shortName = "app21212312";
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.version = "1.0";
        this.formId = formId;
        this.pageId = pageId;
        this.versionId = versionId;
    }
}
