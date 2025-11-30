package rut.miit.airportweb.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "flight_entity")
@Table(name = "flights", schema = "public")
@Setter
public class FlightEntity {

    private Integer id;
    private String flightNumber;
    private String departureCity;
    private String arrivalCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer totalSeats;
    private Integer availableSeats;
    private FlightStatus status;
    private UserEntity createdBy;
    private List<TicketEntity> tickets = new ArrayList<>();

    public enum FlightStatus {
        SCHEDULED, BOARDING, DEPARTED, ARRIVED
    }

    // Constructors, Getters and Setters
    public FlightEntity() {
        this.status = FlightStatus.SCHEDULED;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() { return id; }

    @NotBlank
    @Column(name = "flight_number", unique = true, nullable = false)
    public String getFlightNumber() { return flightNumber; }

    @NotBlank
    @Column(name = "departure_city", nullable = false)
    public String getDepartureCity() { return departureCity; }

    @NotBlank
    @Column(name = "arrival_city", nullable = false)
    public String getArrivalCity() { return arrivalCity; }

    @NotNull
    @Column(name = "departure_time", nullable = false)
    public LocalDateTime getDepartureTime() { return departureTime; }

    @NotNull
    @Column(name = "arrival_time", nullable = false)
    public LocalDateTime getArrivalTime() { return arrivalTime; }

    @Positive
    @Column(name = "total_seats", nullable = false)
    public Integer getTotalSeats() { return totalSeats; }

    @Positive
    @Column(name = "available_seats", nullable = false)
    public Integer getAvailableSeats() { return availableSeats; }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public FlightStatus getStatus() { return status; }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    public UserEntity getCreatedBy() { return createdBy; }

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    public List<TicketEntity> getTickets() { return tickets; }

}
