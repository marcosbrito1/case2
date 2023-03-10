package br.com.alura.school.enrollment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class NewEnrollmentRequest {

	    @Size(max=20)
	    @NotBlank
	    @JsonProperty("username")
	    private final String username;

	    @JsonCreator
	   public NewEnrollmentRequest(String username) {
	        this.username = username;
	    }

	    public String getUsername() {
	        return username;
	    }
	    
	    Enrollment toEntity() {
	        return new Enrollment(username);
	    }

}
