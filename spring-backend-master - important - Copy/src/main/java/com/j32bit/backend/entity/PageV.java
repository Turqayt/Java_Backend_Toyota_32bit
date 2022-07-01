package com.j32bit.backend.entity;

import com.j32bit.backend.dto.form.FormViewDTO;
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
@NamedQuery(name = "Page.findAll", query = "SELECT u FROM PageV u")
@Table(name = "page")
public class PageV {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "created_by")
    private  String createdBy;
    @Column(name = "title")
    private String title;
    @Column(name = "updated_at")
    private  Timestamp updatedAt;
    @Column(name = "update_by")
    private  String updateBy;
    @Column(name = "is_page_name_hide")
    private  boolean isPageNameHide;
    @Column(name = "page_number")
    private  int pageNumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "page_form", joinColumns = @JoinColumn(name = "page_id"), inverseJoinColumns = @JoinColumn(name = "form_id"))
    private List<FormViewDTO> forms;
    public PageV(String title, Boolean isPageNameHide, int pageNumber){
        String createdByName = SecurityContextHolder.getContext().getAuthentication().getName();
        this.createdAt = Timestamp.from(Instant.now());
        this.createdBy = createdByName;
        this.title = title;
        this.updatedAt = null;
        this.updateBy = "null";
        this.isPageNameHide = isPageNameHide;
        this.pageNumber = pageNumber;
        this.forms = getForms();
    }
}
