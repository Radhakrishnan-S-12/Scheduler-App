package com.scheduler.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.dto.EmployeeDTO;
import com.scheduler.dto.SchedulerDTO;
import com.scheduler.exception.InvalidInputException;
import com.scheduler.mapper.EmployeeDetailsMapper;
import com.scheduler.model.EmployeeDetails;
import com.scheduler.model.Scheduler;
import com.scheduler.repository.EmployeeRepository;
import com.scheduler.util.SchedulderUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchedulerService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeDetailsMapper detailsMapper;

	@Autowired
	private SchedulderUtil schedulderUtil;

	/**
	 * This method is to retrieve a employee's schedule details using their email id
	 * 
	 * @param email - patient's email id
	 * @return patientDTO - patient details
	 */

	public EmployeeDTO getEmployeeDetails(String email) {
		EmployeeDetails employeeDetails = employeeRepository.findByEmployeeEmailID(email);
		if (Objects.nonNull(employeeDetails)) {
			log.info("Retrieved details, {}", employeeDetails.toString());
			return detailsMapper.convertEmployeeDetailsToEmployeeDTO(employeeDetails);
		} else {
			log.error("No users available for the given email id - {}", email);
			return new EmployeeDTO();
		}
	}

	/**
	 * This method is to save all the details about an employee and his training
	 * period
	 * 
	 * @param employeeDTO - Patient details
	 * @return true - saved; false - not saved
	 * @throws InvalidInputException
	 */

	public boolean saveScheduleDetails(EmployeeDTO employeeDTO) throws InvalidInputException {
		EmployeeDetails employeeDetails = new EmployeeDetails();
		if (schedulderUtil.isValidEmailID(employeeDTO.getEmployeeEmailID())) {
			try {
				employeeDetails = detailsMapper.convertEmployeeDTOToEmployeeDetails(employeeDTO);
			} catch (Exception e) {
				return false;
			}
			log.info("Patient Details before saving, {}", employeeDetails.toString());
			EmployeeDetails details = employeeRepository.save(employeeDetails);
			log.info("Patient Details after saving, {}", details.toString());
			return true;
		} else
			return false;
	}

	/**
	 * This method is to cancel an employee's training schedule
	 * 
	 * @param emailID - employee's mail id
	 * @return true - cancelled; false - not cancelled
	 * @throws InvalidInputException
	 */
	public boolean cancelSchedule(String emailID) throws InvalidInputException {
		EmployeeDTO employeeDTO = getEmployeeDetails(emailID);
		if (Objects.nonNull(employeeDTO.getEmployeeEmailID())) {
			EmployeeDetails employeeDetails = detailsMapper
					.convertEmployeeDTOToEmployeeDetails(employeeDTO);
			if (schedulderUtil.alreadyCancelledSchedule(employeeDetails)) {
				Scheduler scheduler = employeeDetails.getScheduler();
				scheduler.setActiveStatus(false);
				employeeDetails.setScheduler(scheduler);
				EmployeeDetails updatedDetails = employeeRepository.save(employeeDetails);
				return updatedDetails.getScheduler().isActiveStatus();
			} else {
				log.info("Already Cancelled schedule");
				return true;
			}
		} else
			return false;
	}

	/**
	 * This method is to update a schedule
	 * 
	 * @param employeeDTO - Employee details
	 * @return schedulerDTO - updated schedule details
	 * @throws InvalidInputException
	 */
	public SchedulerDTO updateSchedule(EmployeeDTO employeeDTO) throws InvalidInputException {
		if (schedulderUtil.existingUser(employeeDTO.getEmployeeEmailID())) {
			EmployeeDetails employeeDetails = detailsMapper
					.convertEmployeeDTOToEmployeeDetails(employeeDTO);
			EmployeeDetails updatedDetails = employeeRepository.save(employeeDetails);
			SchedulerDTO schedulerDTO = new SchedulerDTO();
			if (Objects.nonNull(updatedDetails.getScheduler())) {
				schedulerDTO = detailsMapper.convertEmployeeDetailsToEmployeeDTO(employeeDetails)
						.getScheduler();
			}
			return schedulerDTO;
		} else
			return new SchedulerDTO();
	}

}
