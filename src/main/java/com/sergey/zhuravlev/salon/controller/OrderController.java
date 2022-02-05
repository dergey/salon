package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.Client;
import com.sergey.zhuravlev.salon.domain.Order;
import com.sergey.zhuravlev.salon.dto.OrderDto;
import com.sergey.zhuravlev.salon.dto.OrderRequestDto;
import com.sergey.zhuravlev.salon.exception.BadRequestAlertException;
import com.sergey.zhuravlev.salon.mapper.OrderMapper;
import com.sergey.zhuravlev.salon.service.ClientService;
import com.sergey.zhuravlev.salon.service.OrderService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final String ENTITY_NAME = "order";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderService orderService;
    private final ClientService clientService;

    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequestDto dto) throws URISyntaxException {
        log.debug("REST request to save Order : {}", dto);
        Client client = clientService.findOne(dto.getClientId())
            .orElseThrow(() -> new BadRequestAlertException("Client with given ID does not exist", "client", "entitynotexist"));
        Order result = orderService.create(dto.getDate(), client);
        return ResponseEntity.created(new URI("/api/orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(orderMapper.orderToOrderDto(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id,
                                             @RequestBody OrderRequestDto dto) throws URISyntaxException {
        log.debug("REST request to update Order : {}", dto);
        Client client = clientService.findOne(dto.getClientId())
            .orElseThrow(() -> new BadRequestAlertException("Client with given ID does not exist", "client", "entitynotexist"));
        Order result = orderService.update(id, dto.getDate(), client);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(orderMapper.orderToOrderDto(result));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(Pageable pageable) {
        log.debug("REST request to get a page of Orders");
        Page<Order> page = orderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.map(orderMapper::orderToOrderDto).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
        log.debug("REST request to get Order : {}", id);
        Optional<Order> order = orderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(order.map(orderMapper::orderToOrderDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.debug("REST request to delete Order : {}", id);
        orderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
