package com.j32bit.backend.api;

import com.j32bit.backend.dto.application.ApplicationCreateDTO;
import com.j32bit.backend.dto.application.ApplicationDTO;
import com.j32bit.backend.dto.homePage.HomePageDTO;
import com.j32bit.backend.dto.page.NewPageDTO;
import com.j32bit.backend.dto.page.PageDTO;
import com.j32bit.backend.dto.user.*;
import com.j32bit.backend.service.*;
import com.j32bit.backend.shared.ApiResponse;
import com.j32bit.backend.shared.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/user")
@AllArgsConstructor
@CrossOrigin
public class UserAPI {



    private final AuthenticationService authenticationService;

    private final HomePageService homePageService;
    private final UserManagementService userManagementService;

    private  final FormManagementService formManagementService;

    /*
    * LOGIN
    * */

    //Login kullanıcı giriş yapıyor ve token döndürüyor
    @PostMapping("authenticate")
    public ResponseEntity<String> authenticate(@RequestBody UserLoginRequestDTO userLoginRequestDTO) throws Exception {
        return ResponseEntity.ok(authenticationService.authenticate(userLoginRequestDTO));
    }

    /*
    *  HOMEPAGE
    *  Admin kullanıcısı dışındaki roller bu servise erişemiyor.
    * */

    //HomePage ana sayfa bilgilerini döndürüyor
    @GetMapping("homepage")
    public ResponseEntity<GenericResponse<HomePageDTO>> homepage(){
        ArrayList<HomePageDTO> homepage = homePageService.findAll();
        return ResponseEntity.ok(GenericResponse.createSuccessResponse(homepage));
    }

    /*
    * USER MANAGEMENT
    * Admin ve Supervisor roleri erişebilir
    * */

    //Creat_User kullanıcı ekliyor
    @PostMapping("management/createuser")
    public ResponseEntity<?> createUser( @RequestBody UserCreateDTO userCreateDTO) throws SQLException {
        userManagementService.createUser(userCreateDTO);
        log.info("User Created and Role Added ");
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","123456");


            HomePageDTO homePageDTO = new HomePageDTO();

            Statement sta = con.createStatement();
            ResultSet res = sta.executeQuery("Select COUNT(*) From mobile.user where deleted = false");
            while (res.next()) {
                homePageDTO.setNumberOfUsers(Integer.parseInt(res.getString("count")));
            }
            homePageService.updateHomePage(homePageDTO);
        }
        catch (SQLException ex){
            log.error("SQLException: {}",ex.getMessage());
        }
        log.info("Home Page Updated");
        return ResponseEntity.ok(new GenericResponse("User Created."));
    }

    //Read_User Pagination and Sort
    @GetMapping("managament/userpaginationandsort/{offset}/{pagesize}/{field}")
    private ApiResponse<List<UserDTO>> getUserWithPaginationAndSort(@PathVariable int offset,@PathVariable int pagesize,@PathVariable String field){
        List<UserDTO> allUsers = userManagementService.findUsersWithPaginationAndSorting(offset,pagesize,field);

        return  new ApiResponse<>(allUsers.size(), allUsers);
    }

    //Read_User Filtering
   @GetMapping("managament/userfilter/{filter}")
    private ApiResponse<List<UserDTO>> getUserFilter(@PathVariable String filter){

        List<UserDTO> allUsers = userManagementService.findAll(filter);
        return  new ApiResponse<>(allUsers.size(), allUsers);
    }

    //Update_User kullanıcı günceliyor
    @PutMapping("management/updateuser/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Integer id,@RequestBody UserUpdateDTO userUpdateDTO){
        final UserDTO user = userManagementService.updateUser(id,userUpdateDTO);
        log.info("User Updated");
        return  ResponseEntity.ok(user);
    }

    //Delete_User kullanıcı siliyor (soft delete silme)
    @DeleteMapping("management/deleteuser/{id}")
    public  void deleteUser(@PathVariable("id") Integer id) throws SQLException {
        Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","123456");
        Statement stmt=con.createStatement();
        try
        {
            String query ="select * from mobile.userrole where user_id='"+ id +"' " ;
            ResultSet rs=stmt.executeQuery(query);
            Integer userRoleId = 0;

            while (rs.next())
            {
                String userRoleIdString=rs.getString(1);
                userRoleId=Integer.parseInt(userRoleIdString);
                if (userRoleId ==0){
                    log.error("UserRoleId Not Found");
                }
                userManagementService.deleteRole(userRoleId);
            }

            log.info("User Role Deleted");

        }
        catch(SQLException ex)
        {
            log.error("SQLException1: {}",ex.getMessage());
        }
        userManagementService.deleteUser(id);
        log.info("User Deleted");
        try {
            HomePageDTO homePageDTO = new HomePageDTO();
            ResultSet res = stmt.executeQuery("Select COUNT(*) From mobile.user where deleted = false");
            while (res.next()) {
                homePageDTO.setNumberOfUsers(Integer.parseInt(res.getString("count")));
            }
            homePageService.updateHomePage(homePageDTO);
        }
        catch (SQLException ex){
            log.error("SQLException: {}",ex.getMessage());
        }
        stmt.close();
        con.close();
        log.info("Home Page Updated");
    }

    //Read_User_Role Pagination and Sort
    @GetMapping("managament/userrolepaginationandsort/{offset}/{pagesize}/{field}")
    private ApiResponse<List<UserRoleViewDTO>> getUserRoleWithPaginationAndSort(@PathVariable int offset, @PathVariable int pagesize, @PathVariable String field){
        List<UserRoleViewDTO> allUserRoles = userManagementService.findUserRolesWithPaginationAndSorting(offset,pagesize,field);
        return  new ApiResponse<>(allUserRoles.size(), allUserRoles);
    }

    //Read_User_Role Filtering
    @GetMapping("managament/userrolefilter/{filter}")
    private ApiResponse<List<UserRoleViewDTO>> getUserRoleFilter(@PathVariable String filter){
        List<UserRoleViewDTO> allUserRoles = userManagementService.findAllRole(filter);
        return  new ApiResponse<>(allUserRoles.size(), allUserRoles);
    }

    //Update_User_Role kullanıcıya rol atıyor
    @PostMapping("management/userauthorization")
    public ResponseEntity<?> userAuthorization( @RequestBody UserRoleAddedDTO userRoleCreateDTO){
        userManagementService.userAuthorization(userRoleCreateDTO);
        return ResponseEntity.ok(new GenericResponse("User authorization."));
    }

    //Delete_User_Role rol siliyor
    @DeleteMapping("management/deleterole/{userid}/{roleid}")
    public  void deleteRole(@PathVariable("userid") Integer userid,@PathVariable ("roleid") Integer roleid){
        try
        {

            Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","123456");
            Statement stmt=con.createStatement();
            String query ="Select * from mobile.userrole where to_tsvector('simple',role_id || ' ' || user_id) @@ to_tsquery('simple','"+roleid+"&"+userid+"')" ;
            ResultSet rs=stmt.executeQuery(query);
            Integer userRoleId = 0; // user ve role veritabanina eklenmis id sayisini donduryor
            while (rs.next())
            {
                String userRoleIdString=rs.getString(1);
                userRoleId=Integer.parseInt(userRoleIdString);
            }

            if (userRoleId ==0){
                log.error("UserRoleId not found");
            }
            userManagementService.deleteRole(userRoleId);

            stmt.close();
            con.close();
        }
        catch(SQLException ex)
        {
            log.error("SQLException: {}",ex.getMessage());
        }
    } //END MANAGEMENT

    /*
    * FORM MANAGEMENT
    * */

    //APPLICATION
    //Creat_Application
    //Application oluşduğunda user_application, version, application _page, page, page_form, form, form_component ve component`de oluşuyor
    @PostMapping("form/createapplication")
    public ResponseEntity<?> createApplication( @RequestBody ApplicationCreateDTO applicationCreateDTO) throws SQLException {
        formManagementService.createApplication(applicationCreateDTO);
        log.info("Application Created");
        return ResponseEntity.ok(new GenericResponse("Application Created"));
    }

    //Read_Application Pagination and Sort
    @GetMapping("form/applicationpaginationandsort/{offset}/{pagesize}/{field}")
    private ApiResponse<List<ApplicationDTO>> getApplicationWithSorting(@PathVariable int offset, @PathVariable int pagesize, @PathVariable String field){
        List<ApplicationDTO> allApplicatons = formManagementService.findApplicationsWithPaginationAndSorting(offset, pagesize, field);
        return  new ApiResponse<>(allApplicatons.size(), allApplicatons);
    }

    //Read_Application Filter
    @GetMapping("form/applicationfilter/{filter}")
    private ApiResponse<List<ApplicationDTO>> getApplicationFilter(@PathVariable String filter){
        List<ApplicationDTO> allApplication = formManagementService.findAllApplication(filter);
        return  new ApiResponse<>(allApplication.size(), allApplication);
    }

    //Read_Application List
    @GetMapping("form/applicationlist/{id}")
    public ResponseEntity<ApplicationDTO> applicationList(@PathVariable Integer id){
                final ApplicationDTO application = formManagementService.applicationList(id);
        return ResponseEntity.ok(application);
    }

    //Update_Application
    @PutMapping("form/updateapplication/{id}")
    public ResponseEntity<ApplicationDTO> updateApplication(@PathVariable("id") Integer id, @RequestBody ApplicationCreateDTO applicationCreateDTO){
        final ApplicationDTO application = formManagementService.updateApplication(id,applicationCreateDTO);
        log.info("Application Updated");
        return  ResponseEntity.ok(application);
    }

    //Delete_Application
    @DeleteMapping("form/deleteapplication/{id}")
    public void deleteApplication(@PathVariable("id") Integer id) throws SQLException {
        formManagementService.deleteApplication(id);
    }//END APPLICATION

    //PAGE
    //Create_Page
    @PostMapping("form/newpage/{id}")
    public ResponseEntity<?> newPage(@PathVariable("id") Integer id,@RequestBody NewPageDTO newPageDTO) throws SQLException {
        formManagementService.newPage(id,newPageDTO);
        return ResponseEntity.ok(new GenericResponse("NewPage Created"));
    }

    //Update_Page
    @PutMapping("form/updatepage/{id}")
    public ResponseEntity<PageDTO> updatePage(@PathVariable("id") Integer id, @RequestBody PageDTO pageDTO){
        final PageDTO pageDTOView = formManagementService.updatePage(id, pageDTO);
        return ResponseEntity.ok(pageDTOView);
    }

    //Add_Form
    @PostMapping("form/addform/{applicationid}/{pageid}")
    public  ResponseEntity<?> addForm(@PathVariable("applicationid") Integer applicationId, @PathVariable("pageid") Integer pageId) throws SQLException {
        formManagementService.addForm(applicationId,pageId);
        return  ResponseEntity.ok(new GenericResponse("Form Added"));
    }
}
