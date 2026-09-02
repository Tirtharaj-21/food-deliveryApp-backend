package foodDelivery.app.service;

import foodDelivery.app.dto.request.CategoryRequest;
import foodDelivery.app.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    void  updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);
}
