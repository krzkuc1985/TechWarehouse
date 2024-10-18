package com.github.krzkuc1985.rest.item.service;

import com.github.krzkuc1985.dto.item.ItemRequest;
import com.github.krzkuc1985.dto.item.ItemResponse;
import com.github.krzkuc1985.rest.item.model.Item;

import java.util.List;

public interface ItemService {

    Item findByIdOrThrowException(Long id);

    ItemResponse findById(Long id);

    List<ItemResponse> findAll();

    ItemResponse create(ItemRequest itemRequest);

    ItemResponse update(Long id, ItemRequest itemRequest);

    void delete(Long id);

}
