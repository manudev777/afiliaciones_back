package com.rtecnico.afiliaciones.controller;

import com.rtecnico.afiliaciones.model.Department;
import com.rtecnico.afiliaciones.model.District;
import com.rtecnico.afiliaciones.model.Province;
import com.rtecnico.afiliaciones.service.LocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("locations")
@RestController
public class LocationsController {

    @Autowired
    private LocationsService locationsService;

    @GetMapping("departaments")
    public List<Department> getDepartaments(){
        return locationsService.getDepartaments();
    }

    @GetMapping("provinces")
    public List<Province> getProvinces(Long departmentId){
        return locationsService.getProvinces(departmentId);
    }

    @GetMapping("districts")
    public List<District> getDistricts(Long provinceId){
        return locationsService.getDistricts(provinceId);
    }
}
