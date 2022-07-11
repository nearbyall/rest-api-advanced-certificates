package com.epam.esm.service.impl;

import com.epam.esm.persistence.repository.CertificateRepository;
import com.epam.esm.persistence.repository.OrderRepository;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.persistence.repository.entity.CertificateEntity;
import com.epam.esm.persistence.repository.entity.OrderEntity;
import com.epam.esm.persistence.repository.entity.UserEntity;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.aspect.Loggable;
import com.epam.esm.service.dto.ObjectListDTO;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.order.OrderDTO;
import com.epam.esm.service.mapper.OrderMapper;
import com.epam.esm.service.pagination.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CertificateRepository certificateRepository;

    private final OrderMapper mapper;
    private final Paginator paginator;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            CertificateRepository certificateRepository,
                            OrderMapper mapper,
                            Paginator paginator) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
        this.mapper = mapper;
        this.paginator = paginator;
    }

    @Override
    public ObjectListDTO<OrderDTO> getAll(int page, int size) {
        List<OrderEntity> orders = orderRepository.getAll(size, paginator.getOffset(page, size));
        long count = orderRepository.getCount();

        return getOrderObjectListDTO(orders, paginator.getPage(size, (int)count, page));
    }

    @Override
    public Optional<OrderDTO> getById(Integer id) {
        Optional<OrderEntity> getOrder = orderRepository.getById(id);

        return getOrder.map(mapper::toOrderDTO);
    }

    @Override
    @Loggable
    @Transactional
    public Optional<OrderDTO> create(Integer userId, Integer certificateId) {
        boolean isUserExist = userRepository.getById(userId).isPresent();
        Optional<CertificateEntity> certificate = certificateRepository.getById(certificateId);
        boolean isCertificateExist = certificate.isPresent();
        boolean isOrderExist = orderRepository.getOrderByCertificateId(certificateId).isPresent();

        if (!isUserExist || !isCertificateExist || isOrderExist) {
            return Optional.empty();
        }

        OrderEntity order = new OrderEntity()
                .setCost(certificate.get().getPrice())
                .setCertificate(certificate.get())
                .setUser(new UserEntity().setId(userId));

        Optional<Integer> id = orderRepository.create(order);

        return id.isPresent() && id.get() > 0
                ? getById(id.get())
                : Optional.empty();
    }

    @Override
    public ObjectListDTO<OrderDTO> getByUserId(Integer id, int page, int size) {
        List<OrderEntity> orders = orderRepository.getByUserId(id, size, paginator.getOffset(page, size));
        long count = orderRepository.getCountByUserId(id);

        return getOrderObjectListDTO(orders, paginator.getPage(size, (int)count, page));
    }

    @Override
    public Optional<OrderDTO> getByUserIdAndOrderId(Integer userId, Integer orderId) {
        Optional<OrderEntity> getOrder = orderRepository.getByUserIdAndOrderId(userId, orderId);

        return getOrder.map(mapper::toOrderDTO);
    }

    @Override
    public Optional<OrderDTO> delete(Integer id) {
        Optional<OrderDTO> deleteOrder = getById(id);

        if (!deleteOrder.isPresent()) {
            return Optional.empty();
        }

        int result = orderRepository.delete(id);

        return result == 1
                ? deleteOrder
                : Optional.empty();
    }

    private List<OrderDTO> getOrderDTOs(List<OrderEntity> orders) {
        return orders
                .stream()
                .map(mapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    private ObjectListDTO<OrderDTO> getOrderObjectListDTO(List<OrderEntity> orders, PageDTO pageDTO) {
        ObjectListDTO<OrderDTO> objectListDTO = new ObjectListDTO<>();
        objectListDTO.setObjects(getOrderDTOs(orders));
        objectListDTO.setPage(pageDTO);

        return objectListDTO;
    }

}
