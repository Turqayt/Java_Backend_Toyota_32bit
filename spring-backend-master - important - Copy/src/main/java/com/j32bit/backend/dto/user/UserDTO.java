/**
 * 
 */
package com.j32bit.backend.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j32bit.backend.entity.Role;
import com.j32bit.backend.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter

@NoArgsConstructor
public class UserDTO {
	private Integer id;
	private String email;
	private String name;
	private String surname;
	private String userName;
	private String tcNumber;
	private String companyName;
	private String occupation;
	private String phoneNumber;
	private String address;
	private byte isAdmin;
	private byte status;
	private List<Role> roles;
	public UserDTO(String email, String name, String surname, String userName, String tcNumber, String companyName,
				   String occupation, String phoneNumber, String address, byte isAdmin, byte status,List<Role> roles) {

		this.email = email;
		this.name = name;
		this.surname = surname;
		this.userName = userName;
		this.tcNumber = tcNumber;
		this.companyName = companyName;
		this.occupation = occupation;
		this.phoneNumber = phoneNumber;
		this.address =address;
		this.isAdmin = isAdmin;
		this.status = status;
		this.roles = roles;
	}

	public static UserDTO of(User user){
		return new UserDTO(user.getEmail(), user.getName(), user.getSurname(), user.getUserName(), user.getTcNumber(),user.getCompanyName(),
				user.getOccupation(), user.getPhoneNumber(), user.getAddress(), user.getIsAdmin(), user.getStatus(), user.getRoles());
	}

}
