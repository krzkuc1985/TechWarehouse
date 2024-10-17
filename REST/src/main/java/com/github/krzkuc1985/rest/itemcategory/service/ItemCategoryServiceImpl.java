package com.github.krzkuc1985.rest.itemcategory.service;

import com.github.krzkuc1985.dto.itemcategory.ItemCategoryRequest;
import com.github.krzkuc1985.dto.itemcategory.ItemCategoryResponse;
import com.github.krzkuc1985.rest.itemcategory.mapper.ItemCategoryMapper;
import com.github.krzkuc1985.rest.itemcategory.model.ItemCategory;
import com.github.krzkuc1985.rest.itemcategory.repository.ItemCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemCategoryServiceImpl implements ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;
    private final ItemCategoryMapper itemCategoryMapper;

    @Override
    public ItemCategory findByOrThrowException(Long id) {
        return itemCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ItemCategoryResponse findById(Long id) {
        return itemCategoryMapper.mapToResponse(findByOrThrowException(id));
    }

    @Override
    public List<ItemCategoryResponse> findAll() {
        return itemCategoryMapper.mapToResponse(itemCategoryRepository.findAll());
    }

    @Override
    @Transactional
    public ItemCategoryResponse create(ItemCategoryRequest itemCategoryRequest) {
        return itemCategoryMapper.mapToResponse(itemCategoryRepository.save(itemCategoryMapper.mapToEntity(itemCategoryRequest)));
    }

    @Override
    @Transactional
    public ItemCategoryResponse update(Long id, ItemCategoryRequest itemCategoryRequest) {
        ItemCategory itemCategory = findByOrThrowException(id);
        itemCategory.setName(itemCategoryRequest.getName());
        return itemCategoryMapper.mapToResponse(itemCategoryRepository.save(itemCategory));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ItemCategory itemCategory = findByOrThrowException(id);
        itemCategoryRepository.delete(itemCategory);
    }
}
