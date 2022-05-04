package com.epam.esm.service.dto.order;

import org.springframework.hateoas.RepresentationModel;

public class OrderDTO extends RepresentationModel<OrderDTO> {

    private String cost;

    private String orderDate;

    public String getCost() {
        return cost;
    }

    public OrderDTO setCost(String cost) {
        this.cost = cost;
        return this;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public OrderDTO setOrderDate(String orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderDTO orderDTO = (OrderDTO) o;

        if (cost != null ? !cost.equals(orderDTO.cost) : orderDTO.cost != null) return false;
        return orderDate != null ? orderDate.equals(orderDTO.orderDate) : orderDTO.orderDate == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() + " {" +
                "cost='" + cost + '\'' +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}
