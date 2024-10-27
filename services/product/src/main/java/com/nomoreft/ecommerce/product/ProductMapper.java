package com.nomoreft.ecommerce.product;

import com.nomoreft.ecommerce.category.Category;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductMapper {
    public Optional<Product> toProduct(ProductRequest request) {
        return Optional.ofNullable(request)
                .map(req -> Product.builder()
                        .id(req.id())
                        .name(req.name())
                        .description(req.description())
                        .price(req.price())
                        .availableQuantity(req.availableQuantity())
                        .category(
                                Category.builder()
                                        .id(req.categoryId())
                                        .build()
                        )
                        .build());
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );

    }
}
