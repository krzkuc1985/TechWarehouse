package com.github.krzkuc1985.rest.itemcategory.mapper;

import com.github.krzkuc1985.dto.itemcategory.ItemCategoryRequest;
import com.github.krzkuc1985.dto.itemcategory.ItemCategoryResponse;
import com.github.krzkuc1985.rest.itemcategory.model.ItemCategory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemCategoryMapper {

    public ItemCategoryResponse mapToResponse(ItemCategory itemCategory) {
        return ItemCategoryResponse.builder()
                .id(itemCategory.getId())
                .name(itemCategory.getName())
                .build();
    }

    public List<ItemCategoryResponse> mapToResponse(Collection<ItemCategory> itemCategories) {
        return itemCategories.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ItemCategory mapToEntity(ItemCategory itemCategory, ItemCategoryRequest itemCategoryRequest) {
        itemCategory.setName(itemCategoryRequest.getName());
        return itemCategory;
    }

    public ItemCategory mapToEntity(ItemCategoryRequest itemCategoryRequest) {
        return mapToEntity(new ItemCategory(), itemCategoryRequest);
    }

}
