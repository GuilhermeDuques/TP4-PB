package app.product;

import java.math.BigDecimal;

public class Product {
    private final Long id;
    private final String name;
    private final BigDecimal price;
    private final int quantity;
    private final Long categoryId;

    public Product(Long id, String name, BigDecimal price, int quantity, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
    }

    public Product(String name, BigDecimal price, int quantity, Long categoryId) {
        this(null, name, price, quantity, categoryId);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Product withId(Long id) {
        return new Product(id, this.name, this.price, this.quantity, this.categoryId);
    }

    public Product withData(String name, BigDecimal price, int quantity, Long categoryId) {
        return new Product(this.id, name, price, quantity, categoryId);
    }
}
