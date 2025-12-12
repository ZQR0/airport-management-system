package rut.miit.airportweb.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TicketUpdateDto {

    private String seatNumber;

    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private String status; // "BOOKED", "CHECKED_IN", "BOARDED"

    public TicketUpdateDto() {}
}