package br.com.crud.product.repository;

import java.util.UUID;

import br.com.crud.product.Entity.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductyRepository implements PanacheRepositoryBase<Product, UUID> {
}
