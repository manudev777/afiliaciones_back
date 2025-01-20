package com.rtecnico.afiliaciones.repository;

import com.rtecnico.afiliaciones.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
