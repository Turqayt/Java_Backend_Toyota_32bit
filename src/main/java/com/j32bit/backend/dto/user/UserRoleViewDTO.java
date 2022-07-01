package com.j32bit.backend.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j32bit.backend.entity.Role;
import com.j32bit.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/*
* User`e atanmis rolleri diziyor
*
* */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleViewDTO {
    private String name;
    private String surname;
    private String userName;
    private List<Role> roles;

    public static UserRoleViewDTO of(User user){
        return new UserRoleViewDTO(user.getName(),user.getSurname(),user.getUserName(),user.getRoles());
    }
}
