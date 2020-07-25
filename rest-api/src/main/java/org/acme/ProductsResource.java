package org.acme;

import org.acme.domain.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Path("/products")
public class ProductsResource {

    private List<Product> products = Arrays.asList(
            new Product("product-1", "notebook"),
            new Product("product-2", "mouse"));

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() {
        return Response.status(Status.OK).entity(this.products).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) {
        this.products.add(product);
        return Response.status(Status.OK).entity(product).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("{id}") String id, Product product) {
        Optional<Product> productToUpdate = findProductById(id);
        if (productToUpdate.isPresent()) {
            products.remove(productToUpdate.get());
            products.add(product);
            return Response.status(Status.OK).entity(product).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response updateProduct(@PathParam("{id}") String id) {
        Optional<Product> productToRemove = findProductById(id);
        if (productToRemove.isPresent()) {
            products.remove(productToRemove.get());
            return Response.status(Status.OK).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    private Optional<Product> findProductById(String id) {
        return this.products.stream()
                .filter(product -> id.equals(product.getId()))
                .findFirst();
    }
}