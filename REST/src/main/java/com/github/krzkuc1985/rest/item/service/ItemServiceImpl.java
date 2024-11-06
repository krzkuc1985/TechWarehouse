package com.github.krzkuc1985.rest.item.service;

import com.github.krzkuc1985.dto.item.ItemRequest;
import com.github.krzkuc1985.dto.item.ItemResponse;
import com.github.krzkuc1985.dto.inventory.InventoryResponse;
import com.github.krzkuc1985.rest.item.mapper.ItemMapper;
import com.github.krzkuc1985.rest.item.model.Item;
import com.github.krzkuc1985.rest.item.repository.ItemRepository;
import com.github.krzkuc1985.rest.inventory.mapper.InventoryMapper;
import com.github.krzkuc1985.rest.inventory.service.InventoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final InventoryService inventoryService; //TODO fix this
    private final InventoryMapper inventoryMapper;

    @Override
    public Item findByIdOrThrowException(Long id) {
        return itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ItemResponse findById(Long id) {
        return itemMapper.mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public List<ItemResponse> findAll() {
        return itemMapper.mapToResponse(itemRepository.findAll());
    }

    @Override
    @Transactional
    public ItemResponse create(ItemRequest itemRequest) {
        return itemMapper.mapToResponse(itemRepository.save(itemMapper.mapToEntity(itemRequest)));
    }

    @Override
    @Transactional
    public ItemResponse update(Long id, ItemRequest itemRequest) {
        Item item = findByIdOrThrowException(id);
        itemMapper.mapToEntity(item, itemRequest);
        return itemMapper.mapToResponse(itemRepository.save(item));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Item item = findByIdOrThrowException(id);
        itemRepository.delete(item);
    }

    @Override
    @Transactional
    public void changeQuantity(Item item, Integer quantity) {
        if (item.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough items in stock");
        }
        item.setQuantity(item.getQuantity() - quantity);
        itemRepository.save(item);
    }

    @Override
    public List<InventoryResponse> getInventories(Long id) {
        return inventoryMapper.mapToResponse(inventoryService.findByItemId(id));
    }

}
