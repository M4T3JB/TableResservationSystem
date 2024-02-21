package com.example.adriatik.services;

import com.example.adriatik.entities.ClientRequest;
import com.example.adriatik.entities.User;
import com.example.adriatik.repositories.ClientRequestRepository;
import com.example.adriatik.repositories.RoleRepository;
import com.example.adriatik.repositories.UserRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Getter
public class ClientRequestServiceImpl implements ClientRequestService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final ClientRequestRepository clientRequestRepository;

    public ClientRequestServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ClientRequestRepository clientRequestRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.clientRequestRepository = clientRequestRepository;
    }

    @Override
    public ClientRequest createRequest(ClientRequest clientRequest) {
        return clientRequestRepository.save(clientRequest);
    }

    @Override
    public List<ClientRequest> findAll() {
        return clientRequestRepository.findAll();
    }

    @Override
    public List<ClientRequest> getClientRequestsByEmployee(User employee) {
        return clientRequestRepository.findByEmployee(employee);
    }

}
