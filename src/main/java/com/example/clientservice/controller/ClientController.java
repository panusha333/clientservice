package com.example.clientservice.controller;

import com.example.clientservice.model.ClientRequestDTO;
import com.example.clientservice.model.ClientResponseDTO;
import com.example.clientservice.model.ClientUpdateRequestDTO;
import com.example.clientservice.model.SearchRequestDTO;
import com.example.clientservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @GetMapping("/")
    public ResponseEntity<List<ClientResponseDTO>> getClient(@RequestBody @Valid SearchRequestDTO searchRequestDTO)
    {
        List<ClientResponseDTO> clientResponseDTOS = clientService.getClient(searchRequestDTO);
        return new ResponseEntity<>(clientResponseDTOS,HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ClientResponseDTO> createClient( @RequestBody @Valid ClientRequestDTO clientRequestDTO)
    {
        ClientResponseDTO clientResponseDTO = clientService.saveClient(clientRequestDTO);
        return new ResponseEntity<>(clientResponseDTO,HttpStatus.OK);
    }

    @PutMapping("/update/{refId}")
    public ResponseEntity<ClientResponseDTO> updateClient(@RequestBody @Valid ClientUpdateRequestDTO clientUpdateRequestDTO, @PathVariable String refId)
    {
        ClientResponseDTO clientResponseDTO = clientService.updateClient(refId, clientUpdateRequestDTO);
        return new ResponseEntity<>(clientResponseDTO,HttpStatus.OK);
    }
}
