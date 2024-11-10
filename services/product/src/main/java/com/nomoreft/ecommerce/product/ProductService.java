package com.nomoreft.ecommerce.product;

import com.nomoreft.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);

        return product
                .map(repository::save)
                .map(Product::getId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot create product:: request is null"));
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = extractProductIdsFromRequests(request);
        var storedProducts = repository.findAllByIdInOrderById(productIds);
        validateProductExistence(productIds, storedProducts);
        var storedRequest = matchRequestOrderWithProducts(request);

        return purchaseProcess(storedProducts, storedRequest);
    }

    private ArrayList<ProductPurchaseResponse> purchaseProcess(List<Product> storedProducts, List<ProductPurchaseRequest> storedRequest) {
        var purchaseProducts = new ArrayList<ProductPurchaseResponse>();
        for(int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);

            if(!product.hasSufficientQuantity(productRequest.quantity())) {
                throw new ProductPurchaseException("Cannot purchase products:: Insufficient quantity for product with ID: " + product.getId());
            }
            product.reduceQuantity(productRequest.quantity());

            repository.save(product);
            purchaseProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchaseProducts;
    }

    private static List<ProductPurchaseRequest> matchRequestOrderWithProducts(List<ProductPurchaseRequest> request) {
        var storedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        return storedRequest;
    }

    private void validateProductExistence(List<Integer> productIds, List<Product> storedProducts) {
        if(productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("Cannot purchase products:: One or more products not exists");
        }
    }

    private static List<Integer> extractProductIdsFromRequests(List<ProductPurchaseRequest> request) {
        var productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        return productIds;
    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .toList();
    }
}
