package industries.werwolf.training.layers.persistence.util;

public record SortOrder(String property, SortOrder.Direction direction) {

    public enum Direction {
        ASC, DESC
    }
}
