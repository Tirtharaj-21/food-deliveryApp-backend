package foodDelivery.app.controller;
import foodDelivery.app.dto.request.CategoryRequest;
import foodDelivery.app.dto.response.ApiResponse;
import foodDelivery.app.dto.response.CategoryResponse;
import foodDelivery.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @PostMapping("/save")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        categoryService.createCategory(request)
                );
    }

    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<ApiResponse<String>> updateCategory(@PathVariable Long id,
                                                           @Valid @RequestBody CategoryRequest request) {

        categoryService.updateCategory(id, request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .data("Category updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("removeCategory/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .data("Category removed")
                .build();
        return ResponseEntity.ok(response);
    }
}
