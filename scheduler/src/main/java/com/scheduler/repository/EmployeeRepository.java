package com.scheduler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.model.EmployeeDetails;

@Repository
public interface EmployeeRepository extends MongoRepository<EmployeeDetails, String> {

	public EmployeeDetails findByEmployeeEmailID(String patientEmailID);
}
