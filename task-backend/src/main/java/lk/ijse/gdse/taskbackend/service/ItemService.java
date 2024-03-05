package lk.ijse.gdse.taskbackend.service;

import lk.ijse.gdse.taskbackend.dto.ItemDTO;
import lk.ijse.gdse.taskbackend.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Item saveItem(ItemDTO itemDTO);

    List<Item> getAllItems();

    String deleteItem(Long id);

    Optional<Item> findItemById(Long id);

    Item updateItem(ItemDTO itemDTO);
    List<String> findAllItemIds();
}
