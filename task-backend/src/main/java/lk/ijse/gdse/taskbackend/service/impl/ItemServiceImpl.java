package lk.ijse.gdse.taskbackend.service.impl;

import lk.ijse.gdse.taskbackend.dto.ItemDTO;
import lk.ijse.gdse.taskbackend.entity.Item;
import lk.ijse.gdse.taskbackend.repository.ItemRepo;
import lk.ijse.gdse.taskbackend.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private final ItemRepo itemRepo;

    @Autowired
    private final ModelMapper modelMapper;

    public ItemServiceImpl(ItemRepo itemRepo, ModelMapper modelMapper) {
        this.itemRepo = itemRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Item saveItem(ItemDTO itemDTO) {
        return itemRepo.save(modelMapper.map(itemDTO, Item.class));
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }

    @Override
    public String deleteItem(Long id) {
        Optional<Item> byId = itemRepo.findById(id);
        if (byId.isPresent()) {
            itemRepo.deleteById(id);
            return "Delete Success";
        }
        return "Not found ItemId";
    }

    @Override
    public Optional<Item> findItemById(Long id) {
        return itemRepo.findById(id);
    }

    @Override
    public Item updateItem(ItemDTO itemDTO) {
        Optional<Item> existingItem = itemRepo.findById(Long.valueOf(itemDTO.getId()));
        if (existingItem.isPresent()) {
            Item item = existingItem.get();
            item.setCode(itemDTO.getCode());
            item.setName(itemDTO.getName());
            item.setCategory(itemDTO.getCategory());
            item.setUnit(itemDTO.getUnit());
            item.setStatus(Item.Status.valueOf(itemDTO.getStatus()));

            return itemRepo.save(item);
        }
        return null;
    }

    @Override
    public List<String> findAllItemIds() {
        return itemRepo.findAllItemIds();
    }
}
