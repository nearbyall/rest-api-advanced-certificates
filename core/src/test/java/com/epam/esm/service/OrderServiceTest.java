package com.epam.esm.service;

import com.epam.esm.persistence.repository.CertificateRepository;
import com.epam.esm.persistence.repository.OrderRepository;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.persistence.repository.entity.OrderEntity;
import com.epam.esm.service.dto.ObjectListDTO;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.order.OrderDTO;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.mapper.OrderMapper;
import com.epam.esm.service.pagination.Paginator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.epam.esm.service.utils.OrderServiceTestUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
/*
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CertificateRepository certificateRepository;
    @Spy
    private OrderMapper orderMapper;
    @Spy
    private Paginator paginator;

    private OrderService orderService;

    private static final List<OrderEntity> testOrders = getOrders();
    private static final List<OrderDTO> testOrderDTOs = getOrderDTOs();

    @BeforeEach
    public void setUp() {
        orderService = new OrderServiceImpl
                (orderRepository, userRepository, certificateRepository, orderMapper, paginator);
    }

    @Test
    public void testGetAll_shouldDelegateGettingToRepositoryIfOrdersExist() {
        PageDTO expectedPage = new PageDTO().setSize(3).setTotalElements(6).setTotalPages(2).setNumber(1);

        when(orderRepository.getAll(3, 0)).thenReturn(testOrders.subList(0, 3));
        when(orderRepository.getCount()).thenReturn(6);

        ObjectListDTO<OrderDTO> orders = orderService.getAll(1, 3);

        IntStream.range(0, 3).forEach(i -> verify(orderMapper).toOrderDTO(testOrders.get(i)));
        verify(orderRepository).getAll(3, 0);
        verify(orderRepository).getCount();
        verify(paginator).getPage(3, 6, 1);

        verifyNoMoreInteractions(orderRepository);
        verifyNoMoreInteractions(orderMapper);

        assertThat(orders.getObjects().size(), is(3));
        assertThat(orders.getObjects(), is(equalTo(testOrderDTOs.subList(0, 3))));
        assertThat(orders.getPage(), is(equalTo(expectedPage)));
    }

    @Test
    public void testGetById_shouldDelegateGettingToRepositoryIfOrderFoundById() {
        int orderId = 1;
        OrderDTO expectedOrder = testOrderDTOs.get(0);

        when(orderRepository.getById(orderId)).thenReturn(Optional.of(testOrders.get(0)));

        Optional<OrderDTO> actualOrderDTO = orderService.getById(orderId);

        verify(orderMapper).toOrderDTO(testOrders.get(0));
        verify(orderRepository).getById(orderId);

        verifyNoMoreInteractions(orderMapper);
        verifyNoMoreInteractions(orderRepository);

        assertTrue(actualOrderDTO.isPresent());
        assertThat(actualOrderDTO.get(), is(equalTo(expectedOrder)));
    }

    @Test
    public void testGetByUserId_shouldDelegateGettingToRepositoryIfOrderFoundByUserId() {
        int userId = 2;

        List<OrderEntity> userOrders = testOrders.subList(2, 5);
        List<OrderDTO> expectedOrders = testOrderDTOs.subList(2, 5);
        PageDTO expectedPage = new PageDTO().setSize(3).setTotalElements(3).setTotalPages(1).setNumber(1);

        when(orderRepository.getByUserId(userId, 3, 0)).thenReturn(userOrders);
        when(orderRepository.getCountByUserId(userId)).thenReturn(3);

        ObjectListDTO<OrderDTO> orders = orderService.getByUserId(userId, 1, 3);

        IntStream.range(0, 3).forEach(i -> verify(orderMapper).toOrderDTO(userOrders.get(i)));
        verify(orderRepository).getByUserId(userId, 3, 0);
        verify(orderRepository).getCountByUserId(userId);
        verify(paginator).getPage(3, 3, 1);

        verifyNoMoreInteractions(orderRepository);
        verifyNoMoreInteractions(orderMapper);

        assertThat(orders.getObjects().size(), is(3));
        assertThat(orders.getObjects(), is(equalTo(expectedOrders)));
        assertThat(orders.getPage(), is(equalTo(expectedPage)));
    }

    @Test
    public void testGetByUserIdAndOrderId_shouldDelegateGettingToRepositoryIfOrderFoundByOrderIdAndUserId() {
        int userId = 1;
        int orderId = 1;
        OrderDTO expectedOrder = testOrderDTOs.get(0);

        when(orderRepository.getByUserIdAndOrderId(userId, orderId)).thenReturn(Optional.of(testOrders.get(0)));

        Optional<OrderDTO> orders = orderService.getByUserIdAndOrderId(userId, orderId);

        verify(orderMapper).toOrderDTO(testOrders.get(0));
        verify(orderRepository).getByUserIdAndOrderId(userId, orderId);

        verifyNoMoreInteractions(orderMapper);
        verifyNoMoreInteractions(orderRepository);

        assertTrue(orders.isPresent());
        assertThat(orders.get(), is(equalTo(expectedOrder)));
    }

}
*/