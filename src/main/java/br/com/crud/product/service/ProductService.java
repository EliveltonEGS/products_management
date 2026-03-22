package br.com.crud.product.service;

import java.util.List;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import br.com.crud.product.Entity.Product;
import br.com.crud.product.exception.ProductNotFoundException;
import br.com.crud.product.repository.ProductyRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ProductService {

    private final ProductyRepository productyRepository;

    public ProductService(ProductyRepository productyRepository) {
        this.productyRepository = productyRepository;
    }

    public Product create(Product product) {
        productyRepository.persist(product);
        return product;
    }

    public List<Product> findAll(Integer page, Integer pageSize) {
        return productyRepository
                .findAll(Sort.by("name").and("price", Sort.Direction.Descending))
                .page(page, pageSize)
                .list();
    }

    public Product update(UUID id, Product product) {
        var currentProduct = findById(id);
        currentProduct.name = product.name;
        currentProduct.amount = product.amount;
        currentProduct.price = product.price;

        productyRepository.persist(currentProduct);

        return currentProduct;
    }

    public Product findById(UUID id) {
        return productyRepository.findByIdOptional(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    public Long totalProduct() {
        return productyRepository.count();
    }

    public void delete(UUID id) {
        findById(id);

        productyRepository.deleteById(id);
    }
}
