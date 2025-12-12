package rut.miit.airportweb.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserUpdateDto {

    @Size(max = 100, message = "Password hash cannot be longer than 100 symbols")
    private String passwordHash;

    @Size(max = 50, message = "First name cannot be longer than 50 symbols")
    private String firstName;

    @Size(max = 50, message = "Last name cannot be longer than 50 symbols")
    private String lastName;

    public UserUpdateDto() {}
}