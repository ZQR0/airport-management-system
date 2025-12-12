package rut.miit.airportweb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PassengerUpdateDto {

    @Size(max = 20, message = "Phone number cannot be longer than 20 symbols")
    private String phone;

    @Email(message = "Email must have email pattern")
    @Size(max = 100, message = "Email cannot be longer than 100 symbols")
    private String email;

    private Boolean luggageChecked;

    public PassengerUpdateDto() {}
}