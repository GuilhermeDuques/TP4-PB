package app.product;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepository {

    private final Map<Long, Product> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Product save(Product product) {
        Product toSave = product;
        if (product.getId() == null) {
            Long newId = idGenerator.getAndIncrement();
            toSave = product.withId(newId);
        }
        storage.put(toSave.getId(), toSave);
        return toSave;
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Product> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean delete(Long id) {
        return storage.remove(id) != null;
    }
}
