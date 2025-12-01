package app.web;

import app.category.CategoryRepository;
import app.category.CategoryService;
import app.product.ProductRepository;
import app.product.ProductService;
import io.javalin.Javalin;

import java.math.BigDecimal;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        CategoryRepository categoryRepository = new CategoryRepository();
        CategoryService categoryService = new CategoryService(categoryRepository);

        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository, categoryService);

        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        }).start(7000);

        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        });

        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500).json(Map.of("error", "Erro inesperado"));
        });

        app.get("/categories", ctx -> ctx.json(categoryService.listAll()));

        app.post("/categories", ctx -> {
            String name = ctx.formParam("name");
            var created = categoryService.create(name);
            ctx.status(201).json(created);
        });

        app.put("/categories/{id}", ctx -> {
            Long id = Long.valueOf(ctx.pathParam("id"));
            String name = ctx.formParam("name");
            var updated = categoryService.update(id, name);
            ctx.json(updated);
        });

        app.delete("/categories/{id}", ctx -> {
            Long id = Long.valueOf(ctx.pathParam("id"));
            categoryService.delete(id);
            ctx.status(204);
        });

        app.get("/products", ctx -> ctx.json(productService.listAll()));

        app.post("/products", ctx -> {
            String name = ctx.formParam("name");
            String priceStr = ctx.formParam("price");
            String quantityStr = ctx.formParam("quantity");
            String categoryIdStr = ctx.formParam("categoryId");

            BigDecimal price = new BigDecimal(priceStr);
            int quantity = Integer.parseInt(quantityStr);
            Long categoryId = Long.valueOf(categoryIdStr);

            var created = productService.create(name, price, quantity, categoryId);
            ctx.status(201).json(created);
        });

        app.put("/products/{id}", ctx -> {
            Long id = Long.valueOf(ctx.pathParam("id"));
            String name = ctx.formParam("name");
            String priceStr = ctx.formParam("price");
            String quantityStr = ctx.formParam("quantity");
            String categoryIdStr = ctx.formParam("categoryId");

            BigDecimal price = new BigDecimal(priceStr);
            int quantity = Integer.parseInt(quantityStr);
            Long categoryId = Long.valueOf(categoryIdStr);

            var updated = productService.update(id, name, price, quantity, categoryId);
            ctx.json(updated);
        });

        app.delete("/products/{id}", ctx -> {
            Long id = Long.valueOf(ctx.pathParam("id"));
            productService.delete(id);
            ctx.status(204);
        });
    }
}
