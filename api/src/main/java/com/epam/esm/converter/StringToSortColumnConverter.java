package com.epam.esm.converter;

import com.epam.esm.persistence.repository.sort.SortColumn;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts String sort column to {@link SortColumn} Enum.
 */
public class StringToSortColumnConverter implements Converter<String, SortColumn> {

    @Override
    public SortColumn convert(String source) {
        return SortColumn.valueOf(source.toUpperCase());
    }
}
