package com.example.app.repository;

import com.example.app.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    @Query(value = "select * from employees e where e.id > :afterId and (:name is null or lower(e.first_name || ' ' || e.last_name) like '%' || lower(:name) || '%') order by e.id", nativeQuery = true)
    List<Employee> search(@Param("afterId") Long afterId, @Param("name") String name, Pageable pageable);
}
