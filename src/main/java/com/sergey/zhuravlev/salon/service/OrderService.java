package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Client;
import com.sergey.zhuravlev.salon.domain.Order;
import com.sergey.zhuravlev.salon.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order create(Instant date, Client client) {
        Order order = new Order(null, date, client);
        log.debug("Request to save Order : {}", order);
        return orderRepository.save(order);
    }

    @Transactional
    public Order update(Long id, Instant date, Client client) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Order"));
        order.setDate(date);
        order.setClient(client);
        log.debug("Request to save Order : {}", order);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Page<Order> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Order> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }
}
