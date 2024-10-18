package com.github.krzkuc1985.rest.item.mapper;

import com.github.krzkuc1985.dto.item.ItemRequest;
import com.github.krzkuc1985.dto.item.ItemResponse;
import com.github.krzkuc1985.rest.item.model.Item;
import com.github.krzkuc1985.rest.itemcategory.repository.ItemCategoryRepository;
import com.github.krzkuc1985.rest.measurementunit.repository.MeasurementUnitRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {

    private final MeasurementUnitRepository measurementUnitRepository;
    private final ItemCategoryRepository itemCategoryRepository;


    public ItemMapper(MeasurementUnitRepository measurementUnitRepository, ItemCategoryRepository itemCategoryRepository) {
        this.measurementUnitRepository = measurementUnitRepository;
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public ItemResponse mapToResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .orderNumber(item.getOrderNumber())
                .description(item.getDescription())
                .quantity(item.getQuantity())
                .measurementUnitID(item.getMeasurementUnit().getId())
                .minQuantity(item.getMinQuantity())
                .maxQuantity(item.getMaxQuantity())
                .itemCategoryID(item.getItemCategory().getId())
                .archive(item.getArchive())
                .build();
    }

    public List<ItemResponse> mapToResponse(Collection<Item> items) {
        return items.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Item mapToEntity(Item item, ItemRequest itemRequest) {
        item.setName(itemRequest.getName());
        item.setOrderNumber(itemRequest.getOrderNumber());
        item.setDescription(itemRequest.getDescription());
        item.setQuantity(itemRequest.getQuantity());
        item.setMeasurementUnit(measurementUnitRepository.findById(itemRequest.getMeasurementUnitID()).orElseThrow());
        item.setMinQuantity(itemRequest.getMinQuantity());
        item.setMaxQuantity(itemRequest.getMaxQuantity());
        item.setItemCategory(itemCategoryRepository.findById(itemRequest.getItemCategoryID()).orElseThrow());
        item.setArchive(itemRequest.getArchive());
        return item;
    }

    public Item mapToEntity(ItemRequest itemRequest) {
        return mapToEntity(new Item(), itemRequest);
    }

}
