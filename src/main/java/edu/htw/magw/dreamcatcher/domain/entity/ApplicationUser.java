package edu.htw.magw.dreamcatcher.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import edu.htw.magw.dreamcatcher.domain.entity.base.AbstractEntityBase;

@Entity
public class ApplicationUser extends AbstractEntityBase {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	private String lastName;
	
	@NotNull
	private String firstName;
	
	@NotNull
	@Column(unique = true)
	private String userName;
	
	@NotNull
	private GenderType gender ;
	
	@NotNull
	private String email;
	
	@NotNull
	private String passwordString;

	@NotNull
	private UserType userType;
	
	@Lob
	private Byte[] userPicture ;
	
	@OneToMany
	private List<Series> seriesList ;
	
	public ApplicationUser() {
		super();
	}
	
	public ApplicationUser(String lastName, String firstName) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPasswordString() {
		return passwordString;
	}

	public void setPasswordString(String passwordString) {
		this.passwordString = passwordString;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Byte[] getUserPicture() {
		return userPicture;
	}

	public void setUserPicture(Byte[] userPicture) {
		this.userPicture = userPicture;
	}

	/**
	 * @return the series
	 */
	public List<Series> getSeries() {
		return seriesList;
	}

	/**
	 * @param series the series to set
	 */
	public void setSeries(List<Series> seriesList) {
		this.seriesList = seriesList;
	}

	public enum UserType {
		Admin, User
	}
	
	public enum GenderType {
		Female, Male
	}
}
