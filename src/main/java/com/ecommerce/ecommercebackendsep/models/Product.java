package com.ecommerce.ecommercebackendsep.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel{
    private String name;
    private String description;
    private double price;
    @OneToOne(cascade = CascadeType.ALL ,optional = false ,orphanRemoval = true)
    private Inventory inventory;

}
