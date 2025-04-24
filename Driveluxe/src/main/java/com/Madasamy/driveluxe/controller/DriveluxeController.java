package com.Madasamy.driveluxe.controller;

import com.Madasamy.driveluxe.model.Car;
import com.Madasamy.driveluxe.service.DriveluxeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController           //  The class handles HTTP requests  & response json format return
@RequestMapping("/api/cars")   //  URL for all APIs in this controller for base path

public class DriveluxeController {


        private final DriveluxeService driveluxeService;   //  Reference from DriveluxeService class

    //  constructor

        public DriveluxeController(DriveluxeService driveluxeService) {
            this.driveluxeService = driveluxeService;
        }

    // Add a New Car

    @PostMapping
    public Car addCar(@RequestBody Car car) {
        return driveluxeService.addCar(car);
    }

    // Get All Cars

    @GetMapping

        public List<Car> getAllCars() {
            return driveluxeService.getAllCars();
        }

        // Get Car by ID

        @GetMapping("/{id}")
        public ResponseEntity<Car> getCarsById(@PathVariable int id) {
            Car car = driveluxeService.getCarsById(id);
            return car != null ? ResponseEntity.ok(car) : ResponseEntity.notFound().build();
        }




   // Update Car
   @PutMapping("/{id}")
   public ResponseEntity<Car> updateCar(@PathVariable int id, @RequestBody Car car) {
       Car updatedCar = driveluxeService.updateCar(id, car);
       return updatedCar != null ? ResponseEntity.ok(updatedCar) : ResponseEntity.notFound().build();
   }




    // Delete Cars
       @DeleteMapping("/{id}")
     public ResponseEntity<String> deleteTodo(@PathVariable int id) {
           driveluxeService.deleteCar(id);
          return ResponseEntity.ok("Car deleted successfully!");
     }


    // search
    @GetMapping("/searchCars")
    public ResponseEntity<List<Car>> searchCars(@RequestParam(required = false) String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Car> results = driveluxeService.searchCars(keyword);
        return ResponseEntity.ok(results);
    }


//    // New endpoint to get cars ordered by created date (latest first)
//    @GetMapping("/latestCars")
//    public List<Car> getAllCarsLatestFirst() {
//        return driveluxeService.getAllCarsLatestFirst();
//    }






    }






