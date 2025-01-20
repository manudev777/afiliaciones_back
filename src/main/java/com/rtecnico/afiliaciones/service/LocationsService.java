package com.rtecnico.afiliaciones.service;

import com.rtecnico.afiliaciones.model.Department;
import com.rtecnico.afiliaciones.model.District;
import com.rtecnico.afiliaciones.model.Province;
import com.rtecnico.afiliaciones.repository.DepartmentRepository;
import com.rtecnico.afiliaciones.repository.DistrictRepository;
import com.rtecnico.afiliaciones.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationsService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    public List<Department> getDepartaments(){
        return departmentRepository.findAll();
    }

    public List<Province> getProvinces(Long departmentId){
        return provinceRepository.findByDepartmentId(departmentId);
    }

    public List<District> getDistricts(Long provinceId){
        return districtRepository.findByProvinceId(provinceId);
    }


}
