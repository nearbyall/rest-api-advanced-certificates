package com.epam.esm.service.pagination;

import com.epam.esm.service.dto.PageDTO;
import org.springframework.stereotype.Component;

/**
 * Class used for the pagination, calculates the total number of possible pages and collects the PageDTO object for the DTO entity instances
 */
@Component
public class Paginator {

    /**
     * Calculates the offset for the future retrieval in the repository from the given page number and page size
     * @param page page number
     * @param size page size
     * @return offset
     */
    public int getOffset(int page, int size) {
        return size * (page - 1);
    }

    /**
     * Collects PageDTO object with given attributes
     * @param size page size
     * @param totalElements total existing entity instances in the database
     * @param number page number
     * @return page
     */
    public PageDTO getPage(int size, int totalElements, int number) {
        int totalPages = getTotalPages(totalElements, size);

        return new PageDTO()
                .setSize(size)
                .setTotalElements(totalElements)
                .setTotalPages(totalPages)
                .setNumber(number);
    }

    private int getTotalPages(int totalElements, double size) {
        return (int) Math.ceil(totalElements / size);
    }
}
