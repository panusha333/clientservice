package com.example.clientservice.service;

import com.example.clientservice.exceptions.InvalidIdNumberException;
import com.example.clientservice.exceptions.RecordExistsException;
import com.example.clientservice.exceptions.RecordNotExistsException;
import com.example.clientservice.exceptions.SearchParamNotFoundException;
import com.example.clientservice.model.ClientRequestDTO;
import com.example.clientservice.model.ClientResponseDTO;
import com.example.clientservice.model.ClientUpdateRequestDTO;
import com.example.clientservice.model.SearchRequestDTO;
import com.example.clientservice.service.ClientService;
import com.example.clientservice.service.impl.ClientServiceImpl;
import com.example.clientservice.util.IDNumberValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @InjectMocks
    ClientServiceImpl clientServiceImpl;

    ClientRequestDTO clientRequestDTO;
    ClientUpdateRequestDTO clientUpdateRequestDTO;

    SearchRequestDTO searchRequestDTO;
    @BeforeEach
    public void setup() {
        clientRequestDTO = new ClientRequestDTO();
        clientRequestDTO.setFirstName("John");
        clientRequestDTO.setLastName("Doe");
        clientRequestDTO.setIdNumber("7806062507085");
        clientRequestDTO.setMobileNumber("987654321");

        clientUpdateRequestDTO = new ClientUpdateRequestDTO();
        clientUpdateRequestDTO.setFirstName("Jane");

        searchRequestDTO = new SearchRequestDTO();
    }

    @Test
    public void testSaveClient() {
        ClientResponseDTO responseDTO = clientServiceImpl.saveClient(clientRequestDTO);
        Assertions.assertNotNull(responseDTO.getRefId());
    }

    @Test
    public void testSaveClientWithInvalidIdNumber() {
        clientRequestDTO.setIdNumber("7806062507083");
        Assertions.assertThrows(InvalidIdNumberException.class, () -> {
            clientServiceImpl.saveClient(clientRequestDTO);
        });
    }

    @Test
    public void testSaveClientWithRecordExistsException() {
        clientServiceImpl.saveClient(clientRequestDTO);
        Assertions.assertThrows(RecordExistsException.class, () -> {
            clientServiceImpl.saveClient(clientRequestDTO);
        });
    }

    @Test
    public void testUpdateClient() {
        ClientResponseDTO responseDTO = clientServiceImpl.saveClient(clientRequestDTO);

        String recordId = responseDTO.getRefId().toString();
        ClientResponseDTO updatedResponseDTO = clientServiceImpl.updateClient(recordId, clientUpdateRequestDTO);

        Assertions.assertEquals(clientUpdateRequestDTO.getFirstName(), updatedResponseDTO.getFirstName());
    }

    @Test
    public void testUpdateClientWithInvalidRecordId() {
        Assertions.assertThrows(SearchParamNotFoundException.class, () -> {
            clientServiceImpl.updateClient("invalid-record-id", clientUpdateRequestDTO);
        });
    }

    @Test
    public void testUpdateClientWithRecordNotExistsException() {
        Assertions.assertThrows(RecordNotExistsException.class, () -> {
            clientServiceImpl.updateClient("bf0dc926-1bda-4800-a6ff-ab162dcff968", clientUpdateRequestDTO);
        });
    }

    @Test
    public void testGetClientReturnsCorrectClient() {
        ClientResponseDTO responseDTO = clientServiceImpl.saveClient(clientRequestDTO);
        searchRequestDTO.setFirstName("John");
        List<ClientResponseDTO> actualClients = clientServiceImpl.getClient(searchRequestDTO);
        Assertions.assertEquals(List.of(responseDTO), actualClients);
        searchRequestDTO= new SearchRequestDTO();
        searchRequestDTO.setIdNumber("7806062507085");
        actualClients = clientServiceImpl.getClient(searchRequestDTO);
        Assertions.assertEquals(List.of(responseDTO), actualClients);
        searchRequestDTO= new SearchRequestDTO();
        searchRequestDTO.setMobileNumber("987654321");
        actualClients = clientServiceImpl.getClient(searchRequestDTO);
        Assertions.assertEquals(List.of(responseDTO), actualClients);
    }

    @Test()
    public void testGetClientThrowsExceptionForInvalidId() {
       Assertions.assertThrows(SearchParamNotFoundException.class,()-> clientServiceImpl.getClient(searchRequestDTO),"Search param should be either IdNumber or Mobilenumber or FirstName !!");
    }

    @Test()
    public void testGetClientThrowsExceptionForNonExistentId() {
        searchRequestDTO.setIdNumber("7806062507085");
        Assertions.assertThrows(RecordNotExistsException.class,()-> clientServiceImpl.getClient(searchRequestDTO),"Record not exists for the given Id!!!");
        searchRequestDTO= new SearchRequestDTO();
        searchRequestDTO.setMobileNumber("780606259");
        Assertions.assertThrows(RecordNotExistsException.class,()-> clientServiceImpl.getClient(searchRequestDTO),"Record not exists for the given mobile number!!!");
        searchRequestDTO= new SearchRequestDTO();
        searchRequestDTO.setFirstName("Fred");
        Assertions.assertThrows(RecordNotExistsException.class,()-> clientServiceImpl.getClient(searchRequestDTO),"Record not exists for the given First Name!!!");
    }
}
