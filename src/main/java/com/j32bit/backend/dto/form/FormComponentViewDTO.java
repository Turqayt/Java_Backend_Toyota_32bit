package com.j32bit.backend.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name="FormComponentViewDTO.findAll", query="SELECT r FROM FormComponentViewDTO r")
@Table(name="form_component")
public class FormComponentViewDTO implements Serializable {
    @Column(name = "col_number")
    private int colNumber;
    @Column(name = "row_number")
    private int rowNumber;
    @Column(name = "col_size")
    private String colSize;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "datasource_type")
    private boolean datasourceType;
    @Id
    private int id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private FormViewDTO rows;
}
