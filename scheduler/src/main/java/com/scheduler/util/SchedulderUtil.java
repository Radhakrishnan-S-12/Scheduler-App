package com.scheduler.util;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scheduler.model.EmployeeDetails;
import com.scheduler.repository.EmployeeRepository;

@Component
public class SchedulderUtil {

	private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
			+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * This method is to check whether the given mail id is really a mail id of an
	 * existing user
	 * 
	 * @param emailID
	 * @return true - yes; false - no
	 */
	public boolean existingUser(String emailID) {
		List<EmployeeDetails> employeeDetails = employeeRepository.findAll();
		return employeeDetails.stream()
				.filter(employeeDetail -> Objects.nonNull(employeeDetail.getEmployeeEmailID()))
				.map(EmployeeDetails::getEmployeeEmailID).collect(Collectors.toList())
				.contains(emailID);
	}

	/**
	 * This method is to check whether the appointment given the employee to cancel
	 * is already cancelled or not
	 * 
	 * @param employeeDetails
	 * @return true - already cancelled; false - not
	 */
	public boolean alreadyCancelledSchedule(EmployeeDetails employeeDetails) {
		if (!employeeDetails.getScheduler().isActiveStatus()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method is to check whether the given mail id is valid or not
	 * 
	 * @param emailID
	 * @return true - valid email id; false - not a valid email id
	 */
	public boolean isValidEmailID(String emailID) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(emailID);
		return matcher.matches();
	}
}
