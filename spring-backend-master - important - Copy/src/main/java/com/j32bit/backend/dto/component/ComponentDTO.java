package com.j32bit.backend.dto.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j32bit.backend.entity.Component;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComponentDTO {
    private String type;

    public static ComponentDTO of(Component component){
        return new ComponentDTO(component.getType());
    }
}
