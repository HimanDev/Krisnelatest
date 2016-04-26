package com.krisnela.test.mvp.dto;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Comments extends AbstractDto {
public Comments() {
		super();
	}
private String commentText;
@DBRef
private Employee employee;
public String getCommentText() {
	return commentText;
}
public void setCommentText(String commentText) {
	this.commentText = commentText;
}
public Employee getEmployee() {
	return employee;
}
public void setEmployee(Employee employee) {
	this.employee = employee;
}

}
