package com.ecommerce.ecommercebackendsep.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Inventory extends BaseModel {
    @OneToOne(mappedBy = "inventory")
    @JsonIgnore
    private Product product;
    private int InStockQuantity;
}

