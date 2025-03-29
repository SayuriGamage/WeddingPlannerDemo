package lk.ijse.backenddemo.service;

import lk.ijse.backenddemo.dto.CategoriesDTO;
import lk.ijse.backenddemo.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    CategoriesDTO saveCategory(CategoriesDTO categoryDTO);

    List<Category> getAllCategories();

    void deleteCategory(String name);


    boolean updateCategory(String cid, CategoriesDTO categoryDTO);

}