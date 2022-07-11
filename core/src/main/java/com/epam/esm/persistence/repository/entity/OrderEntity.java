package com.epam.esm.persistence.repository.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cost")
    private double cost;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @OneToOne
    @MapsId
    @JoinColumn(name = "certificate_id")
    private CertificateEntity certificate;

    @ManyToOne
    private UserEntity user;

    @PrePersist
    public void onPrePersist() {
        orderDate = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public OrderEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public double getCost() {
        return cost;
    }

    public OrderEntity setCost(double cost) {
        this.cost = cost;
        return this;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public OrderEntity setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public CertificateEntity getCertificate() {
        return certificate;
    }

    public OrderEntity setCertificate(CertificateEntity certificate) {
        this.certificate = certificate;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public OrderEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (Double.compare(that.cost, cost) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (orderDate != null ? !orderDate.equals(that.orderDate) : that.orderDate != null) return false;
        if (certificate != null ? !certificate.equals(that.certificate) : that.certificate != null) return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (certificate != null ? certificate.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() +
                "{" +
                "id=" + id +
                ", cost=" + cost +
                ", orderDate=" + orderDate +
                ", certificate=" + certificate +
                ", user=" + user +
                '}';
    }

}
