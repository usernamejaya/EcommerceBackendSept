package com.ecommerce.ecommercebackendsep.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Address extends BaseModel{
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonBackReference   // Prevent recursion by ignoring this field during serialization
    private List<Order> orders;
    private String city;
    private String street;
    private String postalCode;
    private String country;
}
