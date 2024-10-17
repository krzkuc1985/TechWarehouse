package com.github.krzkuc1985.rest.itemcategory.service;

import com.github.krzkuc1985.dto.itemcategory.ItemCategoryRequest;
import com.github.krzkuc1985.dto.itemcategory.ItemCategoryResponse;
import com.github.krzkuc1985.rest.itemcategory.model.ItemCategory;

import java.util.List;

public interface ItemCategoryService {

    ItemCategory findByOrThrowException(Long id);

    ItemCategoryResponse findById(Long id);

    List<ItemCategoryResponse> findAll();

    ItemCategoryResponse create(ItemCategoryRequest itemCategoryRequest);

    ItemCategoryResponse update(Long id, ItemCategoryRequest itemCategoryRequest);

    void delete(Long id);
}
