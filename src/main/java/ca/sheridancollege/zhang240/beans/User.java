package ca.sheridancollege.zhang240.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * User class for user data.
 * Maps to the sec_user table in the H2 database.
 * 
 * @author Yue Zhang
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

	private Long userId;
	@NonNull
	private String userName;
	@NonNull
	private String encryptedPassword;
	@NonNull
	private Boolean enabled;
}
