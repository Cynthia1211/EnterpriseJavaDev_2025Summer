package ca.sheridancollege.zhang240.beans;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Course class for course data.
 * Maps to the COURSES table in the H2 database.
 * 
 * @author Yue Zhang
 */

@Data
@NoArgsConstructor
public class Course {
	
	private Long courseId;
    private String courseTitle;
    private String category;
    private String instructor;
    private Double price;
    private Double duration;
    private String platform;
    private String description;
    
}
