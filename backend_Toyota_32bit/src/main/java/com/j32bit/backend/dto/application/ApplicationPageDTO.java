package com.j32bit.backend.dto.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j32bit.backend.entity.ApplicationPage;
import lombok.*;

import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationPageDTO {

    private Timestamp updateAt;
    private String updateBy;
    private  String version;
    private int applicationId;
    private int pageId;
    private int versionId;
    private String templateUrl;

    public static ApplicationPageDTO of(ApplicationPage applicationPage){
        return new ApplicationPageDTO(applicationPage.getUpdateAt(), applicationPage.getUpdateBy(),applicationPage.getVersion(),
                applicationPage.getApplicationId(), applicationPage.getPageId(), applicationPage.getVersionId(),
                applicationPage.getTemplateUrl()
                );
    }

}
