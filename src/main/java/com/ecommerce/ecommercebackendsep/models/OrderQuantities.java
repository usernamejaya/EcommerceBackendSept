package com.ecommerce.ecommercebackendsep.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderQuantities extends BaseModel{
    @ManyToOne
    private Product product;
    private int quantity;
    @ManyToOne
    @JsonIgnore
    private Order order;

}