package com.rtecnico.afiliaciones.repository;

import com.rtecnico.afiliaciones.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
    List<Province> findByDepartmentId(Long departmentId);
}
