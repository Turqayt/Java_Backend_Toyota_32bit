package com.j32bit.backend.dto.version;

import lombok.Data;

@Data
public class VersionCreateDTO {
    private Integer id;
    private String version;
    private  int applicationId;

}
