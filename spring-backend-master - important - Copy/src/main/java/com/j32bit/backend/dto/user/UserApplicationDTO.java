package com.j32bit.backend.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j32bit.backend.entity.UserApplication;
import lombok.*;

import java.sql.Timestamp;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserApplicationDTO {
    private int applicationId;
    private int userId;
    private Timestamp updateAt;
    private String updateBy;

    public static UserApplicationDTO of(UserApplication userApplication){
        return new UserApplicationDTO(userApplication.getApplicationId(), userApplication.getUserId(),
                userApplication.getUpdatedAt(), userApplication.getUpdatedBy());
    }
}
