package com.epam.esm.service.utils;

import com.epam.esm.persistence.repository.entity.CertificateEntity;
import com.epam.esm.persistence.repository.entity.OrderEntity;
import com.epam.esm.persistence.repository.entity.UserEntity;
import com.epam.esm.service.dto.order.OrderDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class OrderServiceTestUtils {

    public static List<OrderEntity> getOrders() {
        return Arrays.asList(
                new OrderEntity().setId(1).setCost(20.5).setOrderDate(LocalDateTime.parse("2020-01-26T10:00:00"))
                        .setCertificate(new CertificateEntity().setId(1)).setUser(new UserEntity().setId(1)),
                new OrderEntity().setId(2).setCost(30.5).setOrderDate(LocalDateTime.parse("2020-02-24T12:25:00"))
                        .setCertificate(new CertificateEntity().setId(2)).setUser(new UserEntity().setId(1)),
                new OrderEntity().setId(3).setCost(10).setOrderDate(LocalDateTime.parse("2020-02-24T12:30:00"))
                        .setCertificate(new CertificateEntity().setId(3)).setUser(new UserEntity().setId(2)),
                new OrderEntity().setId(4).setCost(10.8).setOrderDate(LocalDateTime.parse("2020-01-24T12:30:00"))
                        .setCertificate(new CertificateEntity().setId(4)).setUser(new UserEntity().setId(2)),
                new OrderEntity().setId(5).setCost(10).setOrderDate(LocalDateTime.parse("2020-02-24T12:30:00"))
                        .setCertificate(new CertificateEntity().setId(5)).setUser(new UserEntity().setId(2)),
                new OrderEntity().setId(6).setCost(12.99).setOrderDate(LocalDateTime.parse("2021-02-24T12:30:00"))
                        .setCertificate(new CertificateEntity().setId(6)).setUser(new UserEntity().setId(3))
        );
    }

    public static List<OrderDTO> getOrderDTOs() {
        return Arrays.asList(
                new OrderDTO().setOrderDate("2020-01-26T10:00:00").setCost("20.50"),
                new OrderDTO().setOrderDate("2020-02-24T12:25:00").setCost("30.50"),
                new OrderDTO().setOrderDate("2020-02-24T12:30:00").setCost("10.00"),
                new OrderDTO().setOrderDate("2020-01-24T12:30:00").setCost("10.80"),
                new OrderDTO().setOrderDate("2020-02-24T12:30:00").setCost("10.00"),
                new OrderDTO().setOrderDate("2021-02-24T12:30:00").setCost("12.99")
        );
    }

    public static OrderEntity getCreateOrder(CertificateEntity certificate, UserEntity user) {
        return new OrderEntity().setId(7).setCost(15.5).setOrderDate(LocalDateTime.parse("2021-01-26T10:00:00"))
                .setCertificate(certificate).setUser(user);
    }

    public static OrderDTO getCreateOrderDTO() {
        return new OrderDTO().setCost("15.50").setOrderDate("2021-01-26T10:00:00");
    }
}

