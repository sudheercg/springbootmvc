package com.arcus.springboot.thymleafdemo.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arcus.springboot.thymleafdemo.entity.Employee;
import com.arcus.springboot.thymleafdemo.service.EmployeeService;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}
	
	// add mapping for "/list"
	
	 @GetMapping("/export/pdf")
	    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        List<Employee> listUsers = employeeService.findAll();
	         
	        UserPDFExporter exporter = new UserPDFExporter(listUsers);
	        exporter.export(response);
	         
	    }
	 
	 
	 
		
		  @GetMapping("/export/excel") public void exportToExcel(HttpServletResponse
		  response) throws IOException {
		  response.setContentType("application/octet-stream"); DateFormat dateFormatter
		  = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
		  dateFormatter.format(new Date());
		  
		  String headerKey = "Content-Disposition"; String headerValue =
		  "attachment; filename=users_" + currentDateTime + ".xlsx";
		  response.setHeader(headerKey, headerValue);
		  
		  List<Employee> listUsers = employeeService.findAll();
		  
		  UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
		  
		  excelExporter.export(response); }
		 

	@GetMapping("/list")
	public String listEmployees(Model theModel) {
		
		// get employees from db
		List<Employee> theEmployees = employeeService.findAll();
		
		// add to the spring model
		theModel.addAttribute("employees", theEmployees);
		
		return "employees/list-employees";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Employee theEmployee = new Employee();
		
		theModel.addAttribute("employee", theEmployee);
		
		return "employees/employee-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {
		
		// get the employee from the service
		Employee theEmployee = employeeService.findById(theId);//select * from employee where id=21
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", theEmployee);
		
		// send over to our form
		return "employees/employee-form";			
	}
	
	
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
		
		// save the employee
		employeeService.save(theEmployee);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";
	}
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {
		
		// delete the employee
		employeeService.deleteById(theId);//delete from employee where id =?
		
		// redirect to /employees/list
		return "redirect:/employees/list";
		
	}
}


















