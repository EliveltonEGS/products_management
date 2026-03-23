package br.com.crud.product.service;

import java.util.List;
import java.util.UUID;

import br.com.crud.category.repository.CategoryRepository;
import br.com.crud.entities.Product;
import br.com.crud.exception.ItemNotFoundException;
import br.com.crud.product.repository.ProductyRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductService {

    private final ProductyRepository productyRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(
        ProductyRepository productyRepository,
        CategoryRepository categoryRepository
    ) {
        this.productyRepository = productyRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product create(Product product) {
        var category = categoryRepository.findById(product.category.id);

        product.category = category;

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
