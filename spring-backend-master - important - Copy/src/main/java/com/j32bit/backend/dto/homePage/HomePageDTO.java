package com.j32bit.backend.dto.homePage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j32bit.backend.entity.HomePage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomePageDTO {

    private int numberOfForms;
    private int numberOfUsers;
    private int numberOfCompletedForms;
    public static HomePageDTO of(HomePage homePage){
        return new HomePageDTO(homePage.getNumberOfForms(), homePage.getNumberOfUsers(), homePage.getNumberOfCompletedForms());
    }
}
