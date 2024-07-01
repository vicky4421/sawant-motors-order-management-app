package com.vismijatech.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCategoryDTO {
    private Long id;
    private String categoryName;
    private List<ProductDTO> productList;
    private String parentCategory;
    private List<ProductCategoryDTO> childCategories;

}
