package com.example.app.repository;

import com.example.app.entity.Attendance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    boolean existsByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate date);
    boolean existsByEmployeeIdAndAttendanceDateAndIdNot(Long employeeId, LocalDate date, Long id);
    @Query(value = "select a.* from attendance_records a join employees e on e.id=a.employee_id where a.id > :afterId " +
        "and (:employeeId is null or e.id = :employeeId) and (:date is null or a.attendance_date = :date) " +
        "and (:name is null or lower(e.first_name || ' ' || e.last_name) like '%' || lower(:name) || '%') order by a.id", nativeQuery = true)
    List<Attendance> search(@Param("afterId") Long afterId, @Param("employeeId") Long employeeId,
        @Param("date") LocalDate date, @Param("name") String name, Pageable pageable);
}
