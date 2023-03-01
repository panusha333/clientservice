package com.example.clientservice.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientResponseDTO {

    private UUID refId;

    private String firstName;

    private String lastName;

    private long mobileNumber;

    private long idNumber;

    private String address;

}
