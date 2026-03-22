package br.com.crud.category.controller;

import java.util.Map;
import java.util.UUID;

import br.com.crud.category.service.CategoryService;
import br.com.crud.entities.Category;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @POST
    @Transactional
    public Response create(@Valid Category product) {
        return Response
                .status(Response.Status.CREATED)
                .entity(Map.of(
                        "data", categoryService.create(product),
                        "message", "created"))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        return Response.ok(categoryService.findById(id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") UUID id, @Valid Category product) {
        return Response.ok(Map.of(
                "data", categoryService.update(id, product),
                "message", "Updated.")).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        return Response.ok(Map.of(
                "data", categoryService.findAll(page, pageSize),
                "total", categoryService.total())).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") UUID id) {
        categoryService.delete(id);
        return Response.noContent().build();
    }
}
