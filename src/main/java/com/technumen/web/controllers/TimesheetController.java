package com.technumen.web.controllers;

import com.technumen.constants.TimesheetConstants;
import com.technumen.models.Employee;
import com.technumen.models.Timesheet;
import com.technumen.services.EmailService;
import com.technumen.services.TimesheetService;
import com.technumen.utils.DateUtils;
import com.technumen.web.validators.TimesheetValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class TimesheetController {

    @Autowired
    TimesheetService timesheetService;
    @Autowired
    EmailService emailService;
    @Autowired
    TimesheetValidator timesheetValidator;

    /**
     * Get method to forward the request to Add Timesheet page with required details.
     *
     * @param modelMap
     * @return
     */
    @GetMapping("/addTimesheet")
    public ModelAndView addTimesheet(ModelMap modelMap, HttpSession session) {
        log.info("Inside addTimesheet method of IndexController");
        if (session == null || session.getAttribute("user") == null) {
            log.error("Inside addTimesheet, session not found. Redirecting the request to Login page.");
            return new ModelAndView("redirect:/login");
        }
        //Check if the startEndDatesMap is already existing in the session.
        if (session.getAttribute("startEndDatesMap") != null) {
            modelMap.addAttribute("startEndDatesMap", (Map<Date, String>) session.getAttribute("startEndDatesMap"));
        } else {
            List<Date> endDatesList = DateUtils.getListWeekEndDatesOfLastThreeMonths();
            log.info("After getting endDateList: " + endDatesList.size());
            Map<Date, String> startEndDatesMap = DateUtils.getWeekStartEndDatesMap(endDatesList);
            modelMap.addAttribute("startEndDatesMap", startEndDatesMap);
            session.setAttribute("startEndDatesMap", startEndDatesMap);
            //Making the map eligible for Garbage collection.
            startEndDatesMap = null;
        }
        modelMap.addAttribute("timesheetObj", new Timesheet());
        log.info("After setting all the required modelMap attributes.");
        return new ModelAndView("employee/addTimesheet", modelMap);
    }

    /**
     * Post method to save the time sheet information after performing necessary validations.
     *
     * @param timesheetObj
     * @param result
     * @param status
     * @param model
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/addTimesheet")
    public ModelAndView addTimesheet(
            @ModelAttribute("timesheetObj") Timesheet timesheetObj, BindingResult result,
            SessionStatus status, Model model, RedirectAttributes redirectAttributes, HttpSession session)
            throws Exception {
        log.info("Inside addTimesheet post method.");
        Employee employee = (Employee) session.getAttribute("user");
        MultipartFile file = timesheetObj.getUploadFile();
        if (file.getSize() != 0) {
            log.info("Name: " + file.getName() + " File type: " + file.getContentType() +
                    " Size: " + file.getSize() + " Original Name: " + file.getOriginalFilename());
            //Set the fromDate based on the selected end date of the week.
            timesheetObj.setFromDate(DateUtils.getTimesheetWeekStartDate(timesheetObj.getToDate()));
            timesheetObj.setEmployeeName(employee.getEmployeeFullName());
            timesheetObj.setClientName(employee.getClientName());
            //File Upload information
            timesheetObj.setBlobContent(file.getBytes());
            timesheetObj.setFileSize(file.getSize());
            timesheetObj.setDscFileName(file.getOriginalFilename());
            timesheetObj.setTimesheetStatus(TimesheetConstants.TIMESHEET_STATUS_SUBMITTED);
            timesheetObj.setTimesheetType(TimesheetConstants.TIMESHEET_TYPE_WEEKLY);
            timesheetObj.setNameCreated(employee.getEmployeeFullName());
            timesheetObj.setEmployee(employee);
            timesheetObj.setDateCreated(new Date());

            timesheetValidator.validate(timesheetObj, result);
            //Check if the Add Time sheet form information has errors.
            if (result.hasErrors()) {
                model.addAttribute("css", "danger");
                model.addAttribute("msg", "Invalid / Missing Information. Please correct the information entered below!!");

                return new ModelAndView("employee/addTimesheet");
            }
            //Save the Time sheet information in the database.
            timesheetService.save(timesheetObj);

            model.addAttribute("css", "success");
            model.addAttribute("msg", "Timesheet Information Submitted successfully!!");
            model.addAttribute("summarymodal", "yes");

            return new ModelAndView("employee/addTimesheet");
        } else {
            log.error("File not found");
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "Please upload your approved timesheet for the selected time period.");

            return new ModelAndView("employee/addTimesheet");
        }

    }

}
