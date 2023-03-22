package com.sample.retailstore.cache;

import com.sample.retailstore.constants.CategoryType;
import com.sample.retailstore.entity.Category;
import com.sample.retailstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author irfan.nagoo
 */


@Component
@RequiredArgsConstructor
public class CategoryCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryCache.class);

    private final CategoryRepository categoryRepository;

    private final List<Category> categoryCache = new ArrayList<>();

    @PostConstruct
    private void load() {
        categoryRepository.findAll().forEach(status -> categoryCache.add(status));
        LOGGER.info("Category cache initialized Successfully");
    }

    public Category getCategory(String category) {
        return categoryCache.stream()
                .filter(cat -> cat.getCategory().equalsIgnoreCase(category))
                .findAny()
                .orElse(null);
    }

    public List<CategoryType> getSuperCategoryTypeList(Category category) {
        List<CategoryType> categoryTypeList = new ArrayList<>();
        populateSuperCategoryHierarchy(categoryTypeList, category);
        return categoryTypeList;
    }

    public List<Category> getSubCategoryList(CategoryType categoryType) {
        List<Category> subCategoryList = new ArrayList<>();
        Category category = getCategory(categoryType.getValue());
        subCategoryList.add(category);
        List<Category> categoryList = categoryCache.stream()
                .filter(cat -> category.getId().equals(cat.getParentId()))
                .collect(Collectors.toList());
        subCategoryList.addAll(categoryList);
        return subCategoryList;
    }

    private void populateSuperCategoryHierarchy(List<CategoryType> finalList, Category category) {
        if (null == category) {
            return;
        }
        finalList.add(0, CategoryType.getCategoryByValue(category.getCategory()));
        Category parentCategory = categoryCache.stream()
                .filter(cat -> cat.getId().equals(category.getParentId()))
                .findFirst()
                .orElse(null);
        populateSuperCategoryHierarchy(finalList, parentCategory);
    }
}
