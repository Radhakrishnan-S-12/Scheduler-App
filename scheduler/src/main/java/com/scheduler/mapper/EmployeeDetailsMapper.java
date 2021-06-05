package com.scheduler.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scheduler.dto.EmployeeDTO;
import com.scheduler.exception.InvalidInputException;
import com.scheduler.model.EmployeeDetails;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmployeeDetailsMapper {

	@Autowired
	private ModelMapper employeeDetailsMapper;

	public EmployeeDetails convertEmployeeDTOToEmployeeDetails(EmployeeDTO employeeDTO)
			throws InvalidInputException {
		EmployeeDetails employeeDetails = new EmployeeDetails();
		try {
			employeeDetails = employeeDetailsMapper.map(employeeDTO, EmployeeDetails.class);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new InvalidInputException("Please give all the detials");
		}
		return employeeDetails;
	}

	public EmployeeDTO convertEmployeeDetailsToEmployeeDTO(EmployeeDetails employeeDetails) {
		return employeeDetailsMapper.map(employeeDetails, EmployeeDTO.class);
	}
}
