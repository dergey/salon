package com.sergey.zhuravlev.salon.mapper;

import com.sergey.zhuravlev.salon.domain.Order;
import com.sergey.zhuravlev.salon.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto orderToOrderDto(Order order);
}
