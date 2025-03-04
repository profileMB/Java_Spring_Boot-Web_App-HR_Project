package com.projects.hrwebapp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projects.hrwebapp.model.Employee;
import com.projects.hrwebapp.service.EmployeeService;
import com.projects.hrwebapp.validation.OnCreate;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	
	/**
     * Displays the home page with a list of employees
     * 
     * - Retrieves all employees using the EmployeeService
     * - Adds the list to the model under the key "employees"
     * - Returns the "home" view to be rendered
     */
	@GetMapping("/")
	public String home(Model model) {
	    Iterable<Employee> listEmployee = service.getEmployees();
	    model.addAttribute("employees", listEmployee);
	    return "home";
	}

	
	/**
     * Renders the form for creating a new employee
     *
     * - Initializes a new Employee object
     * - Adds the new Employee to the model under the key "employee"
     * - Returns the "formNewEmployee" view for displaying the form
     */
	@GetMapping("/createEmployee")
	public String createEmployee(Model model) {
		Employee e = new Employee();
		model.addAttribute("employee", e);
		return "formNewEmployee";
	}	
	
	
    /**
     * Renders the form for updating an existing employee
     *
     * - Retrieves the Employee with the given id using the EmployeeService
     * - Adds the Employee to the model under the key "employee"
     * - Returns the "formUpdateEmployee" view for displaying the update form
     *
     * @param id The id of the employee to update.
     * @param model The model used to pass data to the view.
     */
	@GetMapping("/updateEmployee/{id}")
	public String updateEmployee(@PathVariable final int id, Model model) {
		Employee e = service.getEmployee(id);		
		model.addAttribute("employee", e);	
		return "formUpdateEmployee";		
	}	
	
	
	/**
	 * Deletes an employee by the given id and redirects to the home page
	 *
	 * - Invokes the EmployeeService to remove the employee with the specified id
	 * - Returns a ModelAndView that redirects to the "/" endpoint after successful deletion
	 *
	 * @param id the unique identifier of the employee to delete
	 */
	@GetMapping("/deleteEmployee/{id}")
	public ModelAndView deleteEmployee(@PathVariable final int id) {
		service.deleteEmployee(id);
		return new ModelAndView("redirect:/");		
	}
   
	
   /**
    * Handles the form submission for creating an employee
    * 
    * - Validates the input data
    * - Attempts to save the employee via the service
    * - If an exception occurs (e.g., duplicate email), adds an error message to the model
    * - Redirects to the home page upon successful save
    * 
    * @param employee The employee object populated from the form
    */
   @PostMapping("/saveEmployee")
   public ModelAndView saveEmployee(@ModelAttribute @Validated({Default.class, OnCreate.class}) Employee employee, BindingResult bindingResult,Model model) {
       
	   // Check if there are any validation errors in the Employee object
	   if (bindingResult.hasErrors()) {
		   
		   // Return the form view to allow the user to fix the errors (e.g., missing or improperly formatted fields)
           return new ModelAndView("formNewEmployee");
       }

       try {
    	   
    	   // Attempt to save the employee to the database through the service
           service.saveEmployee(employee); 
           
       } catch (RuntimeException ex) {
    	   
    	   // Add an error message to the model to inform the user that the email is already associated with an account
    	   model.addAttribute("emailExistsError", "Cet e-mail est déjà rattaché à un compte. Veuillez renseigner une nouvelle adresse e-mail.");
    	   
    	   // Return the form view with the error message displayed
    	   return new ModelAndView("formNewEmployee");
       }

       // If no exceptions occur, redirect the user to the home page
       return new ModelAndView("redirect:/");
   }

   
   /**
    * Handles the form submission for modifying an existing employee
    * 
    * This method follows the same logic as the saveEmployee() method above,  
    * but is specifically intended for updating an employee's information
    */
   @PostMapping("/modifyEmployee")
   public ModelAndView modifyEmployee(@ModelAttribute @Valid Employee employee, BindingResult bindingResult, Model model) {
	    if (bindingResult.hasErrors()) {
	        return new ModelAndView("formUpdateEmployee");
	    }
	    try {
	        service.saveEmployee(employee);
	    } catch (RuntimeException ex) {
	        model.addAttribute("emailExistsError", "Cet e-mail est déjà rattaché à un compte. Veuillez renseigner une nouvelle adresse e-mail.");
	        return new ModelAndView("formUpdateEmployee");
	    }
	    return new ModelAndView("redirect:/");
	}

}
