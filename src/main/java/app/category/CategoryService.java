package app.category;

import java.util.List;

public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category create(String name) {
        validateName(name);
        return repository.save(new Category(name.trim()));
    }

    public Category get(Long id) {
        validateId(id);
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
    }

    public List<Category> listAll() {
        return repository.findAll();
    }

    public Category update(Long id, String name) {
        validateId(id);
        validateName(name);
        Category existing = get(id);
        existing.setName(name.trim());
        return repository.save(existing);
    }

    public void delete(Long id) {
        validateId(id);
        boolean removed = repository.delete(id);
        if (!removed) {
            throw new IllegalArgumentException("Categoria não encontrada");
        }
    }

    private void validateId(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("Id inválido");
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().length() < 2) {
            throw new IllegalArgumentException("Nome inválido");
        }
    }
}
