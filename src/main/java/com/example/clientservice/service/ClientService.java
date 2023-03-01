package com.example.clientservice.service;

import com.example.clientservice.model.ClientRequestDTO;
import com.example.clientservice.model.ClientResponseDTO;
import com.example.clientservice.model.ClientUpdateRequestDTO;
import com.example.clientservice.model.SearchRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ClientService {

    ClientResponseDTO saveClient(ClientRequestDTO clientRequestDTO) ;
    ClientResponseDTO updateClient(String id, ClientUpdateRequestDTO clientUpdateRequestDTO);
    List<ClientResponseDTO> getClient(SearchRequestDTO searchRequestDTO);
}
