package app.category;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class CategoryRepository {

    private final Map<Long, Category> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Category save(Category category) {
        if (category.getId() == null) {
            category.setId(idGenerator.getAndIncrement());
        }
        storage.put(category.getId(), category);
        return category;
    }

    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Category> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean delete(Long id) {
        return storage.remove(id) != null;
    }
}
