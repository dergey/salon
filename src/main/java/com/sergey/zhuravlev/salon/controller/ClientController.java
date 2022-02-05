package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.Client;
import com.sergey.zhuravlev.salon.dto.ClientDto;
import com.sergey.zhuravlev.salon.dto.ClientRequestDto;
import com.sergey.zhuravlev.salon.mapper.ClientMapper;
import com.sergey.zhuravlev.salon.service.ClientService;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private static final String ENTITY_NAME = "client";

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientRequestDto dto) throws URISyntaxException {
        Client result = clientService.create(dto.getFirstName(),
            dto.getLastName(),
            dto.getEmail(),
            dto.getPhoneNumber(),
            dto.getSex());
        return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(clientMapper.clientToClientDto(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id, @Valid @RequestBody ClientRequestDto dto) {
        Client result = clientService.update(id,
            dto.getFirstName(),
            dto.getLastName(),
            dto.getEmail(),
            dto.getPhoneNumber(),
            dto.getSex());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .body(clientMapper.clientToClientDto(result));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients(Pageable pageable) {
        Page<Client> page = clientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.map(clientMapper::clientToClientDto).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Long id) {
        Optional<Client> client = clientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(client.map(clientMapper::clientToClientDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
