package com.epam.esm.service.dto;

import java.util.Objects;

public class PageDTO {

    private int size;
    private int totalElements;
    private int totalPages;
    private int number;

    public int getSize() {
        return size;
    }

    public PageDTO setSize(int size) {
        this.size = size;
        return this;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public PageDTO setTotalElements(int totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public PageDTO setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public PageDTO setNumber(int number) {
        this.number = number;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageDTO pageDTO = (PageDTO) o;

        if (size != pageDTO.size) return false;
        if (totalElements != pageDTO.totalElements) return false;
        if (totalPages != pageDTO.totalPages) return false;
        return number == pageDTO.number;
    }

    @Override
    public int hashCode() {
        int result = size;
        result = 31 * result + totalElements;
        result = 31 * result + totalPages;
        result = 31 * result + number;
        return result;
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() +
                "{" +
                "size=" + size +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", number=" + number +
                '}';
    }

}
