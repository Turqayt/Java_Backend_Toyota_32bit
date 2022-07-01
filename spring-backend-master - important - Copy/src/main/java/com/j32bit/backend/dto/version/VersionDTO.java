package com.j32bit.backend.dto.version;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j32bit.backend.entity.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VersionDTO {
    private Integer id;
    private String version;
    private  int applicationId;

    public VersionDTO(Integer id, String version){
        this.id = id;
        this.version = version;
    }

    public static VersionDTO of(Version version){
        return new VersionDTO(version.getId(),version.getVersion());
    }
}
