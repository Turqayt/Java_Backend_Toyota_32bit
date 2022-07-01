/**
 * 
 */
package com.j32bit.backend.dto.role;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
	
	private Integer id;	
	private String roleName;	
	private String shortName;
	

	

}
