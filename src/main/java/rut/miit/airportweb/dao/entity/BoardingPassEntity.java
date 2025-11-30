package rut.miit.airportweb.dao.entity;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "boarding_pass_entity")
@Table(name = "boarding_passes", schema = "public")
@Setter
public class BoardingPassEntity {

    private Integer id;
    private TicketEntity ticket;
    private LocalDateTime checkInTime;
    private Boolean passportVerified = false;
    private Boolean luggageVerified = false;
    private Boolean boarded = false;
    private UserEntity verifiedByBorderGuard;
    private UserEntity verifiedByCustoms;

    // Constructors, Getters and Setters
    public BoardingPassEntity() {
        this.checkInTime = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() { return id; }

    @OneToOne
    @JoinColumn(name = "ticket_id", unique = true, nullable = false)
    public TicketEntity getTicket() { return ticket; }

    @Column(name = "check_in_time")
    public LocalDateTime getCheckInTime() { return checkInTime; }

    @Column(name = "passport_verified")
    public Boolean getPassportVerified() { return passportVerified; }

    @Column(name = "luggage_verified")
    public Boolean getLuggageVerified() { return luggageVerified; }

    @Column(name = "boarded")
    public Boolean getBoarded() { return boarded; }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by_border_guard")
    public UserEntity getVerifiedByBorderGuard() { return verifiedByBorderGuard; }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by_customs")
    public UserEntity getVerifiedByCustoms() { return verifiedByCustoms; }

}
