package asia.fortitude.java.sboot.productapplication.service;

import asia.fortitude.java.sboot.productapplication.model.Product;
import asia.fortitude.java.sboot.productapplication.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProductService implements IProduct{
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Page<Product> getProductsWithPagination(Pageable pageable){
        Page<Product> result = productRepository.findAll(pageable);
        return result;
    }

    public Page<Product> getProductByCode(String code, Pageable pageable){
        Page<Product> result = productRepository.findByCode(code, pageable);
        return result;
    }

    public Product addOrUpdateProduct(Product requestProduct){
        Product responseProduct = new Product();
        if(null==requestProduct.getId()){
            fillProduct(responseProduct,requestProduct);
        }
        else{
            Product product = productRepository.findById(requestProduct.getId())
                    .orElseThrow(() -> new NoSuchElementException("No product found for this id :: " + requestProduct.getId()));
            fillProduct(responseProduct, requestProduct);
            responseProduct.setId(product.getId());
        }

        return productRepository.save(responseProduct);
    }

    public void deleteProduct(Product requestProduct){
        productRepository.deleteById(requestProduct.getId());
    }

    void fillProduct(Product responseProduct, Product requestProduct){

        responseProduct.setName(requestProduct.getName());
        responseProduct.setBrand(requestProduct.getBrand());
        responseProduct.setCategory(requestProduct.getCategory());
        responseProduct.setCode(requestProduct.getCode());
        responseProduct.setDescription(requestProduct.getDescription());
        responseProduct.setType(requestProduct.getType());
    }

}
