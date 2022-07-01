package com.j32bit.backend.dto.application;

import lombok.Data;

//Application create ve update metotlarına DTO yapıyor
@Data
public class ApplicationCreateDTO {
    private String description;
    private String name;
    private byte formtype;
}
