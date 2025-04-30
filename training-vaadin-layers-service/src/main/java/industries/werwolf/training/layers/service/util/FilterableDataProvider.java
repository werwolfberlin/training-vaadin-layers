package industries.werwolf.training.layers.service.util;

import industries.werwolf.training.layers.persistence.util.Page;

import java.util.stream.Stream;

public interface FilterableDataProvider<T> {
    Stream<T> fetch(Page page);
}
