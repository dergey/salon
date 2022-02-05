package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Client;
import com.sergey.zhuravlev.salon.domain.enumeration.Sex;
import com.sergey.zhuravlev.salon.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client create(String firstName, String lastName, String email, String phoneNumber, Sex sex) {
        Client client = new Client(null, firstName, lastName, email, phoneNumber, sex);
        log.debug("Request to create Client : {}", client);
        return clientRepository.save(client);
    }

    @Transactional
    public Client update(Long id, String firstName, String lastName, String email, String phoneNumber, Sex sex) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Client"));
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setEmail(email);
        client.setPhoneNumber(phoneNumber);
        client.setSex(sex);
        log.debug("Request to update Client : {}", client);
        return client;
    }

    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

   @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
