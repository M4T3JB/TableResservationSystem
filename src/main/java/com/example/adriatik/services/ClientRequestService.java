package com.example.adriatik.services;

import com.example.adriatik.dto.ClientRequestPayload;
import com.example.adriatik.entities.ClientRequest;

import com.example.adriatik.entities.User;
import java.util.List;


public interface ClientRequestService {

    ClientRequest createRequest(ClientRequest clientRequest);

    List<ClientRequest> findAll();

    List<ClientRequest> getClientRequestsByEmployee(User employee);
}
