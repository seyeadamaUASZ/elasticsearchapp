package com.sid.gl.controller;

import com.sid.gl.document.Vehicle;
import com.sid.gl.dto.SearchRequestDTO;
import com.sid.gl.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {
    private final VehicleService service;

    @Autowired
    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @PostMapping
    public void index(@RequestBody final Vehicle vehicle) {
        service.saveIndex(vehicle);
    }

    @GetMapping("/{id}")
    public Vehicle getById(@PathVariable final String id) {
        return service.getById(id);
    }

    @PostMapping("/search")
    public List<Vehicle> search(@RequestBody final SearchRequestDTO searchRequestDTO){
        return service.search(searchRequestDTO);
    }

    @GetMapping("/search/{date}")
    public List<Vehicle> getAllVehiclesCreatedSince(
            @PathVariable
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            final Date date) {
        return service.getAllVehiclesCreatedSince(date);
    }

}
