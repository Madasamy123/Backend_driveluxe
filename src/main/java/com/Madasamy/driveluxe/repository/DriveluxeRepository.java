package com.madasamy.driveluxe.repository;

import com.madasamy.driveluxe.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

@Repository
public interface DriveluxeRepository extends JpaRepository<Car, Integer> {   //  < Entity,primary key type >

    // custome search query

    @Query("SELECT c FROM Car c WHERE " +
            "(c.brand IS NOT NULL AND LOWER(c.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
            "(c.model IS NOT NULL AND LOWER(c.model) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
            "(c.fuelType IS NOT NULL AND LOWER(c.fuelType) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Car> searchByKeyword(@Param("keyword") String keyword);

    Optional<Car> findByModel(String model);


//    // New query to get cars ordered by creation date (latest first)
//    @Query("SELECT c FROM Car c ORDER BY c.createdAt DESC")
//    List<Car> findAllCarsLatestFirst();


}


