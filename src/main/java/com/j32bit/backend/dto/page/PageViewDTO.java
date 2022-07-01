package com.j32bit.backend.dto.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j32bit.backend.dto.form.FormViewDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name="PageViewDTO.findAll", query="SELECT r FROM PageViewDTO r")
@Table(name="page")
public class PageViewDTO {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)//otomatik artan deger
    private Integer id;
    @Column(name="title")
    private String title;
    @Column(name = "is_page_name_hide")
    private boolean isPageNameHide;
    @Column(name = "page_number")
    private int pageNumber;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "page_form", joinColumns = @JoinColumn(name = "page_id"), inverseJoinColumns = @JoinColumn(name = "form_id"))
    private List<FormViewDTO> forms;

}
