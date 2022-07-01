package com.j32bit.backend.service;

import com.j32bit.backend.dto.user.*;
import com.j32bit.backend.entity.User;

import java.util.ArrayList;
import java.util.List;

public interface UserManagementService {
    public User findByUsername(String username);

    /**
     *
     * @return all Users
     */
    ArrayList<UserDTO> findAll();
    UserDTO createUser(UserCreateDTO userCreateDTO);//Creat_User
    List<UserDTO> findUsersWithPaginationAndSorting(int offset, int pageSize, String field); //Read_User Pagination and Sorting
    List<UserDTO> findAll(String filterText);//Read_User Filtering
    UserDTO updateUser(Integer id, UserUpdateDTO userUpdateDTO); //Update_User
    void deleteUser(Integer id); //Delete_User
    List<UserRoleViewDTO> findUserRolesWithPaginationAndSorting(int offset, int pageSize, String field); //Read_User_Role Pagination and Sorting
    List<UserRoleViewDTO> findAllRole(String filterText);//Read_User_Role Filtering
    UserRoleDTO userAuthorization(UserRoleAddedDTO userRoleAddedDTO);//Update_User_Role
    void deleteRole(Integer id);//Delete_User_Role


}
