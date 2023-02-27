package br.com.alura.school.enrollment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.school.report.ReportEnrollmentResponse;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{
	
	@Query(value="select E.user.email as email, count(*) as quantidade_matriculas from Enrollment as E group by E.user.email order by quantidade_matriculas desc")
	List<ReportEnrollmentResponse> findAllReportEnrollment();
	
	
}
