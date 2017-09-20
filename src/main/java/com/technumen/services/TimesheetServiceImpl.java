package com.technumen.services;

import com.technumen.constants.TimesheetConstants;
import com.technumen.models.Employee;
import com.technumen.models.Timesheet;
import com.technumen.repositories.TimesheetRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TimesheetServiceImpl implements TimesheetService {

    @Autowired
    TimesheetRepository timesheetRepository;

    @Override
    public void save(Timesheet timesheetObj) throws Exception {
        log.info("Inside save method of TimesheetServiceImpl :: " + timesheetObj.getTimesheetId());
        timesheetRepository.save(timesheetObj);
    }

    @Override
    public Timesheet getTimesheetByTimesheetId(long timesheetId) throws Exception {
        log.info("Inside getTimesheetByTimesheetId method of TimesheetServiceImpl --> timesheetId: " + timesheetId);
        return timesheetRepository.findOne(timesheetId);
    }

    @Override
    public Timesheet getTimesheetByEndDate(Date endDate, Employee employee) throws Exception {
        log.info("Inside getTimesheetByEndDate method of TimesheetServiceImpl :: endDate: " + endDate);
        return timesheetRepository.findTimesheetByToDateAndEmployee(endDate, employee);
    }

    @Override
    public List<Timesheet> getTimesheetSummaryStaff(Date fromDate, Date toDate, String timesheetStatus) throws Exception {
        log.info("Inside getTimesheetSummaryStaff method of TimesheetServiceImpl :: fromDate: " + fromDate +
                "toDate: " + toDate + " timesheetStatus: " + timesheetStatus);
        if (StringUtils.isBlank(timesheetStatus) || StringUtils.equalsIgnoreCase(timesheetStatus, "ALL")) {
            log.info("Inside the timesheet status of ALL.");
            return timesheetRepository.findTimesheetsByFromDateGreaterThanEqualAndToDateLessThanEqual(fromDate, toDate);
        } else {
            log.info("Inside the timesheet status based on fromDate, toDate and Timesheet status.");
            return timesheetRepository.findTimesheetsByFromDateGreaterThanEqualAndToDateLessThanEqualAndTimesheetStatus
                    (fromDate, toDate, timesheetStatus);
        }
    }

    @Override
    public void approveTimesheet(long timesheetId, String reviewerName, String reviewComments) throws Exception {
        log.info("Inside approveTimesheet method of TimesheetServiceImpl, timesheetId :: " + timesheetId);
        Timesheet timesheetObj = timesheetRepository.findOne(timesheetId);
        timesheetObj.setTimesheetStatus(TimesheetConstants.TIMESHEET_STATUS_APPROVED);
        timesheetObj.setDateApproved(new Date());
        timesheetObj.setNameApproved(reviewerName);
        timesheetObj.setReviewerComments(reviewComments);
        timesheetObj.setDateLastModified(new Date());
        timesheetObj.setNameLastModified(reviewerName);
        //Updating the Timesheet object with necessary information.
        timesheetRepository.save(timesheetObj);
    }

    @Override
    public void rejectTimesheet(long timesheetId, String reviewerName, String reviewComments) throws Exception {
        log.info("Inside rejectTimesheet method of TimesheetServiceImpl, timesheetId :: "
                + timesheetId + " reviewerComments: " + reviewComments);
        Timesheet timesheetObj = timesheetRepository.findOne(timesheetId);
        timesheetObj.setTimesheetStatus(TimesheetConstants.TIMESHEET_STATUS_REJECTED);
        timesheetObj.setDateApproved(null);
        timesheetObj.setNameApproved("");
        timesheetObj.setReviewerComments(reviewComments);
        timesheetObj.setDateLastModified(new Date());
        timesheetObj.setNameLastModified(reviewerName);
        //Updating the Timesheet object with necessary information.
        timesheetRepository.save(timesheetObj);
    }
}
