package com.sergey.zhuravlev.salon.service.impl;

import com.sergey.zhuravlev.salon.service.OrderService;
import com.sergey.zhuravlev.salon.domain.Order;
import com.sergey.zhuravlev.salon.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Save a order.
     *
     * @param order the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Order save(Order order) {
        log.debug("Request to save Order : {}", order);
        return orderRepository.save(order);
    }

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAll(pageable);
    }


    /**
     * Get one order by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id);
    }

    /**
     * Delete the order by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }
}
