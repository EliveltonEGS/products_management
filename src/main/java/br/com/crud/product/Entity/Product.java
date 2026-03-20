package br.com.crud.product.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    protected Product() {
    }

    public Product(String name, Integer amount, BigDecimal price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false, length = 50)
    public String name;

    @Min(value = 1, message = "The amount must not be less than zero.")
    public Integer amount;

    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal price;
}
