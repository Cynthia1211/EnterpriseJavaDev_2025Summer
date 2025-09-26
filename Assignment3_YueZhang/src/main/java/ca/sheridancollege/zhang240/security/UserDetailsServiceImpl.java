package ca.sheridancollege.zhang240.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.zhang240.database.DatabaseAccess;

/**
 * Get user details from database and perform authorities
 *  
 * @author Yue Zhang
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private DatabaseAccess da;

	//get the user details from login form 
	//search the details from H2 database
	//authenticate the login result
	//return as UserDetail which defined in spring secure
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//call method findUserAccount to search by email in H2
		ca.sheridancollege.zhang240.beans.User user = da.findUserAccount(username);
		
		//throw exception if not found
		if (user == null) {
			System.out.println("User not found:" + username);
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
		
		//call method getRolesById to get all roles of the user
		List<String> roleNameList = da.getRolesById(user.getUserId());
		
		//Convert the roles in H2 database into spring security roles
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (roleNameList != null) {
			for (String role : roleNameList) {
				grantList.add(new SimpleGrantedAuthority(role));
			}
		}
		
		//packaged the user details into userDetails
		UserDetails userDetails = (UserDetails) 
				new org.springframework.security.core.userdetails.User(
							user.getUserName(), user.getEncryptedPassword(), grantList);
		return userDetails;
	}

}
