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
@NamedQuery(name = "ApplicationPage.findAll", query = "SELECT u FROM ApplicationPage u")
@Table(name = "application_page")
@SQLDelete(sql = "UPDATE mobile.application_page SET deleted = true WHERE  id=?")
@Where(clause = "deleted=false")
public class ApplicationPage {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "created_by")
    private  String createdBy;
    @Column(name = "is_home_page")
    private Boolean isHomePage;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "updated_at")
    private Timestamp updateAt;
    @Column(name = "update_by")
    private String updateBy;
    @Column(name = "version")
    private  String version;
    @Column(name = "application_id")
    private int applicationId;
    @Column(name = "page_id")
    private int pageId;
    @Column(name = "version_id")
    private int versionId;
    @Column(name = "template_url")
    private String templateUrl;
    private boolean deleted = Boolean.FALSE ; //False = not deleted , True = deleted
    public ApplicationPage(Timestamp updateAt, String updateBy, String version, int applicationId,
                           int pageId, int versionId, String templateUrl) {

        String createdByName = SecurityContextHolder.getContext().getAuthentication().getName();
       this.createdAt = Timestamp.from(Instant.now());
       this.createdBy = createdByName;
       this.isHomePage = Boolean.TRUE;
       this.shortName = "Shortname";
       this.updateAt = updateAt;
       this.updateBy = updateBy;
       this.version = version;
       this.applicationId = applicationId;
       this.pageId = pageId;
       this.versionId = versionId;
       this.templateUrl = templateUrl;

        log.info("Creating Application_Page");

    }
}
