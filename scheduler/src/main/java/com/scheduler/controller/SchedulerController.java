package com.scheduler.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.dto.EmployeeDTO;
import com.scheduler.dto.SchedulerDTO;
import com.scheduler.exception.InvalidInputException;
import com.scheduler.exception.InvalidUserException;
import com.scheduler.service.SchedulerService;

@RestController
public class SchedulerController {

	@Autowired
	private SchedulerService schedulerService;

	@GetMapping(value = "/employeedetail/{email}")
	public ResponseEntity<SchedulerDTO> getPatientDetails(@PathVariable String email) {
		EmployeeDTO employeeDTO = schedulerService.getEmployeeDetails(email);
		SchedulerDTO schedulerDTO = employeeDTO.getScheduler();
		return Objects.nonNull(employeeDTO.getScheduler())
				? new ResponseEntity<>(schedulerDTO, HttpStatus.OK)
				: new ResponseEntity<>(schedulerDTO, HttpStatus.NO_CONTENT);
	}

	@PostMapping(value = "/scheduledetail")
	public ResponseEntity<String> saveScheduleDetails(@RequestBody EmployeeDTO employeeDetails)
			throws InvalidInputException {
		return schedulerService.saveScheduleDetails(employeeDetails)
				? new ResponseEntity<>("Successfully saved", HttpStatus.OK)
				: new ResponseEntity<>("Please give valid details", HttpStatus.BAD_REQUEST);
	}

	@PatchMapping(value = "/cancelschedule/{emailID}")
	public ResponseEntity<String> cancelSchedule(@PathVariable String emailID)
			throws InvalidInputException {
		boolean activeStatus = schedulerService.cancelSchedule(emailID);
		if (!activeStatus) {
			return new ResponseEntity<>("Cancelled Successfully", HttpStatus.OK);
		} else
			return new ResponseEntity<>("Already cancelled scheduler", HttpStatus.BAD_REQUEST);
	}

	@PutMapping(value = "/modifyschedule")
	public ResponseEntity<SchedulerDTO> modifySchedule(@RequestParam String emailID,
			@RequestBody EmployeeDTO employeeDTO)
			throws InvalidInputException, InvalidUserException {
		if (emailID.equals(employeeDTO.getEmployeeEmailID())) {
			SchedulerDTO schedulerDTO = schedulerService.updateSchedule(employeeDTO);
			return Objects.nonNull(schedulerDTO.getStartDate())
					? new ResponseEntity<SchedulerDTO>(schedulerDTO, HttpStatus.OK)
					: new ResponseEntity<SchedulerDTO>(new SchedulerDTO(), HttpStatus.BAD_REQUEST);
		} else {
			throw new InvalidUserException("This user doesn't exist - " + emailID);
		}
	}
}
