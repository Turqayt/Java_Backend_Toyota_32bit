package com.j32bit.backend.dto.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j32bit.backend.entity.PageForm;
import lombok.*;

import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageFormDTO {
    private Timestamp updatedAt;
    private String updatedBy;
    private int formId;
    private int pageId;
    private int versionId;

    public  static PageFormDTO of(PageForm pageForm){
        return new PageFormDTO(pageForm.getUpdatedAt(),pageForm.getUpdatedBy(),pageForm.getFormId(),
                pageForm.getPageId(),pageForm.getVersionId());
    }
}
