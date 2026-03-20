package br.com.crud.product.controller;

import java.util.Map;

import br.com.crud.product.Entity.Product;
import br.com.crud.product.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @POST
    @Transactional
    public Response create(@Valid Product product) {
        return Response
            .status(Response.Status.CREATED)
            .entity(Map.of(
                    "data", productService.create(product),
                    "message", "created"
            )).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        return Response.ok(productService.findById(id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") UUID id, Product product) {
        return Response.ok(Map.of(
        "data", productService.update(id, product),
        "Message", "Updated."
        )).build();
        
    }

    @GET
    public Response findAll(
        @QueryParam("page") @DefaultValue("0") Integer page,
        @QueryParam("pageSize") @DefaultValue("10") Integer pageSize
    ) {
        return Response.ok(Map.of(
            "data", productService.findAll(page, pageSize),
                "total", productService.totalProduct()
        )).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") UUID id) {
        productService.delete(id);
        return Response.noContent().build();
    }
}
