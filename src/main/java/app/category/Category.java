package app.category;

public class Category {

    private final Long id;
    private final String name;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this(null, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category withId(Long id) {
        return new Category(id, this.name);
    }

    public Category withName(String name) {
        return new Category(this.id, name);
    }
}
