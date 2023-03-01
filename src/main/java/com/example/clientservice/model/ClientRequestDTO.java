package com.example.clientservice.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientRequestDTO {

    @NotBlank(message = "The first name is required.")
    private String firstName;

    @NotBlank(message = "The lastName is required!")
    private String lastName;

    @Pattern(regexp="(^$|[0-9]{9})", message = "mobile number is incorrect")
    private String mobileNumber;

    @NotBlank(message = "The id number is required!")
    @Pattern(regexp="([0-9]{13})", message = "id number is incorrect")
    private String idNumber;

    private String address;

}
