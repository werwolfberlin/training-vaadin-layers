package industries.werwolf.training.layers.persistence.filter;

import org.jspecify.annotations.Nullable;

public record Range<T>(@Nullable T min, @Nullable T max) {
}
