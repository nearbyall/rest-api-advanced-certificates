package com.epam.esm.service.mapper;

import com.epam.esm.persistence.repository.entity.OrderEntity;
import com.epam.esm.service.dto.order.OrderDTO;
import org.springframework.stereotype.Component;

import static com.epam.esm.service.mapper.СertificateMapper.DATE_FORMAT;
import static com.epam.esm.service.mapper.СertificateMapper.NUMBER_FORMATTER;

@Component
public class OrderMapper {

    public OrderDTO toOrderDTO(OrderEntity order) {
        return new OrderDTO()
                .setCost(NUMBER_FORMATTER.format(order.getCost()))
                .setOrderDate(DATE_FORMAT.format(order.getOrderDate()));
    }
}
