package com.krisnela.test.mvp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.krisnela.test.mvp.dto.Comments;
import com.krisnela.test.mvp.dto.Employee;

@Repository
public class EmployeeDaoImpl {
	@Autowired
    private MongoOperations mongoOperations;
	public void  save(Employee employee) {
		mongoOperations.save(employee, "employee");
	}
	public void  save(Comments comments) {
		mongoOperations.save(comments, "comments");
	}
	public List<Employee> find(Query query) {
		return mongoOperations.find(query, Employee.class);	
	}

}
