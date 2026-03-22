package br.com.crud.product.service;

import java.util.List;
import java.util.UUID;

import br.com.crud.entities.Product;
import br.com.crud.exception.ItemNotFoundException;
import br.com.crud.product.repository.ProductyRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

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
        Product currentProduct = this.findById(id);
        currentProduct.name = product.name;
        currentProduct.amount = product.amount;
        currentProduct.price = product.price;

        productyRepository.persist(currentProduct);

        return currentProduct;
    }

    public Product findById(UUID id) {
        return productyRepository.findByIdOptional(id).orElseThrow(ItemNotFoundException::new);
    }

    public Long total() {
        return productyRepository.count();
    }

    public void delete(UUID id) {
        this.findById(id);

        productyRepository.deleteById(id);
    }
}
