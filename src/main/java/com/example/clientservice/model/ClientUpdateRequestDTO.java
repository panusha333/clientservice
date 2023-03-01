package com.example.clientservice.model;

import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientUpdateRequestDTO {


    private String firstName;

    private String lastName;

    @Pattern(regexp="(^$|[0-9]{9})", message = "mobile number is incorrect")
    private String mobileNumber;

    @Pattern(regexp="(^$|[0-9]{13})", message = "id number is incorrect")
    private String idNumber;

    private String address;

}
