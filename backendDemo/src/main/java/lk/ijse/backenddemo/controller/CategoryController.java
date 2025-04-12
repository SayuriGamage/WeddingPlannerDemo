package lk.ijse.backenddemo.controller;


import lk.ijse.backenddemo.dto.CategoriesDTO;
import lk.ijse.backenddemo.entity.Category;
import lk.ijse.backenddemo.entity.Services;
import lk.ijse.backenddemo.repo.CategoryRepository;
import lk.ijse.backenddemo.service.CategoryService;
import lk.ijse.backenddemo.util.PicEncoder;
import lk.ijse.backenddemo.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin
public class CategoryController {


    @Autowired
    private CategoryService categoryService;


    @PostMapping(value ="/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<CategoriesDTO> saveCategory(
            @RequestPart ("name") String name,
            @RequestPart ("description") String description,
            @RequestPart ("photo") MultipartFile photo
    ) {
        CategoriesDTO categoryDTO = new CategoriesDTO();

        String image = PicEncoder.generatePicture(photo);

        categoryDTO.setName(name);
        categoryDTO.setDescription(description);
        categoryDTO.setPhoto(image);

        return ResponseEntity.ok(categoryService.saveCategory(categoryDTO));
    }


    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllServices() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateCategory(
            @PathVariable ("id") String cid,
            @RequestPart("name") String name,
            @RequestPart("description") String description,
            @RequestPart("photo") MultipartFile photo) {

        CategoriesDTO categoryDTO = new CategoriesDTO();
        categoryDTO.setName(name);
        categoryDTO.setDescription(description);
        categoryDTO.setPhoto(PicEncoder.generatePicture(photo));

        return ResponseEntity.ok(categoryService.updateCategory(cid,categoryDTO));

    }


    @DeleteMapping(path = "/delete/{name}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseUtil> deleteCategory(@PathVariable String name) {
        categoryService.deleteCategory(name);
        return ResponseEntity.ok(new ResponseUtil(200, "Category has been deleted", null));
    }



}