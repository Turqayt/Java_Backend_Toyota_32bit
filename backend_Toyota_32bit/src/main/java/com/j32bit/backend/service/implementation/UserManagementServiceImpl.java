package com.j32bit.backend.service.implementation;

import com.j32bit.backend.dto.user.*;
import com.j32bit.backend.entity.Role;
import com.j32bit.backend.entity.User;
import com.j32bit.backend.entity.UserRole;
import com.j32bit.backend.exception.NotFoundException;
import com.j32bit.backend.repository.UserRepository;
import com.j32bit.backend.repository.UserRoleRepository;
import com.j32bit.backend.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/*
* Kullanıcı ekleme, rol düzenleme, güncelleme ve "soft delete" silme işlemi bu serviste yapılıyor
* */

@Service
@RequiredArgsConstructor
@Log4j2
public class UserManagementServiceImpl implements UserManagementService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserRoleRepository userRoleRepository;


    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found in database.");
        }
        log.debug("UserDetailServiceImpl: loadUserByUsername method completed.");
        return user;
    }

    @Override
    public ArrayList<UserDTO> findAll() {
        ArrayList<User> users = userRepository.findAll();
        ArrayList<UserDTO> userDtos = new ArrayList<>();
        for(User user : users) {
            UserDTO userDto = new UserDTO();
            BeanUtils.copyProperties(user,userDto);
            List<Role> roles = user.getRoles();
            List<Role> roleDTOs = new ArrayList<>();
            for(Role role : roles){
                Role roleDTO = new Role();
                BeanUtils.copyProperties(role,roleDTO);
                roleDTOs.add(roleDTO);
            }
            userDto.setRoles(roleDTOs);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    //Create_User Burada kullanıcı ve rol bilgileri ekleniyor
    @Override
    @Transactional
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        final User user = userRepository.save(new User(userCreateDTO.getAddress(), userCreateDTO.getCompanyname(), userCreateDTO.getEmail(),
                userCreateDTO.getName(), userCreateDTO.getOccupation(), userCreateDTO.getPhonenumber(), userCreateDTO.getSurname(),
                userCreateDTO.getTcnumber(), userCreateDTO.getUsername(), null,null));
        log.info("User Created");
        UserRoleAddedDTO userRoleAddedDTO = new UserRoleAddedDTO();
        userRoleAddedDTO.setRole_id(2);
        userRoleAddedDTO.setUser_id(user.getId());
        userAuthorization(userRoleAddedDTO);
        log.info("Role added");
        return UserDTO.of(user);
    }

    //Read_User Pagination and Sort
    @Override
    public List<UserDTO> findUsersWithPaginationAndSorting(int offset, int pageSize, String field) {
        Page<User> users = userRepository.findAll(PageRequest.of(offset,pageSize).withSort(Sort.by(field)));
        ArrayList<UserDTO> userDtos = new ArrayList<>();
        for(User user : users) {
            UserDTO userDto = new UserDTO();
            BeanUtils.copyProperties(user,userDto);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    //Read_User Filter
    @Override
    public List<UserDTO> findAll(String filterText) {
        if(filterText == null || filterText.isEmpty()){
            ArrayList<User> users = userRepository.findAll();
            ArrayList<UserDTO> userDtos = new ArrayList<>();

            for(User user : users) {
                UserDTO userDto = new UserDTO();
                BeanUtils.copyProperties(user,userDto);
                userDtos.add(userDto);
            }
            return  userDtos;
        }
        else {
            List<User> users = userRepository.search(filterText);
            List<UserDTO> userDtos = new ArrayList<>();

            for(User user : users) {
                UserDTO userDto = new UserDTO();
                BeanUtils.copyProperties(user,userDto);
                userDtos.add(userDto);
            }
            return  userDtos;
        }

    }

    //Update_User kullanıcı bilgileri günceleniyor
    @Override
    @Transactional
    public UserDTO updateUser(Integer id, UserUpdateDTO userUpdateDTO) {
        final User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
        log.info("User Found");
        user.setAddress(userUpdateDTO.getAddress());
        user.setCompanyName(userUpdateDTO.getCompanyname());
        user.setEmail(userUpdateDTO.getEmail());
        user.setName(userUpdateDTO.getName());
        user.setOccupation(userUpdateDTO.getOccupation());
        user.setPhoneNumber(userUpdateDTO.getPhonenumber());
        user.setSurname(userUpdateDTO.getSurname());
        user.setTcNumber(userUpdateDTO.getTcnumber());
        user.setUserName(userUpdateDTO.getUsername());
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        String updateBy = SecurityContextHolder.getContext().getAuthentication().getName();
        user.setUpdatedBy(updateBy);
        log.info("User Values Assigned");
        final User updateUser = userRepository.save(user);
        log.info("User Data Saved in Database");
        return UserDTO.of(updateUser);
    }

    //Delete_User kullanici siliyor
    @Override
    @Transactional
    public void deleteUser(Integer id){
        userRepository.deleteById(id);
        log.info("User Deleted");
    }

    //Read_User_Role Pagination and Sort
    @Override
    public List<UserRoleViewDTO> findUserRolesWithPaginationAndSorting(int offset, int pageSize, String field) {
        Page<User> users = userRepository.findAll(PageRequest.of(offset,pageSize).withSort(Sort.by(field)));
        ArrayList<UserRoleViewDTO> userRoleViewDTOS = new ArrayList<>();
        for(User user : users) {
            UserRoleViewDTO userDto = new UserRoleViewDTO();
            BeanUtils.copyProperties(user,userDto);
            userRoleViewDTOS.add(userDto);
        }
        return userRoleViewDTOS;
    }

    //Read_User_Role Filter
    @Override
    public List<UserRoleViewDTO> findAllRole(String filterText) {
        if(filterText == null || filterText.isEmpty()){
            ArrayList<User> users = userRepository.findAll();
            ArrayList<UserRoleViewDTO> userRoleViewDTOS = new ArrayList<>();

            for(User user : users) {
                UserRoleViewDTO userRoleViewDTO = new UserRoleViewDTO();
                BeanUtils.copyProperties(user,userRoleViewDTO);
                userRoleViewDTOS.add(userRoleViewDTO);
            }
            return  userRoleViewDTOS;
        }
        else {
            List<User> users = userRepository.search(filterText);
            List<UserRoleViewDTO> userRoleViewDTOS = new ArrayList<>();

            for(User user : users) {
                UserRoleViewDTO userRoleViewDTO = new UserRoleViewDTO();
                BeanUtils.copyProperties(user,userRoleViewDTO);
                userRoleViewDTOS.add(userRoleViewDTO);
            }
            return  userRoleViewDTOS;

        }
    }

    //Update_User_Role rol bilgileri günceleniyor
    @Override
    @Transactional
    public UserRoleDTO userAuthorization(UserRoleAddedDTO userRoleAddedDTO) {
        final UserRole userRole = userRoleRepository.save(new UserRole(userRoleAddedDTO.getRole_id(), userRoleAddedDTO.getUser_id()));
       log.info("Rol Updated");
        return UserRoleDTO.of(userRole);
    }

    //Delete_Role rol siliyor
    @Override
    @Transactional
    public void deleteRole(Integer id) {
        userRoleRepository.deleteById(id);
        log.info("Role Deleted");
    }

}
