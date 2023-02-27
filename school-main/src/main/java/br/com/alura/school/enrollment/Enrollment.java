package br.com.alura.school.enrollment;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.alura.school.course.*;
import br.com.alura.school.user.User;

@Entity
@Table(name = "Enrollment")
public class Enrollment {
	
	@Id
    @GeneratedValue(strategy = IDENTITY)
	@Column
	private Long id;
	
    @Size(max=20)
    @NotBlank
    @Column(nullable = false)
    private String username;
	
	@Column(nullable = false)
	private Date dateenrollment = new java.sql.Date(System.currentTimeMillis());
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "fk_course", nullable=false)
	private Course course;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "fk_user", nullable=false)
	private User user;
	
    @Deprecated
    protected Enrollment() { }

    public Enrollment(String username, Course course, User user) {
        this.username = username;
        this.course = course;
        this.user = user;
    }
    
    public Enrollment(Long id,String username, Course course, User user) {
        this.id = id;
    	this.username = username;
        this.course = course;
        this.user = user;
        
    }
    
    
    Enrollment(String username){
    	this.username = username;
    }
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateenrollment() {
		return dateenrollment;
	}

	public void setDateenrollment(Date dateenrollment) {
		this.dateenrollment = dateenrollment;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

