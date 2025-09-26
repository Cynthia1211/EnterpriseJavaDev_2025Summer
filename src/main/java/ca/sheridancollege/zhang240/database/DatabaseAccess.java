package ca.sheridancollege.zhang240.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.zhang240.beans.Course;
import ca.sheridancollege.zhang240.beans.User;

/**
 * 
 * Uses Spring's JdbcTemplate for database access.
 * Defines methods to perform CRUD operations on the course database.
 * 
 * @author Yue Zhang
 */
@Repository
public class DatabaseAccess {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 
	 * search the user record from H2 database and return the records packaged as User
	 *
	 */
	public User findUserAccount(String userName) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user where userName = :userName";
		namedParameters.addValue("userName", userName);
		try {
		return jdbc.queryForObject(query, namedParameters,new BeanPropertyRowMapper<User>(User.class));
		} catch (EmptyResultDataAccessException erdae) {
		return null; //return null if not found
		}
	}
	
	/**
	 * 
	 * search the user's role with user_id return user's all role records
	 *
	 */
	public List<String> getRolesById(Long userId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT sec_role.roleName " + "FROM user_role, sec_role "
				+ "WHERE user_role.roleId = sec_role.roleId " + "AND userId = :userId";
		namedParameters.addValue("userId", userId);
		return jdbc.queryForList(query, namedParameters, String.class);
	}
	
	/**
	 * 
	 * Get a list of all courses
	 *
	 */
	public List<Course> getAllCourses() {

		String query = "SELECT * FROM COURSES";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Course>(Course.class));
	}
	
	/**
	 * 
	 * Inserts a new course into the database.
	 * 
	 * @param courseTitle - COURSE_TITLE
	 * @param category - CATEGORY
	 * @param instructor - INSTRUCTOR
	 * @param price - PRICE
	 * @param duration - DURATION
	 * @param platform - PLATFORM
	 * @param description - DESCRIPTION
	 *
	 */
	public void insertCourse(Course course) {
	    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	    String query = "INSERT INTO COURSES (COURSE_TITLE, CATEGORY, INSTRUCTOR, PRICE, DURATION, PLATFORM, DESCRIPTION) " +
	                   "VALUES (:courseTitle, :category, :instructor, :price, :duration, :platform, :description)";
	    namedParameters.addValue("courseTitle", course.getCourseTitle());
	    namedParameters.addValue("category", course.getCategory());
	    namedParameters.addValue("instructor", course.getInstructor());
	    namedParameters.addValue("price", course.getPrice());
	    namedParameters.addValue("duration", course.getDuration());
	    namedParameters.addValue("platform", course.getPlatform());
	    namedParameters.addValue("description", course.getDescription());

	    int rowsAffected = jdbc.update(query, namedParameters);
	}
	
	/**
	 * 
	 * Delete a course from the database.
	 * 
	 * @param courseId - COURSE_ID
	 *
	 */
	public void deleteCourseById(Long id) {
	    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	    String query = "DELETE FROM COURSES WHERE COURSE_ID = :id";
	    namedParameters.addValue("id", id);

	    if (jdbc.update(query, namedParameters) > 0)
	        System.out.println("Deleted course " + id + " from the database.");
	}

	/**
	 * 
	 * Update the price of a course from the database.
	 * 
	 * @param courseId - COURSE_ID
	 * @param price - PRICE
	 *
	 */
	public void updateCoursePrice(Long id, Double price) {
	    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	    String query = "UPDATE COURSES SET PRICE = :price WHERE COURSE_ID = :id";
	    namedParameters.addValue("id", id);
	    namedParameters.addValue("price", price);

	    int rows = jdbc.update(query, namedParameters);
	    if (rows > 0) {
	        System.out.println("Course " + id + " has been updated.");
	    }
	}

		
}
