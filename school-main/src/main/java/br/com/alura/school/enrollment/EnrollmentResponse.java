package br.com.alura.school.enrollment;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;


public class EnrollmentResponse {
	    @JsonProperty
	    private final Date dateenrollment;
	    
	    @JsonProperty
	    private final String username;

	    EnrollmentResponse(Enrollment enrollment) {
	        this.dateenrollment = enrollment.getDateenrollment();
	        this.username = enrollment.getUsername();
	   }

}
