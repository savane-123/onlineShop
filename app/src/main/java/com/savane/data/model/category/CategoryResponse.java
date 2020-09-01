package com.savane.data.model.category;

import java.util.List;

public class CategoryResponse {
    private boolean error;
    private List<CategoryModel> categories;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }
}
