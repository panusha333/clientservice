package com.example.clientservice.controller;

import com.example.clientservice.model.ClientRequestDTO;
import com.example.clientservice.model.ClientResponseDTO;
import com.example.clientservice.model.ClientUpdateRequestDTO;
import com.example.clientservice.model.SearchRequestDTO;
import com.example.clientservice.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private ClientRequestDTO clientRequestDTO;
    private ClientUpdateRequestDTO clientUpdateRequestDTO;
    private SearchRequestDTO searchRequestDTO;
    private ClientResponseDTO clientResponseDTO;

    @BeforeEach
    void setUp() {
        clientRequestDTO = new ClientRequestDTO();
        clientRequestDTO.setFirstName("John");
        clientRequestDTO.setLastName("Doe");
        clientRequestDTO.setMobileNumber("723456789");
        clientRequestDTO.setIdNumber("7806062507085");

        clientUpdateRequestDTO = new ClientUpdateRequestDTO();
        clientUpdateRequestDTO.setFirstName("Jane");

        searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setFirstName("John");

        clientResponseDTO = new ClientResponseDTO();
        clientResponseDTO.setRefId(UUID.randomUUID());
        clientResponseDTO.setFirstName("John");
        clientResponseDTO.setLastName("Doe");
        clientResponseDTO.setMobileNumber(723456789L);
        clientResponseDTO.setIdNumber(7806062507085L);
        clientResponseDTO.setAddress("1234 Main St");
    }

    @Test
    void getClient_withValidSearchRequestDTO_returnsClientResponseDTOList() {
        // Arrange
        when(clientService.getClient(any(SearchRequestDTO.class))).thenReturn(Collections.singletonList(clientResponseDTO));

        // Act
        ResponseEntity<List<ClientResponseDTO>> response = clientController.getClient(searchRequestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(clientResponseDTO), response.getBody());
    }

    @Test
    void createClient_withValidClientRequestDTO_returnsClientResponseDTO() {
        // Arrange
        when(clientService.saveClient(any(ClientRequestDTO.class))).thenReturn(clientResponseDTO);

        // Act
        ResponseEntity<ClientResponseDTO> response = clientController.createClient(clientRequestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientResponseDTO, response.getBody());
    }

    @Test
    void updateClient_withValidClientUpdateRequestDTOAndRefId_returnsClientResponseDTO() {
        // Arrange
        String refId = clientResponseDTO.getRefId().toString();
        when(clientService.updateClient(refId, clientUpdateRequestDTO)).thenReturn(clientResponseDTO);

        // Act
        ResponseEntity<ClientResponseDTO> response = clientController.updateClient(clientUpdateRequestDTO, refId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientResponseDTO, response.getBody());
    }
}
