package com.serjer.task.consumer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serjer.task.consumer.model.Item;

public interface ItemRepository extends JpaRepository <Item, Long> {

    List<Item> findAllItemsByStatusType(String statusType);

    Item findItemByUuidTps(String uuid);
}
