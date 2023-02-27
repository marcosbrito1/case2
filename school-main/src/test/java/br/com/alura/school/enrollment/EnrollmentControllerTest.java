package br.com.alura.school.enrollment;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EnrollmentControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
	@Autowired
	private UserRepository userRepository;	
    

    @Test
    void should_add_new_enrollment() throws Exception {
    	courseRepository.save(new Course("spring-1", "Spring Basics", "Spring Core and Spring MVC."));
        userRepository.save(new User("john", "john@email.com"));
        NewEnrollmentRequest newEnrollmentRequest = new NewEnrollmentRequest("john");

        mockMvc.perform(post("/courses/spring-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newEnrollmentRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/enrollments/john"));
    }
    
    @Test
    void should_not_allow_duplication_enrollment() throws Exception {
    	courseRepository.save(new Course("spring-10", "Spring", "Spring MVC."));
        userRepository.save(new User("alex", "alex@email.com"));
        Course course = courseRepository.findByCode("spring-10").get();
        User user = userRepository.findByUsername("alex").get();
        NewEnrollmentRequest newEnrollmentRequest = new NewEnrollmentRequest("alex");
       enrollmentRepository.save(new Enrollment(1L,newEnrollmentRequest.getUsername(), course, user));
      
        
        mockMvc.perform(post("/courses/spring-10/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newEnrollmentRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}