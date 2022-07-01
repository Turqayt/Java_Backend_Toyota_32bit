package com.j32bit.backend.service.implementation;

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
import com.j32bit.backend.dto.version.VersionCreateDTO;
import com.j32bit.backend.dto.version.VersionDTO;
import com.j32bit.backend.entity.*;
import com.j32bit.backend.exception.NotFoundException;
import com.j32bit.backend.repository.*;
import com.j32bit.backend.service.FormManagementService;
import com.j32bit.backend.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
/*
* FORM MANAGEMENT
* */

@Service
@RequiredArgsConstructor
@Log4j2
public class FormManagementServiceImpl implements FormManagementService {
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    UserApplicationRepository userApplicationRepository;
    @Autowired
    VersionRepository versionRepository;
    @Autowired
    ApplicationPageRepository applicationPageRepository;
    @Autowired
    PageRepository pageRepository;
    @Autowired
    PageFormRepository pageFormRepository;
    @Autowired
    FormRepository formRepository;
    @Autowired
    FormComponentRepository formComponentRepository;
    @Autowired
    ComponentRepositroy componentRepositroy;
    private final UserManagementService userManagementService;
    private int versionId, pageId, formId, componentId;

    //Create_Application
    @Override
    public ApplicationDTO createApplication(ApplicationCreateDTO applicationCreateDTO) {
        final Application application = applicationRepository.save(new Application(applicationCreateDTO.getDescription(),
                applicationCreateDTO.getName(),"App164800043612084",null,null,"1.0", applicationCreateDTO.getFormtype()));
        log.info("Application is Created");

        //#User_Application
        String createdByName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserApplicationDTO userApplicationDTO = new UserApplicationDTO();
        userApplicationDTO.setApplicationId(application.getId());
        User user = userManagementService.findByUsername(createdByName);
        userApplicationDTO.setUserId(user.getId());
        userApplicationDTO.setUpdateAt(null);
        userApplicationDTO.setUpdateBy("null");
        creatUserApplication(userApplicationDTO);
        log.info("User_Application Created");

        //#Version
        createVersion("1.0",application.getId());
        log.info("Version Created");

        //#Page
        PageDTO pageDTO = new PageDTO();
        pageDTO.setTitle("SCREEN1");
        pageDTO.setPageNameHide(false);
        pageDTO.setPageNumber(1);
        createPage(pageDTO);
        log.info("Page Created");

        //#Application_Page
        ApplicationPageDTO applicationPageDTO = new ApplicationPageDTO();
        applicationPageDTO.setUpdateAt(null);
        applicationPageDTO.setUpdateBy("null");
        applicationPageDTO.setVersion("1.0");
        applicationPageDTO.setApplicationId(application.getId());
        applicationPageDTO.setVersionId(versionId);
        applicationPageDTO.setTemplateUrl("url");
        applicationPageDTO.setPageId(pageId);
        createApplicationPage(applicationPageDTO);
        log.info("Application_page Created");

        return ApplicationDTO.of(application);
    }

    //Create_User_Application
    @Override
    public UserApplicationDTO creatUserApplication(UserApplicationDTO userApplicationDTO) {
        final UserApplication userApplication = userApplicationRepository.save(new UserApplication(userApplicationDTO.getApplicationId(),userApplicationDTO.getUserId(),
                userApplicationDTO.getUpdateAt(),userApplicationDTO.getUpdateBy()));
        log.info("User_Application is Created");
        return UserApplicationDTO.of(userApplication);
    }

    //Create_Version version oluşturuyor
    @Override
    public VersionDTO createVersion(String version, int applicationId){
        VersionCreateDTO versionCreateDTO = new VersionCreateDTO();
        versionCreateDTO.setVersion(version);
        versionCreateDTO.setApplicationId(applicationId);
        final Version version1 = versionRepository.save(new Version(versionCreateDTO.getVersion(),versionCreateDTO.getApplicationId()));
        versionId = version1.getId();
        log.info("Version Created");
        return VersionDTO.of(version1);
    }

    //Create_Application_Page
    @Override
    public ApplicationPageDTO createApplicationPage(ApplicationPageDTO applicationPageDTO) {
        final ApplicationPage applicationPage = applicationPageRepository.save(new ApplicationPage(applicationPageDTO.getUpdateAt(),applicationPageDTO.getUpdateBy(),
                applicationPageDTO.getVersion(),applicationPageDTO.getApplicationId(),applicationPageDTO.getPageId(),applicationPageDTO.getVersionId(),
                applicationPageDTO.getTemplateUrl()));
        log.info("Application_Page Created");
        return ApplicationPageDTO.of(applicationPage);
    }

    //Create_Page
    @Override
    public PageDTO createPage(PageDTO pageDTO) {
        final PageV page = pageRepository.save(new PageV(pageDTO.getTitle(),pageDTO.isPageNameHide(),pageDTO.getPageNumber()));
        pageId = page.getId();

        //Form
        FormCreateDTO formCreateDTO = new FormCreateDTO();
        formCreateDTO.setTitle("");
        createForm(formCreateDTO);

        //Page_Form
        PageFormDTO pageFormDTO = new PageFormDTO();
        pageFormDTO.setFormId(formId);
        pageFormDTO.setPageId(pageId);
        pageFormDTO.setVersionId(versionId);
        createPageForm(pageFormDTO);

        log.info("Page Created");
        return PageDTO.of(page);
    }

    //Create_Page_Form
    @Override
    public PageFormDTO createPageForm(PageFormDTO pageFormDTO) {
        final PageForm pageForm = pageFormRepository.save(new PageForm(null,"null",pageFormDTO.getFormId(),pageFormDTO.getPageId(),
                pageFormDTO.getVersionId()));
        log.info("Page_Form Created");
        return PageFormDTO.of(pageForm);
    }

    //Create_Form
    @Override
    public FormDTO createForm(FormCreateDTO formCreateDTO) {
        final Form form = formRepository.save(new Form(formCreateDTO.getTitle(),null,"null"));
        formId = form.getId();
        log.info("Form is Created");

        //Component1
        createComponent();
        log.info("Component1 Created");

        //FormComponent1
        FormComponentDTO formComponentDTO = new FormComponentDTO();
        formComponentDTO.setComponentId(componentId);
        formComponentDTO.setFormId(formId);
        formComponentDTO.setVersionId(versionId);
        formComponentDTO.setColNumber(0);
        createFormComponent(formComponentDTO);
        log.info("Form_Component1 Created");

        //Component2
        createComponent();
        log.info("Component2 Created");

        //FormComponent2
        FormComponentDTO formComponentDTO2 = new FormComponentDTO();
        formComponentDTO2.setComponentId(componentId);
        formComponentDTO2.setFormId(formId);
        formComponentDTO2.setVersionId(versionId);
        formComponentDTO2.setColNumber(1);
        createFormComponent(formComponentDTO2);
        log.info("Form_Component2 Created");

        return FormDTO.of(form);
    }

    //Create_Form_Component
    @Override
    public FormComponentDTO createFormComponent(FormComponentDTO formComponentDTO) {
        final FormComponent formComponent = formComponentRepository.save(new FormComponent(formComponentDTO.getComponentId(),formComponentDTO.getFormId(),
                formComponentDTO.getVersionId(), formComponentDTO.getColNumber()));
        log.info("Create Form Component");
        return FormComponentDTO.of(formComponent);
    }

    //Create_Component
    @Override
    public ComponentDTO createComponent() {
        final Component component = componentRepositroy.save(new Component(""));
        componentId = component.getId();
        return ComponentDTO.of(component);
    } //Create_Application oluşunca şuraya kadar olan bütün fonksiyonlar oluşuyor #End_Create_Application

    //Application Pagination and Sorting
    @Override
    public List<ApplicationDTO> findApplicationsWithPaginationAndSorting(int offset, int pageSize, String field) {
        Page<Application> applications = applicationRepository.findAll(PageRequest.of(offset,pageSize).withSort(Sort.by(field)));
        ArrayList<ApplicationDTO> applicationDTOS = new ArrayList<>();
        for(Application application : applications) {
            ApplicationDTO applicationDTO = new ApplicationDTO();
            BeanUtils.copyProperties(application,applicationDTO);
            applicationDTOS.add(applicationDTO);
        }
        return applicationDTOS;
    }

    //Read_Application Filter
    @Override
    public List<ApplicationDTO> findAllApplication(String filterText) {
        if(filterText == null || filterText.isEmpty()){
            ArrayList<Application> applications = applicationRepository.findAll();
            ArrayList<ApplicationDTO> applicationDTOS = new ArrayList<>();

            for(Application application : applications) {
                ApplicationDTO applicationDTO = new ApplicationDTO();
                BeanUtils.copyProperties(application,applicationDTO);
                applicationDTOS.add(applicationDTO);
            }
            return  applicationDTOS;
        }
        else {
            List<Application> applications = applicationRepository.search(filterText);
            List<ApplicationDTO> applicationDTOS = new ArrayList<>();

            for(Application application : applications) {
                ApplicationDTO applicationDTO = new ApplicationDTO();
                BeanUtils.copyProperties(application,applicationDTO);
                applicationDTOS.add(applicationDTO);
            }
            return  applicationDTOS;
        }
    }

    //Read_Application application listi id olarak döndürüyor
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ApplicationDTO applicationList(Integer id) {
        final Application application = applicationRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not Found Exception"));

        return  ApplicationDTO.of(application);
    }

    //Update_Application
    @Override
    public ApplicationDTO updateApplication(Integer id, ApplicationCreateDTO applicationCreateDTO) {
        final Application application = applicationRepository.findById(id).orElseThrow(() -> new NotFoundException("Application Not Found"));
        log.info("Application Found");
        String updateBy = SecurityContextHolder.getContext().getAuthentication().getName();
        application.setName(applicationCreateDTO.getName());
        application.setDescription(applicationCreateDTO.getDescription());
        application.setFormType(applicationCreateDTO.getFormtype());
        application.setUpdatedAt(Timestamp.from(Instant.now()));
        application.setUpdatedBy(updateBy);
        log.info("Application Values Assigned");
        final Application updateApplication = applicationRepository.save(application);
        log.info("Application Data Saved in Database");
        return ApplicationDTO.of(updateApplication);
    }

    //Delete_Application
    @Override
    public void deleteApplication(Integer id) throws SQLException {
        Connection con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","123456");
        Statement stmt=con.createStatement();

        //#Delete_User_Application
        try
        {
            String query ="select * from mobile.user_application where application_id='"+ id +"' " ;
            ResultSet rs=stmt.executeQuery(query);
            Integer userApplicationId = 0;

            while (rs.next())
            {
                String userApplicationIdString=rs.getString(1);
                userApplicationId=Integer.parseInt(userApplicationIdString);
                if (userApplicationId ==0){
                    log.error("UserApplicationId Not Found");
                }
                deleteUserApplication(userApplicationId);
            }

            log.info("User_Application Soft Deleted");
        }
        catch(SQLException ex)
        {
            log.error("SQLException1: {}",ex.getMessage());
        }

        //#Delete_Application_Page
        try
        {
            String query ="select * from mobile.application_page where application_id='"+ id +"' " ;
            ResultSet rs=stmt.executeQuery(query);
            Integer applicationPageId = 0;

            while (rs.next())
            {
                String applicationPageIdString=rs.getString(1);
                applicationPageId=Integer.parseInt(applicationPageIdString);
                if (applicationPageId ==0){
                    log.error("ApplicationPageId Not Found");
                }
                deleteApplicationPage(applicationPageId);
            }

            log.info("Application_Page Soft Deleted");
        }
        catch(SQLException ex)
        {
            log.error("SQLException1: {}",ex.getMessage());
        }

        //#Delete_Version
        try
        {
            String query ="select * from mobile.version where application_id='"+ id +"' " ;
            ResultSet rs=stmt.executeQuery(query);
            Integer versionId = 0;

            while (rs.next())
            {
                String versionIdString=rs.getString(1);
                versionId=Integer.parseInt(versionIdString);
                if (versionId ==0){
                    log.error("Version Not Found");
                }
                deleteVersion(versionId);
            }

            log.info("Version Soft Deleted");
        }
        catch(SQLException ex)
        {
            log.error("SQLException1: {}",ex.getMessage());
        }

        stmt.close();
        con.close();
        log.info("Start application");
        applicationRepository.deleteById(id);
    }

    //Delete_User_Application
    @Override
    public void deleteUserApplication(Integer id) {
        userApplicationRepository.deleteById(id);
    }

    //Delete_Application_Page
    @Override
    public void deleteApplicationPage(Integer id) {
        applicationPageRepository.deleteById(id);
    }

    //Delete_Version
    @Override
    public void deleteVersion(Integer id) throws SQLException {
        Connection con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","123456");
        Statement stmt=con.createStatement();

        //#Delete_Page_Form
        try
        {
            String query ="select * from mobile.page_form where version_id='"+ id +"' " ;
            ResultSet rs=stmt.executeQuery(query);
            Integer pageFormId = 0;

            while (rs.next())
            {
                String pageFormIdString=rs.getString(1);
                pageFormId=Integer.parseInt(pageFormIdString);
                if (pageFormId ==0){
                    log.error("PageFormId Not Found");
                }
                deletePageForm(pageFormId);
            }

            log.info("Page_Form Soft Deleted");
        }
        catch(SQLException ex)
        {
            log.error("SQLException1: {}",ex.getMessage());
        }

        //#Delete_Form_Component
        try
        {
            String query ="select * from mobile.form_component where version_id='"+ id +"' " ;
            ResultSet rs=stmt.executeQuery(query);
            Integer formComponentId = 0;

            while (rs.next())
            {
                String formComponentIdString=rs.getString(1);
                formComponentId=Integer.parseInt(formComponentIdString);
                if (formComponentId ==0){
                    log.error("FormComponentId Not Found");
                }
                deleteFormComponent(formComponentId);
            }

            log.info("Form_Component Soft Deleted");
        }
        catch(SQLException ex)
        {
            log.error("SQLException1: {}",ex.getMessage());
        }
        stmt.close();
        con.close();
        versionRepository.deleteById(id);
    }

    //Delete_Page_Form
    @Override
    public void deletePageForm(Integer id) {
        pageFormRepository.deleteById(id);
    }

    //Delete_Form_Component
    @Override
    public void deleteFormComponent(Integer id) {
        formComponentRepository.deleteById(id);
    }

    //New_Page
    @Override
    public void  newPage(Integer applicationId, NewPageDTO newPageDTO) throws SQLException {

        //#Version
        updateVersion(applicationId);
        log.info("Version Updated");

        //#Page
        PageDTO pageDTO =new  PageDTO();
        pageDTO.setTitle(newPageDTO.getTitle());
        pageDTO.setPageNameHide(false);

        Connection con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","123456");
        Statement stmt=con.createStatement();

        //Find Old Page Id
        Integer pageid = 0;
        try
        {
            String query ="select * from mobile.application_page where application_id='"+ applicationId +"' " ;
            ResultSet rs=stmt.executeQuery(query);

            while (rs.next())
            {
                String pageIdString=rs.getString(10);
                pageid=Integer.parseInt(pageIdString);
                if (pageid ==0){
                    log.error("PageId Not Found");
                }
                log.info("Giris1 Yapildi");
            }
        }
        catch(SQLException ex)
        {
            log.error("SQLException1: {}",ex.getMessage());
        }

        //Find Page Number
        int pageNumber = 0;
        try
        {
            String query2 ="select * from mobile.page where id='"+ pageid +"' " ;
            ResultSet rs2=stmt.executeQuery(query2);


            while (rs2.next())
            {
                String pageNumberString=rs2.getString(8);
                pageNumber=Integer.parseInt(pageNumberString);
                log.info("rakarm: {}",pageNumber);
                if (pageNumber ==0){
                    log.error("PageNumber Not Found");
                }
                log.info("Giris2 Yapildi");
                pageNumber=pageNumber+1;
            }
        }
        catch(SQLException ex)
        {
            log.error("SQLException1: {}",ex.getMessage());
        }
        pageDTO.setPageNumber(pageNumber);
        createPage(pageDTO);

        //#Application_Page
        ApplicationPageDTO applicationPageDTO = new ApplicationPageDTO();
        applicationPageDTO.setUpdateAt(null);
        applicationPageDTO.setUpdateBy("null");
        applicationPageDTO.setVersion("1.1");
        applicationPageDTO.setApplicationId(applicationId);
        applicationPageDTO.setVersionId(versionId);
        applicationPageDTO.setTemplateUrl("url");
        applicationPageDTO.setPageId(pageId);
        createApplicationPage(applicationPageDTO);
        log.info("Application_page Created");

    }

    //Update_Version
    @Override
    public void updateVersion(Integer applicationId) throws SQLException {
        Connection con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","123456");
        Statement stmt=con.createStatement();
        Integer versionid = 0;
        float versionNumber =0.0f;
        String versionNumberString="0.0";
        try
        {
            String query ="select * from mobile.version where application_id='"+ applicationId +"' " ;
            ResultSet rs=stmt.executeQuery(query);

            while (rs.next())
            {
                String versionIdString=rs.getString(1);
                versionNumberString = rs.getString(2);
                versionid=Integer.parseInt(versionIdString);
                versionNumber = Float.parseFloat(versionNumberString);
                versionNumber= versionNumber + 0.1f;
                versionNumberString = String.format("%2f", versionNumber);
                if (versionid ==0){
                    log.error("VersionId Not Found");
                }
            }
        }
        catch(SQLException ex)
        {
            log.error("SQLException1: {}",ex.getMessage());
        }

        final Version version = versionRepository.findById(versionid).orElseThrow(() -> new NotFoundException("Application Not Found"));
        log.info("Version Found");
        version.setVersion(versionNumberString);
        log.info("Version Values Assigned");
        final Version updateVersion = versionRepository.save(version);
        log.info("Version Data Saved in Database");
        log.info("Version Updated");
        versionId = versionid;
    }

    //Update_Page
    @Override
    public PageDTO updatePage(Integer pageId, PageDTO pageDTO) {
        final PageV pageV = pageRepository.findById(pageId).orElseThrow(() -> new NotFoundException("Page Not Found"));
        log.info("Page Found");
        String updateBy = SecurityContextHolder.getContext().getAuthentication().getName();
        pageV.setTitle(pageDTO.getTitle());
        pageV.setUpdatedAt(Timestamp.from(Instant.now()));
        pageV.setUpdateBy(updateBy);
        pageV.setPageNameHide(pageDTO.isPageNameHide());
        pageV.setPageNumber(pageDTO.getPageNumber());
        log.info("Page Values Assigned");
        final PageV updatePage = pageRepository.save(pageV);
        log.info("Page Data Saved in Database");
        return PageDTO.of(updatePage);

    }

    //Add Form
    @Override
    public void addForm(Integer applicationId, Integer pageId) throws SQLException {

        //Form
        FormCreateDTO formCreateDTO = new FormCreateDTO();
        formCreateDTO.setTitle("");
        createForm(formCreateDTO);

        //Update_Version
        updateVersion(applicationId);

        //Page_Form
        PageFormDTO pageFormDTO = new PageFormDTO();
        pageFormDTO.setFormId(formId);
        pageFormDTO.setPageId(pageId);
        pageFormDTO.setVersionId(versionId);
        createPageForm(pageFormDTO);

    }


}
