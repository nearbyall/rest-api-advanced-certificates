package com.epam.esm.converter;

import com.epam.esm.persistence.repository.sort.SortOrder;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts String sort order to {@link SortOrder} Enum.
 */
public class StringToSortOrderConverter implements Converter<String, SortOrder> {

    @Override
    public SortOrder convert(String source) {
        return SortOrder.valueOf(source.toUpperCase());
    }
}
