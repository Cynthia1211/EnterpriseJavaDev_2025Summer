package ca.sheridancollege.zhang240.controllers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.zhang240.beans.Course;
import ca.sheridancollege.zhang240.database.DatabaseAccess;

/**
 * Process web requests:
 * login, logout
 * insert, update, and delete courses.
 *
 *	@author Yue Zhang
 */
@Controller
public class HomeController {
	
	@Autowired
	@Lazy //to avoid creating tables and schema multiple times
	private DatabaseAccess da;
	
	List<Course> courseList = new CopyOnWriteArrayList<Course>();
	
	/**
     * Set index page(public) as / page.
     * Shows all courses on this page
     * 
     */
	@GetMapping("/")
	public String index(Model model, @ModelAttribute Course course) {
		model.addAttribute("courseList", da.getAllCourses());
		return "index";
	}
	
	/**
     * Navigation to login page(public) 
     * 
     */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	/**
     * Navigation to error page(public)
     * 
     */
	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}
	
	/**
     * Navigation to insert page(secure) 
     * 
     */
	@GetMapping("/secure/insert")
	public String insert(Model model, @ModelAttribute Course course) {
		return "/secure/insert";
	}
	
	/**
     * Navigation to delete page(secure and ADMIN only)  
     * 
     */
	@GetMapping("/secure/delete")
	public String delete(Model model, @ModelAttribute Course course) {
		model.addAttribute("courseList", da.getAllCourses());
		return "/secure/delete";
	}
	
	/**
     * Navigation to update page(secure) 
     * 
     */
	@GetMapping("/secure/update")
	public String update(Model model, @ModelAttribute Course course) {
		model.addAttribute("courseList", da.getAllCourses());
		return "/secure/update";
	}

	/**
     * Inserts a new course based on form input.
     * Show all courses after insert
     * 
     */
	@PostMapping("/insertCourse")
	public String insertCourse(Model model, @ModelAttribute Course course) {
	    da.insertCourse(course);
	    model.addAttribute("courseList", da.getAllCourses());
	    return "/secure/insert"; 
	}

	/**
     * Deletes a course by selecting course id from the dropdown list 
     * Show all courses before and after deletion
     * 
     */
	@GetMapping("/deleteCourse")
	public String deleteCourse(Model model, 
					  @RequestParam("id") Long id) {
	    da.deleteCourseById(id); 
	    model.addAttribute("courseList", da.getAllCourses());  
	    return "secure/delete";  
	}

	/**
     * Update the price of a course by selecting course id from the dropdown list 
     * Show all courses before and after deletion
     * 
     */
	@PostMapping("/updateCourse")
	public String updateCourse(Model model,
	                   @RequestParam("id") Long id,
	                   @RequestParam("price") Double price) {
	    da.updateCoursePrice(id, price);
	    model.addAttribute("courseList", da.getAllCourses());
	    return "secure/update";
	}
	
}
