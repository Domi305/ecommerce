package pl.github.dominik.ecommerce.application;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import pl.github.dominik.ecommerce.domain.ProductCategory;
import pl.github.dominik.ecommerce.domain.ProductCategoryRepository;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository repository;

    private final ProductCategoryDomainDtoConverter converter;

    public Optional<ProductCategoryDto> get(long id) {
        return repository.findById(id).map(converter::convert);
    }

    public ProductCategoryDto save(@NonNull String name, Long parentCategoryId) {
        var entity = new ProductCategory();
        entity.setName(name);

        Optional.ofNullable(parentCategoryId)
                .flatMap(repository::findById)
                .ifPresent(entity::setParentCategory);

        entity = repository.save(entity);
        return converter.convert(entity);
    }

    public void removeOne(long categoryId) {
        repository.findById(categoryId).ifPresent(category -> {
            for (final ProductCategory childCategory : category.getChildrenCategories()) {
                childCategory.setParentCategory(category.getParentCategory());
            }
            repository.delete(category);
        });
    }

    public void removeAll(long categoryId) {
        repository.findById(categoryId).ifPresent(category -> {
            // 2 queues to avoid recursion
            Deque<ProductCategory> productCategoriesToDelete = new LinkedList<>();
            Queue<ProductCategory> productCategoriesToProcess = new LinkedList<>();

            productCategoriesToProcess.add(category);

            while (!productCategoriesToProcess.isEmpty()) {
                val productCategory = productCategoriesToProcess.poll();
                productCategoriesToDelete.addFirst(productCategory);
                productCategoriesToProcess.addAll(productCategory.getChildrenCategories());
            }

            while (!productCategoriesToDelete.isEmpty()) {
                repository.delete(productCategoriesToDelete.pollFirst());
            }
        });
    }

    //or:
    //recursion - might lead to running out of memory
    @SuppressWarnings("unused")
    private void removeAll(ProductCategory category) {
        category.getChildrenCategories().forEach(this::removeAll);
        repository.delete(category);

        //A -> B -> {C,D}
        //1. removeAll(A)
        //2. removeAll(B)
        //3. removeAll(C)
        //4. repository.delete(C);
        //5. removeAll(D)
        //5. repository.delete(B);
        //6. repository.delete(A);
    }

    public List<ProductCategoryDto> list(Long rootProductCategoryId) {

        List<ProductCategoryDto> result = new LinkedList<>();
        Queue<ProductCategory> queue = new LinkedList<>();

        queue.addAll(repository.findByParentCategoryId(rootProductCategoryId));

        while (!queue.isEmpty()) {
            val productCategory = queue.poll();
            result.add(converter.convert(productCategory));
            queue.addAll(productCategory.getChildrenCategories());
        }

        return result;
    }
}