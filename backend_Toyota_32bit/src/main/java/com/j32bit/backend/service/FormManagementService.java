package com.j32bit.backend.service;

import com.j32bit.backend.dto.application.ApplicationCreateDTO;
import com.j32bit.backend.dto.application.ApplicationDTO;
import com.j32bit.backend.dto.application.ApplicationPageDTO;
import com.j32bit.backend.dto.component.ComponentDTO;
import com.j32bit.backend.dto.form.FormComponentDTO;
import com.j32bit.backend.dto.form.FormCreateDTO;
import com.j32bit.backend.dto.form.FormDTO;
import com.j32bit.backend.dto.page.NewPageDTO;
import com.j32bit.backend.dto.page.PageDTO;
import com.j32bit.backend.dto.page.PageFormDTO;
import com.j32bit.backend.dto.user.UserApplicationDTO;
import com.j32bit.backend.dto.version.VersionDTO;

import java.sql.SQLException;
import java.util.List;

public interface FormManagementService {
    ApplicationDTO createApplication(ApplicationCreateDTO applicationCreateDTO);//Create_application
    UserApplicationDTO creatUserApplication(UserApplicationDTO applicationDTO);//Create_User_Application
    VersionDTO createVersion(String version, int applicationId);//Create_Version
    ApplicationPageDTO createApplicationPage(ApplicationPageDTO applicationPageDTO);//Create_Application_Page
    PageDTO createPage(PageDTO pageDTO);//Create_Page
    PageFormDTO createPageForm(PageFormDTO pageFormDTO);//Create_Page_Form
    FormDTO createForm(FormCreateDTO formCreateDTO);//Create_Form
    FormComponentDTO createFormComponent(FormComponentDTO formComponentDTO);//Create_Form_Component
    ComponentDTO createComponent();//Create_Component
    List<ApplicationDTO> findApplicationsWithPaginationAndSorting(int offset, int pageSize, String field);//Read_Application Pagination and Sorting
    List<ApplicationDTO> findAllApplication(String filterText);//Read_Application Filter
    ApplicationDTO applicationList(Integer id); //Read_Application application listi id olarak döndürüyor
    ApplicationDTO updateApplication(Integer id, ApplicationCreateDTO applicationUpdateDTO);//Update_application
    void deleteApplication(Integer id) throws SQLException;//Delete_Application
    void deleteUserApplication(Integer id);//Delete_User_Application
    void deleteApplicationPage(Integer id);//Delete_Application_Page
    void deleteVersion(Integer id) throws SQLException;//Delete_Version
    void deletePageForm(Integer id);//Delete_Page_Form
    void deleteFormComponent(Integer id);//Delete_FormComponent
    void newPage(Integer applicationId, NewPageDTO newPageDTO) throws SQLException;//Create_Page NewPage
    void updateVersion(Integer id) throws SQLException;//Update_Version
    PageDTO updatePage(Integer pageId, PageDTO pageDTO);//Update_Page
    void addForm(Integer applicationId, Integer pageId) throws SQLException;//Add Form

}
