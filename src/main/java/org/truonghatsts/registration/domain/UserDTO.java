package org.truonghatsts.registration.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserDTO {
    private Integer id;
    @NotBlank
    private String firstName;
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;
    private String password;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "birthday must be in format yyyy-mm-dd")
    private String birthday;
}
