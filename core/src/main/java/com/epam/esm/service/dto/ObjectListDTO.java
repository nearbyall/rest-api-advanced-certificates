package com.epam.esm.service.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class ObjectListDTO<T> extends RepresentationModel<ObjectListDTO<T>> {

    private List<T> objects;
    private PageDTO page;

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    public PageDTO getPage() {
        return page;
    }

    public void setPage(PageDTO page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ObjectListDTO<?> that = (ObjectListDTO<?>) o;

        if (objects != null ? !objects.equals(that.objects) : that.objects != null) return false;
        return page != null ? page.equals(that.page) : that.page == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (objects != null ? objects.hashCode() : 0);
        result = 31 * result + (page != null ? page.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() +
                "{" +
                "objects=" + objects +
                ", page=" + page +
                '}';
    }

}
