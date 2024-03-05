package lk.ijse.gdse.taskbackend.service.impl;

import lk.ijse.gdse.taskbackend.dto.ItemDTO;
import lk.ijse.gdse.taskbackend.entity.Category;
import lk.ijse.gdse.taskbackend.entity.Item;
import lk.ijse.gdse.taskbackend.entity.Unit;
import lk.ijse.gdse.taskbackend.repository.CategoryRepo;
import lk.ijse.gdse.taskbackend.repository.ItemRepo;
import lk.ijse.gdse.taskbackend.repository.UnitRepo;
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
    private final CategoryRepo categoryRepo;
    @Autowired
    private final UnitRepo unitRepo;

    @Autowired
    private final ModelMapper modelMapper;

    public ItemServiceImpl(ItemRepo itemRepo, CategoryRepo categoryRepo, UnitRepo unitRepo, ModelMapper modelMapper) {
        this.itemRepo = itemRepo;
        this.categoryRepo = categoryRepo;
        this.unitRepo = unitRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Item saveItem(ItemDTO itemDTO) {
        // Fetch or create Category entity
        Category category = categoryRepo.findById(itemDTO.getCategory().getId()).orElse(new Category());

        // Fetch or create Unit entity
        Unit unit = unitRepo.findById(itemDTO.getUnit().getId()).orElse(new Unit());

        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setCode(itemDTO.getCode());
        item.setCategory(category);
        item.setUnit(unit);
        item.setStatus(Item.Status.valueOf(itemDTO.getStatus()));

        // Merge or persist Category and Unit
        categoryRepo.save(category);
        unitRepo.save(unit);

        return itemRepo.save(item);
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
