package edu.htw.magw.dreamcatcher.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.htw.magw.dreamcatcher.domain.entity.ApplicationUser;

public interface ApplicationUserDao extends JpaRepository<ApplicationUser, Long> {

public ApplicationUser findByFirstName(String firstName) ;
	 
	public ApplicationUser findByFirstNameAndLastName(String firstName, String lastName) ;
	
	public ApplicationUser findByUserNameAndPasswordString(String userName, String passwordString) ;

	public ApplicationUser findByUserName(String username);
	
	public ApplicationUser findById(long id);

}