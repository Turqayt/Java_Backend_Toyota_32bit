package com.j32bit.backend.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name="FormViewDTO.findAll", query="SELECT r FROM FormViewDTO r")
@Table(name="form")
public class FormViewDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)//otomatik artan deger
    private Integer id;
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "rows",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<FormComponentViewDTO> components;
}
