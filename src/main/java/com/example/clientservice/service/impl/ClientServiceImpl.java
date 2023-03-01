package com.example.clientservice.service.impl;

import com.example.clientservice.exceptions.InvalidIdNumberException;
import com.example.clientservice.exceptions.RecordExistsException;
import com.example.clientservice.exceptions.RecordNotExistsException;
import com.example.clientservice.exceptions.SearchParamNotFoundException;
import com.example.clientservice.model.ClientRequestDTO;
import com.example.clientservice.model.ClientResponseDTO;
import com.example.clientservice.model.ClientUpdateRequestDTO;
import com.example.clientservice.model.SearchRequestDTO;
import com.example.clientservice.service.ClientService;
import com.example.clientservice.util.IDNumberValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    Map<UUID, ClientResponseDTO> clientStore = new HashMap<>();

    Map<Long, UUID> phoneNumberMap = new HashMap<>();
    Map<String, List<UUID>> firstNameMap = new HashMap<>();
    Map<Long, UUID> idMap = new HashMap<>();

    @Override
    public ClientResponseDTO saveClient(ClientRequestDTO clientRequestDTO) {
        if (!IDNumberValidator.validateIDNumber(clientRequestDTO.getIdNumber())) {
            throw new InvalidIdNumberException("Id number is invalid!!");
        }
        String validationResponse = recordExists(clientRequestDTO);
        if (!StringUtils.isBlank(validationResponse)) {
            throw new RecordExistsException(validationResponse);
        } else {
            UUID id = UUID.randomUUID();
            ObjectMapper objectMapper = new ObjectMapper();
            ClientResponseDTO clientResponseDTO = null;
            try {
                clientResponseDTO = objectMapper.readValue(objectMapper.writeValueAsString(clientRequestDTO), ClientResponseDTO.class);

                clientResponseDTO.setRefId(id);
                clientStore.put(id, clientResponseDTO);
                if (clientResponseDTO.getMobileNumber() != 0) {
                    phoneNumberMap.put(clientResponseDTO.getMobileNumber(), id);
                }
                List<UUID> uuidList = firstNameMap.get(clientResponseDTO.getFirstName().strip().toLowerCase());
                if (uuidList == null || uuidList.isEmpty()) {
                    uuidList = new ArrayList<>();
                    uuidList.add(id);
                } else {
                    uuidList.add(id);
                }
                firstNameMap.put(clientResponseDTO.getFirstName().strip().toLowerCase(), uuidList);
                idMap.put(clientResponseDTO.getIdNumber(), id);
                return clientResponseDTO;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String recordExists(ClientRequestDTO clientRequestDTO) {
        if (idMap.get(Long.parseLong(clientRequestDTO.getIdNumber())) != null) {
            return "Already exists a record with same IdNumber!!!";
        } else if (StringUtils.isNotBlank(clientRequestDTO.getMobileNumber()) && phoneNumberMap.get(Long.parseLong(clientRequestDTO.getMobileNumber())) != null) {
            return "Already exists a record with same mobile number!!!";
        } else {
            return "";
        }
    }

    private String recordExists(ClientRequestDTO clientRequestDTO, UUID uuid) {
        UUID idNumberUUID = StringUtils.isNotBlank(clientRequestDTO.getIdNumber()) ? idMap.get(Long.parseLong(clientRequestDTO.getIdNumber())) : null;
        UUID mobileNumberUUID = StringUtils.isNotBlank(clientRequestDTO.getMobileNumber()) ? phoneNumberMap.get(Long.parseLong(clientRequestDTO.getMobileNumber())) : null;
        if (idNumberUUID != null && !idNumberUUID.toString().equalsIgnoreCase(uuid.toString())) {
            return "Already exists a record with same IdNumber!!!";
        } else if (mobileNumberUUID != null && !mobileNumberUUID.toString().equalsIgnoreCase(uuid.toString())) {
            return "Already exists a record with same mobile number!!!";
        } else {
            return "";
        }
    }

    @Override
    public ClientResponseDTO updateClient(String recordId, ClientUpdateRequestDTO clientUpdateRequestDTO) {
        UUID id = null;
        try {
            id = UUID.fromString(recordId);
        } catch (IllegalArgumentException exception) {
            throw new SearchParamNotFoundException("Invalid client record Id!!!");
        }
        if (clientStore.get(id) == null) {
            throw new RecordNotExistsException("Record not exists for the given Id!!!");
        } else {

            ObjectMapper objectMapper = new ObjectMapper();
            ClientRequestDTO clientRequestDTO = null;
            try {
                clientRequestDTO = objectMapper.readValue(objectMapper.writeValueAsString(clientUpdateRequestDTO), ClientRequestDTO.class);
                String validationResponse = recordExists(clientRequestDTO, id);
                if (!StringUtils.isEmpty(validationResponse)) {
                    throw new RecordExistsException(validationResponse);
                } else {
                    ClientResponseDTO clientResponseDTO = clientStore.get(id);
                    String firstName = clientResponseDTO.getFirstName();
                    long mobileNumber = clientResponseDTO.getMobileNumber();
                    Long idNumber = clientResponseDTO.getIdNumber();
                    if (StringUtils.isNotBlank(clientUpdateRequestDTO.getFirstName()))
                        clientResponseDTO.setFirstName(clientUpdateRequestDTO.getFirstName());
                    if (StringUtils.isNotBlank(clientUpdateRequestDTO.getLastName()))
                        clientResponseDTO.setLastName(clientUpdateRequestDTO.getLastName());
                    if (StringUtils.isNotBlank(clientUpdateRequestDTO.getMobileNumber()))
                        clientResponseDTO.setMobileNumber(Long.parseLong(clientUpdateRequestDTO.getMobileNumber()));
                    if (StringUtils.isNotBlank(clientUpdateRequestDTO.getIdNumber()))
                        clientResponseDTO.setIdNumber(Long.parseLong(clientUpdateRequestDTO.getIdNumber()));
                    clientStore.put(id, clientResponseDTO);
                    phoneNumberMap.remove(mobileNumber);
                    phoneNumberMap.put(clientResponseDTO.getMobileNumber(), id);

                    List<UUID> uuidList = firstNameMap.get(firstName.strip().toLowerCase());
                    if (uuidList.size() == 1) {
                        firstNameMap.remove(firstName.strip().toLowerCase());
                    } else {
                        uuidList.remove(id);
                        firstNameMap.put(firstName.strip().toLowerCase(), uuidList);
                    }
                    uuidList = firstNameMap.get(clientResponseDTO.getFirstName().strip().toLowerCase());
                    if (uuidList == null || uuidList.isEmpty()) {
                        uuidList = List.of(id);
                    } else {
                        uuidList.add(id);
                    }
                    firstNameMap.put(clientResponseDTO.getFirstName().strip().toLowerCase(), uuidList);
                    idMap.remove(idNumber);
                    idMap.put(clientResponseDTO.getIdNumber(), id);
                    return clientResponseDTO;
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<ClientResponseDTO> getClient(SearchRequestDTO searchRequestDTO) {
        if (StringUtils.isNotBlank(searchRequestDTO.getIdNumber())) {
            UUID searchId = idMap.get(Long.parseLong(searchRequestDTO.getIdNumber()));
            if (searchId == null) {
                throw new RecordNotExistsException("Record not exists for the given Id!!!");
            } else {
                return List.of(clientStore.get(searchId));
            }
        }
        if (StringUtils.isNotBlank(searchRequestDTO.getMobileNumber())) {
            UUID searchId = phoneNumberMap.get(Long.parseLong(searchRequestDTO.getMobileNumber()));
            if (searchId == null) {
                throw new RecordNotExistsException("Record not exists for the given mobile number!!!");
            } else {
                return List.of(clientStore.get(searchId));
            }
        }
        if (!StringUtils.isBlank(searchRequestDTO.getFirstName())) {
            List<UUID> searchIds = firstNameMap.get(searchRequestDTO.getFirstName().strip().toLowerCase());
            if (searchIds == null || searchIds.isEmpty()) {
                throw new RecordNotExistsException("Record not exists for the given First Name!!!");
            } else {
                return searchIds.stream().map(id -> clientStore.get(id)).collect(Collectors.toList());
            }
        }

        throw new SearchParamNotFoundException("Search param should be either IdNumber or Mobilenumber or FirstName !!");

    }
}
