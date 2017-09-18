package com.technumen.web.controllers;

import com.technumen.constants.TimesheetConstants;
import com.technumen.models.Employee;
import com.technumen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping(value = "/dashboard")
public class DashboardController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView forwardToAppropriateDashboard(HttpSession session, Model model) {
        log.info("Inside the forwardToAppropriateDashboard method of DashboardController");
        Employee employee = (Employee) session.getAttribute("user");
        //Check if the employee object exist in the session.
        if (employee == null) {
            log.error("Cannot find employee object in the session, so forwarding to Login page");
            return new ModelAndView("redirect:/login");
        }
        log.info("Check for Employee role and forward to appropriate employee role");
        String viewName = "";
        log.info("Switch case to check for the Employee role based on the employeeRoleId");
        switch (employee.getEmployeeRoleId()) {
            //Employee
            case TimesheetConstants.USER_ROLE_EMPLOYEE_ID:
                employee.setEmployeeRoleDesc(TimesheetConstants.USER_ROLE_EMPLOYEE);
                viewName = "employee/dashboard";
                break;
            //Supervisor
            case TimesheetConstants.USER_ROLE_SUPERVISOR_ID:
                employee.setEmployeeRoleDesc(TimesheetConstants.USER_ROLE_SUPERVISOR);
                session.setAttribute("weekStartDatesList", DateUtils.getWeekStartDatesMap());
                session.setAttribute("weekEndDatesList", DateUtils.getWeekEndDatesMap());
                session.setAttribute("timesheetStatusList", TimesheetConstants.TIMESHEET_STATUS_LIST);
                viewName = "staff/dashboard-staff";
                break;
            //Admin
            case TimesheetConstants.USER_ROLE_ADMIN_ID:
                employee.setEmployeeRoleDesc(TimesheetConstants.USER_ROLE_ADMIN);
                session.setAttribute("weekStartDatesList", DateUtils.getListWeekStartDatesOfLastThreeMonths());
                session.setAttribute("weekEndDatesList", DateUtils.getListLocalWeekEndDatesOfLastThreeMonths());
                session.setAttribute("timesheetStatusList", TimesheetConstants.TIMESHEET_STATUS_LIST);
                viewName = "staff/dashboard-admin";
                break;
            default:
                viewName = "login";
        }

        log.info("View Name from the switch case: " + viewName);
        model.addAttribute("currentEndDate", DateUtils.getCurrentTimesheetWeekEndDate());
        return new ModelAndView(viewName);
    }
}
