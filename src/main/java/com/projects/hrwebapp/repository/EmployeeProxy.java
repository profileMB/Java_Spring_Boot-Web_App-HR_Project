package com.projects.hrwebapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.projects.hrwebapp.CustomProperties;
import com.projects.hrwebapp.model.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmployeeProxy {
	
	// Inject custom properties (the base API URL)
	@Autowired
	private CustomProperties props;
	
	
	/**
	 * Retrieves a list of all employees via the API
	 *
	 * @return an Iterable containing the retrieved employees
	 */
	public Iterable<Employee> getEmployees() {
		
		// Retrieve the base API URL from the properties and construct the complete URL
		String baseApiUrl = props.getApiUrl();
		String getEmployeesUrl = baseApiUrl + "/employees";

		// Create a RestTemplate instance and perform a GET request to retrieve a list of employees
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Iterable<Employee>> response = restTemplate.exchange(
				getEmployeesUrl,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<Iterable<Employee>> () {}
				);
		
		// Log the response status
		log.debug("Get Employees call " + response.getStatusCode().toString());
		
		// Return the response body (list of employees)
		return response.getBody();	
	}
	
	
	/**
	 * Retrieves a specific employee by ID via the API
	 *
	 * @param id the identifier of the employee
	 * @return the Employee object corresponding to the given ID
	 */
	public Employee getEmployee(int id) {
		String baseApiUrl = props.getApiUrl();
		String getEmployeeUrl = baseApiUrl + "/employee/" + id;

		// Create a RestTemplate instance and perform a GET request to retrieve a specific employee by ID
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Employee> response = restTemplate.exchange(
				getEmployeeUrl, 
				HttpMethod.GET, 
				null,
				Employee.class
				);
		
		log.debug("Get Employee by id call " + response.getStatusCode());
		
		return response.getBody();
	}
	
	
	/**
	 * Creates a new employee via the API
	 *
	 * @param e the Employee object to create
	 * @return the created Employee object as returned by the API
	 */
	public Employee createEmployee(Employee e) {
		String baseApiUrl = props.getApiUrl();
		String createEmployeeUrl = baseApiUrl + "/employee";
		
		// Create a RestTemplate instance and perform a POST request to create a new employee, sending the employee data in the request body
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Employee> request = new HttpEntity<Employee>(e);
		ResponseEntity<Employee> response = restTemplate.exchange(
				createEmployeeUrl,
				HttpMethod.POST,
				request,
				Employee.class
				);
			
		log.debug("Create Employee call " + response.getStatusCode());
			
		return response.getBody();
	}


	/**
	 * Updates an existing employee via the API
	 *
	 * @param e the Employee object containing updated data 
	 * @return the updated Employee object as returned by the API
	 */
	public Employee updateEmployee(Employee e) {
		String baseApiUrl = props.getApiUrl();
		String updateEmployeeUrl = baseApiUrl + "/employee/" + e.getId();

		// Create a RestTemplate instance and perform a PUT request to update an existing employee, sending the updated data in the request body
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Employee> request = new HttpEntity<Employee>(e);
		ResponseEntity<Employee> response = restTemplate.exchange(
				updateEmployeeUrl, 
				HttpMethod.PUT, 
				request, 
				Employee.class
				);
		
		log.debug("Update Employee call " + response.getStatusCode());
		
		return response.getBody();
	}
	

	/**
	 * Deletes an employee via the API
	 *
	 * @param id the identifier of the employee to delete
	 */
	public void deleteEmployee(int id) {
		String baseApiUrl = props.getApiUrl();
		String deleteEmployeeUrl = baseApiUrl + "/employee/" + id;
		
		// Create a RestTemplate instance and perform a DELETE request to remove an employee by their ID
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Void> response = restTemplate.exchange(
				deleteEmployeeUrl, 
				HttpMethod.DELETE, 
				null, 
				Void.class
				);
		
		log.debug("Delete Employee call " + response.getStatusCode());
	}

}