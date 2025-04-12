package lk.ijse.backenddemo.service.impl;

import lk.ijse.backenddemo.dto.CategoriesDTO;
import lk.ijse.backenddemo.entity.Category;
import lk.ijse.backenddemo.repo.CategoryRepository;
import lk.ijse.backenddemo.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll().stream().map(category -> {
            Category dto = new Category();
            dto.setName(category.getName());
            dto.setDescription(category.getDescription());
            dto.setPhoto(category.getPhoto());
            return dto;
        }).collect(Collectors.toList());
    }




    @Override
    public void deleteCategory(String name) {
        if (categoryRepository.existsByName(name)) {
            categoryRepository.deleteByName(name);
        }
    }

    @Override
    public boolean updateCategory(String id, CategoriesDTO categoryDTO) {

        Category existingcat = categoryRepository.findById(UUID.fromString(id)).orElse(null);

        if (existingcat != null) {
            existingcat.setName(categoryDTO.getName());
            existingcat.setDescription(categoryDTO.getDescription());
            existingcat.setPhoto(categoryDTO.getPhoto());
            categoryRepository.save(existingcat);
            return true;
        }
        return false;

    }


    @Override
    public CategoriesDTO saveCategory(CategoriesDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoriesDTO.class);
    }

}
