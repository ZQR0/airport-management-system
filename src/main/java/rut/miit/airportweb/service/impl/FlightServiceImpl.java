package rut.miit.airportweb.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import rut.miit.airportweb.dao.entity.FlightEntity;
import rut.miit.airportweb.dao.entity.UserEntity;
import rut.miit.airportweb.dao.repository.FlightRepository;
import rut.miit.airportweb.dao.repository.UserRepository;
import rut.miit.airportweb.dto.FlightCreateDto;
import rut.miit.airportweb.dto.FlightDto;
import rut.miit.airportweb.exception.EntityAlreadyExistsException;
import rut.miit.airportweb.exception.EntityNotFoundException;
import rut.miit.airportweb.exception.NotPermittedOperation;
import rut.miit.airportweb.mapper.FlightMapper;
import rut.miit.airportweb.service.FlightService;
// Добавляем импорт для исключения
import rut.miit.airportweb.exception.EntityAlreadyExistsException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public FlightDto getFlightByNumber(String flightNumber) {
        FlightEntity flight = this.flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Flight with flight number %s not found", flightNumber)));

        return FlightMapper.map(flight);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public FlightDto createFlight(FlightCreateDto flightCreateDto, String createdByUserUsername) {
        UserEntity creator = this.userRepository.findByUsername(createdByUserUsername)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Creator with username %s not found", createdByUserUsername)));

        // Проверяем, не существует ли уже рейс с таким номером
        if (flightRepository.findByFlightNumber(flightCreateDto.getFlightNumber()).isPresent()) {
            throw new EntityAlreadyExistsException(
                    String.format("Flight with number %s already exists", flightCreateDto.getFlightNumber()));
        }

        FlightEntity flight = FlightMapper.map(flightCreateDto);
        flight.setCreatedBy(creator);
        flight.setTickets(new ArrayList<>());

        FlightEntity savedEntity = this.flightRepository.save(flight);
        log.info("Created flight with flight number {}", flightCreateDto.getFlightNumber());

        return FlightMapper.map(savedEntity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteFlight(String flightNumber) {
        FlightEntity flight = this.flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Flight with flight number %s not found", flightNumber)));

        // Проверяем, есть ли билеты на этот рейс
        if (!flight.getTickets().isEmpty()) {
            throw new NotPermittedOperation("Cannot delete flight with existing tickets");
        }

        this.flightRepository.delete(flight);
        log.info("Deleted flight with flight number {}", flightNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightDto> findFlightsByCities(String departureCity, String arrivalCity) {
        return this.flightRepository.findByDepartureCityAndArrivalCity(departureCity, arrivalCity)
                .stream()
                .map(FlightMapper::map)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightDto> findFlightsByTimes(String departureTime, String arrivalTime) {
        // Конвертируем строки в LocalDateTime
        LocalDateTime departure = FlightMapper.parseDateTimeString(departureTime);
        LocalDateTime arrival = FlightMapper.parseDateTimeString(arrivalTime);

        return this.flightRepository.findByDepartureTimeAndArrivalTime(departure, arrival)
                .stream()
                .map(FlightMapper::map)
                .toList();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public FlightDto updateAvailableSeats(String flightNumber, int seatsToBook) {
        FlightEntity flight = this.flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Flight with flight number %s not found", flightNumber)));

        int availableSeats = flight.getAvailableSeats();
        availableSeats -= seatsToBook;

        if (availableSeats < 0) {
            throw new NotPermittedOperation("Available seats cannot be less than zero");
        }

        flight.setAvailableSeats(availableSeats);
        FlightEntity updatedFlight = this.flightRepository.save(flight);
        log.info("Updated available seats for flight {}: {} -> {}",
                flightNumber, flight.getAvailableSeats(), availableSeats);

        return FlightMapper.map(updatedFlight);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightDto> getFlightsList() {
        return this.flightRepository.findAll()
                .stream()
                .map(FlightMapper::map)
                .toList();
    }



}