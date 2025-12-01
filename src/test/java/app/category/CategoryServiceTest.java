package app.category;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {

    @Test
    void deveCriarCategoriaValida() {
        CategoryService service = new CategoryService(new CategoryRepository());
        Category category = service.create(" Tecnologia ");

        assertNotNull(category.getId());
        assertEquals("Tecnologia", category.getName());
    }

    @Test
    void naoDeveCriarCategoriaComNomeInvalido() {
        CategoryService service = new CategoryService(new CategoryRepository());

        assertThrows(IllegalArgumentException.class, () -> service.create(null));
        assertThrows(IllegalArgumentException.class, () -> service.create(""));
        assertThrows(IllegalArgumentException.class, () -> service.create(" "));
        assertThrows(IllegalArgumentException.class, () -> service.create("A"));
    }

    @Test
    void naoDeveBuscarIdInvalido() {
        CategoryService service = new CategoryService(new CategoryRepository());

        assertThrows(IllegalArgumentException.class, () -> service.get(null));
        assertThrows(IllegalArgumentException.class, () -> service.get(0L));
        assertThrows(IllegalArgumentException.class, () -> service.get(-1L));
    }

    @Test
    void naoDeveAtualizarComNomeInvalido() {
        CategoryService service = new CategoryService(new CategoryRepository());
        Category created = service.create("Tecnologia");

        assertThrows(IllegalArgumentException.class, () -> service.update(created.getId(), null));
        assertThrows(IllegalArgumentException.class, () -> service.update(created.getId(), " "));
        assertThrows(IllegalArgumentException.class, () -> service.update(created.getId(), "A"));
    }

    @Test
    void naoDeveDeletarCategoriaInexistente() {
        CategoryService service = new CategoryService(new CategoryRepository());
        assertThrows(IllegalArgumentException.class, () -> service.delete(1L));
    }
}
