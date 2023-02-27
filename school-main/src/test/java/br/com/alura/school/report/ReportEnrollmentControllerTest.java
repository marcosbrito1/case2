package br.com.alura.school.report;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.enrollment.Enrollment;
import br.com.alura.school.enrollment.EnrollmentRepository;
import br.com.alura.school.enrollment.NewEnrollmentRequest;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ReportEnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
	@Autowired
	private UserRepository userRepository;	

    
    @Test
    void should_retrieve_enrollment_empty() throws Exception {
      mockMvc.perform(get("/courses/enroll/report")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))      
                .andExpect(result -> {assertThat(result.getResponse().getContentAsString()).isEmpty();});
    }
    
    @Test
    void should_retrieve_all_enrollments() throws Exception {
      	courseRepository.save(new Course("spring-20", "Spring", "Spring MVC."));
        userRepository.save(new User("alexia", "alexia@email.com"));
        Course course = courseRepository.findByCode("spring-20").get();
        User user = userRepository.findByUsername("alexia").get();
        NewEnrollmentRequest newEnrollmentRequest = new NewEnrollmentRequest("alexia");
       enrollmentRepository.save(new Enrollment(3L,newEnrollmentRequest.getUsername(), course, user));
         
        mockMvc.perform(get("/courses/enroll/report")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].email", is("alexia@email.com")))
                .andExpect(jsonPath("$[0].quantidade_matriculas", is(1)));
    	
    }

}