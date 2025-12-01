package app.product;

import app.category.CategoryService;

import java.math.BigDecimal;
import java.util.List;

public class ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository repository, CategoryService categoryService) {
        this.repository = repository;
        this.categoryService = categoryService;
    }

    public Product create(String name, BigDecimal price, int quantity, Long categoryId) {
        validateName(name);
        validatePrice(price);
        validateQuantity(quantity);
        validateCategoryId(categoryId);

        // Integração: garante que a categoria existe antes de criar o produto
        categoryService.get(categoryId);

        Product product = new Product(name.trim(), price, quantity, categoryId);
        return repository.save(product);
    }

    public Product get(Long id) {
        validateId(id);
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
    }

    public List<Product> listAll() {
        return repository.findAll();
    }

    public Product update(Long id, String name, BigDecimal price, int quantity, Long categoryId) {
        validateId(id);
        validateName(name);
        validatePrice(price);
        validateQuantity(quantity);
        validateCategoryId(categoryId);

        categoryService.get(categoryId);

        Product existing = get(id);
        existing.setName(name.trim());
        existing.setPrice(price);
        existing.setQuantity(quantity);
        existing.setCategoryId(categoryId);
        return repository.save(existing);
    }

    public void delete(Long id) {
        validateId(id);
        boolean removed = repository.delete(id);
        if (!removed) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
    }

    private void validateId(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("Id inválido");
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido");
        }
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço inválido");
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }
    }

    private void validateCategoryId(Long categoryId) {
        if (categoryId == null || categoryId < 1) {
            throw new IllegalArgumentException("Categoria inválida");
        }
    }
}
