package br.com.alura.school.enrollment;

import static java.lang.String.format;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;


@RestController
public class EnrollmentController {
	
	@Autowired
	private UserRepository userRepository;	
	
	@Autowired
	private CourseRepository courseRepository;
	
	
	private final EnrollmentRepository enrollmentRepository;
	
	EnrollmentController(EnrollmentRepository enrollmentRepository){
		this.enrollmentRepository = enrollmentRepository;
	
	}
	
   
    @GetMapping("/enrollments")
    ResponseEntity<List<EnrollmentResponse>> allEnrollments(){
    	List<Enrollment> listEnrollment = enrollmentRepository.findAll();
    	List<EnrollmentResponse> list = listEnrollment.stream().map(EnrollmentResponse::new).collect(Collectors.toList());
    	return ResponseEntity.ok(list);
    }
   
	
	
	@PostMapping("/courses/{courseCode}/enroll")
    ResponseEntity<Void> newEnrollment(@RequestBody @Valid NewEnrollmentRequest newEnrollmentRequest, @PathVariable("courseCode") String courseCode) {
   	  User user = null;
      List<User> listUser = userRepository.findAll();	
      Course course = null;
      List<Course> listCourse = courseRepository.findAll();
      List<Enrollment> listEnrollment = enrollmentRepository.findAll();
      
      for(int i=0; i<listEnrollment.size();i++) {
    	  if(newEnrollmentRequest.getUsername().equals(listEnrollment.get(i).getUsername()) && courseCode.equals(listEnrollment.get(i).getCourse().getCode())) {
    		return ResponseEntity.badRequest().build();  
    	  }
      }
      
      for(int i=0;i<listUser.size();i++) {
    	  if(newEnrollmentRequest.getUsername().equals(listUser.get(i).getUsername())) {
    		 user = listUser.get(i); 
    	  }
      }
      
      for(int i=0;i<listCourse.size();i++) {
    	  if(courseCode.equals(listCourse.get(i).getCode())) {
    		  course = listCourse.get(i);
    	  }
      }
		
		enrollmentRepository.save(new Enrollment(newEnrollmentRequest.getUsername(), course, user));
        URI location = URI.create(format("/enrollments/%s", newEnrollmentRequest.getUsername()));
        return ResponseEntity.created(location).build();
    }

}
