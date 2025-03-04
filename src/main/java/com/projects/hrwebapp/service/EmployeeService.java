package com.projects.hrwebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.hrwebapp.model.Employee;
import com.projects.hrwebapp.repository.EmployeeProxy;


@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeProxy employeeProxy;
	
	
	public Employee getEmployee(final int id) {
		return employeeProxy.getEmployee(id);
	}
	
	public Iterable<Employee> getEmployees() {
		return employeeProxy.getEmployees();
	}
	
	public void deleteEmployee(final int id) {
		employeeProxy.deleteEmployee(id);
	}
	
	public Employee saveEmployee(Employee employee) {
		Employee savedEmployee;
		
		// Convert last name to uppercase 
		employee.setLastName(employee.getLastName().toUpperCase());
		
	    // Check if the employee is new (ID is null) to create a new record,  
	    // otherwise update the existing one.
		if(employee.getId() == null) {
			savedEmployee = employeeProxy.createEmployee(employee);
		} else {
			savedEmployee = employeeProxy.updateEmployee(employee);
		}
		
		return savedEmployee;
		
	}
	
}