package com.example.rentcar.cars;


import com.example.rentcar.brand.BrandDto;
import com.example.rentcar.model.ModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

@Service
public class CarsService {
    @Autowired
    private CarsRepo carsRepo;

    public List<CarsDto> showCars(String token) {
    List<CarsTable> cars = carsRepo.findCarsByStatus(CarStatu.AVAILABLE);
        return createCarList(cars);
    }

    public List<CarsDto> createCarList(List<CarsTable> cars) {
        List<CarsDto> carsList = new ArrayList<>();
        for (CarsTable carsTable : cars) {
            BrandDto brandDto = BrandDto.builder()
                    .brandName(carsTable.getModel().getBrand().getName())
                    .build();
            ModelDto modelDto = ModelDto.builder()
                    .brand(brandDto)
                    .name(carsTable.getModel().getName())
                    .year(carsTable.getModel().getYear())
                    .build();


            CarsDto carsDto = CarsDto.builder()
                    .carId(carsTable.getId())
                    .model(modelDto)
                    .plate(carsTable.getPlate())
                    .color(carsTable.getColor())
                    .mileage(carsTable.getMileage())
                    .dailyPrice(carsTable.getDailyPrice())
                    .status(carsTable.getStatus().toString())
                    .gearType(carsTable.getGearType())
                    .fuelType(carsTable.getFuelType())
                    .seatCount(carsTable.getSeatCount())
                    .build();
            carsList.add(carsDto);

        }
        return carsList;
    }

}
