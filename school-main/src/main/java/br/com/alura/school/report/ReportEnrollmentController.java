package br.com.alura.school.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.alura.school.enrollment.EnrollmentRepository;


@RestController
public class ReportEnrollmentController {
	
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	
	   
    @GetMapping("/courses/enroll/report")
    ResponseEntity<List<ReportEnrollmentResponse>> allEnrollments(){   	
    List<ReportEnrollmentResponse> list = enrollmentRepository.findAllReportEnrollment();
    if(list.size() == 0) {
    	return ResponseEntity.noContent().build();
    }
      	return ResponseEntity.ok(list);
    }
  

}
