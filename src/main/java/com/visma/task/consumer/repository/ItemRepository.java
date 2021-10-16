package com.visma.task.consumer.repository;

import com.visma.task.consumer.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository <Item, Long> {

    List<Item> findAllItemsByStatusType(String statusType);

    Item findItemByUuidTps(String uuid);
}
