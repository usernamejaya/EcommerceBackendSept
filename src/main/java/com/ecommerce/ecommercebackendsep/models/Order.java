package com.ecommerce.ecommercebackendsep.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity(name = "orders")
public class Order extends BaseModel{
    @ManyToOne
    @JsonManagedReference
    private User user;
    @ManyToOne
    @JsonManagedReference
    private Address address;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderQuantities> orderQuantities;
    private LocalDate orderDate;

}
