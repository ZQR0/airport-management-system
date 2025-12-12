package rut.miit.airportweb.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rut.miit.airportweb.dao.entity.FlightEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Integer> {

    @Query("SELECT f FROM flight_entity f WHERE f.flightNumber = :flightNumber")
    Optional<FlightEntity> findByFlightNumber(String flightNumber);

    // Исправляем типы параметров
    @Query("SELECT DISTINCT f FROM flight_entity f WHERE f.departureTime = :departureTime AND f.arrivalTime = :arrivalTime")
    List<FlightEntity> findByDepartureTimeAndArrivalTime(LocalDateTime departureTime, LocalDateTime arrivalTime);

    @Query("SELECT DISTINCT f FROM flight_entity f WHERE f.departureCity = :departureCity AND f.arrivalCity = :arrivalCity")
    List<FlightEntity> findByDepartureCityAndArrivalCity(String departureCity, String arrivalCity);

    // Добавляем полезные методы
    @Query("SELECT f FROM flight_entity f WHERE f.status = :status")
    List<FlightEntity> findByStatus(String status);

    @Query("SELECT f FROM flight_entity f WHERE f.availableSeats > 0")
    List<FlightEntity> findAvailableFlights();

    @Query("SELECT f FROM flight_entity f WHERE f.departureCity = :city OR f.arrivalCity = :city")
    List<FlightEntity> findFlightsByCity(String city);
}