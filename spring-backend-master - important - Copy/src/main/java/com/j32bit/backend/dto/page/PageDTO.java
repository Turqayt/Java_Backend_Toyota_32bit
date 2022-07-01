package com.j32bit.backend.dto.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j32bit.backend.entity.PageV;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {

    private String title;
    private boolean isPageNameHide;
    private int pageNumber;



    public static PageDTO of(PageV pageV){
        return new PageDTO( pageV.getTitle(), pageV.isPageNameHide(), pageV.getPageNumber());
    }
}
