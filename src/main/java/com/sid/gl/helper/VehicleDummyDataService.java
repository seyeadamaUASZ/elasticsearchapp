package com.sid.gl.helper;

import com.sid.gl.document.Vehicle;
import com.sid.gl.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class VehicleDummyDataService {
    private static final Logger LOG = LoggerFactory.getLogger(Vehicle.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final VehicleService vehicleService;

    public VehicleDummyDataService(final VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public void insertDummyData() {
        vehicleService.saveIndex(buildVehicle("1", "Audi A1", "AAA-123", "2010-01-01"));
        vehicleService.saveIndex(buildVehicle("2", "Audi A3", "AAB-123", "2011-07-05"));
        vehicleService.saveIndex(buildVehicle("3", "Audi A3", "AAC-123", "2012-10-03"));

        vehicleService.saveIndex(buildVehicle("4", "BMW M3", "AAA-023", "2021-10-06"));
        vehicleService.saveIndex(buildVehicle("5", "BMW 3", "1AA-023", "2001-10-01"));
        vehicleService.saveIndex(buildVehicle("6", "BMW M5", "12A-023", "1999-05-08"));

        vehicleService.saveIndex(buildVehicle("7", "VW Golf", "42A-023", "1991-04-08"));
        vehicleService.saveIndex(buildVehicle("8", "VW Passat", "18A-023", "2021-04-08"));

        vehicleService.saveIndex(buildVehicle("9", "Skoda Kodiaq", "28A-023", "2020-01-04"));
        vehicleService.saveIndex(buildVehicle("10", "Skoda Yeti", "88A-023", "2015-03-09"));
    }

    private static Vehicle buildVehicle(final String id,
                                        final String name,
                                        final String number,
                                        final String date) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        vehicle.setName(name);
        vehicle.setNumber(number);
        try {
            vehicle.setCreated(DATE_FORMAT.parse(date));
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }

        return vehicle;
    }

}
