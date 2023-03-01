package com.example.clientservice.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchRequestDTO {

    private String firstName;

    @Pattern(regexp="(^$|[0-9]{9})", message = "mobile number is incorrect")
    private String mobileNumber;

    @Pattern(regexp="(^$|[0-9]{13})", message = "id number is incorrect")
    private String idNumber;

}
