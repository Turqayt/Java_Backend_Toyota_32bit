package com.j32bit.backend.dto.form;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j32bit.backend.entity.Form;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FormDTO {
    private Integer id;
    private String title;
    public static FormDTO of(Form form){
        return new FormDTO(form.getId(), form.getTitle());
    }
}
