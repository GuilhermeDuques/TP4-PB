package app.product;

import app.category.Category;
import app.category.CategoryRepository;
import app.category.CategoryService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductService createServiceWithOneCategory(Category[] outCategory) {
        CategoryRepository categoryRepository = new CategoryRepository();
        CategoryService categoryService = new CategoryService(categoryRepository);
        Category created = categoryService.create("Tecnologia");
        if (outCategory != null && outCategory.length > 0) {
            outCategory[0] = created;
        }
        ProductRepository productRepository = new ProductRepository();
        return new ProductService(productRepository, categoryService);
    }

    @Test
    void deveCriarProdutoComCategoriaExistente() {
        Category[] holder = new Category[1];
        ProductService service = createServiceWithOneCategory(holder);

        Product product = service.create(
                "Notebook",
                new BigDecimal("3500.00"),
                10,
                holder[0].getId()
        );

        assertNotNull(product.getId());
        assertEquals(holder[0].getId(), product.getCategoryId());
    }

    @Test
    void naoDeveCriarProdutoComCategoriaInexistente() {
        CategoryRepository categoryRepository = new CategoryRepository();
        CategoryService categoryService = new CategoryService(categoryRepository);
        ProductRepository productRepository = new ProductRepository();
        ProductService service = new ProductService(productRepository, categoryService);

        assertThrows(IllegalArgumentException.class, () ->
                service.create("Notebook", new BigDecimal("1000.00"), 5, 999L));
    }

    @Test
    void naoDeveCriarProdutoComDadosInvalidos() {
        Category[] holder = new Category[1];
        ProductService service = createServiceWithOneCategory(holder);
        Long categoryId = holder[0].getId();

        assertThrows(IllegalArgumentException.class, () ->
                service.create("", new BigDecimal("1000.00"), 5, categoryId));

        assertThrows(IllegalArgumentException.class, () ->
                service.create("Notebook", new BigDecimal("-1.00"), 5, categoryId));

        assertThrows(IllegalArgumentException.class, () ->
                service.create("Notebook", new BigDecimal("1000.00"), -1, categoryId));
    }

    @Test
    void deveAtualizarProdutoMantendoIntegridadeCategoria() {
        CategoryRepository categoryRepository = new CategoryRepository();
        CategoryService categoryService = new CategoryService(categoryRepository);
        Category cat1 = categoryService.create("Tecnologia");
        Category cat2 = categoryService.create("Papelaria");

        ProductRepository productRepository = new ProductRepository();
        ProductService service = new ProductService(productRepository, categoryService);

        Product product = service.create("Notebook", new BigDecimal("3000.00"), 5, cat1.getId());

        product = service.update(
                product.getId(),
                "Notebook Gamer",
                new BigDecimal("5000.00"),
                3,
                cat2.getId()
        );

        assertEquals("Notebook Gamer", product.getName());
        assertEquals(new BigDecimal("5000.00"), product.getPrice());
        assertEquals(3, product.getQuantity());
        assertEquals(cat2.getId(), product.getCategoryId());
    }
}
