package com.j32bit.backend.dto.form;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j32bit.backend.entity.FormComponent;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormComponentDTO {
    private int componentId;
    private int formId;
    private int versionId;
    int colNumber;
    public static FormComponentDTO of(FormComponent formComponent){
        return new FormComponentDTO(formComponent.getComponentId(), formComponent.getFormId(), formComponent.getVersionId(), formComponent.getColNumber());

    }
}
