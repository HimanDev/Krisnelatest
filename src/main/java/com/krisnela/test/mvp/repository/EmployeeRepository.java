package com.krisnela.test.mvp.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.krisnela.test.mvp.dto.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
	public Employee findByObjectId(String objectId);
}
