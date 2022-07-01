package com.j32bit.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "HomePage.findAll", query = "SELECT u FROM HomePage u")
@Table(name = "homepage")
public class HomePage implements Serializable {
    @Id
    private Integer id;
    @Column(name = "number_of_forms")
    private int numberOfForms;
    @Column(name = "number_of_users")
    private int numberOfUsers;
    @Column(name = "number_of_completed_forms")
    private int numberOfCompletedForms;
}
