package com.github.krzkuc1985.rest.item.repository;

import com.github.krzkuc1985.rest.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
