package asia.fortitude.java.sboot.productapplication.controller;

import asia.fortitude.java.sboot.productapplication.model.Product;
import asia.fortitude.java.sboot.productapplication.model.StringResponse;
import asia.fortitude.java.sboot.productapplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/products")
    public Page<Product> getProductWithPagination(
            @RequestParam int pageNum, int pageSize
    ){
        Pageable pageable = PageRequest.of(pageNum, pageSize,  Sort.by("id"));
        return productService.getProductsWithPagination(pageable);
    }

    @GetMapping("/products/{code}")
    public Page<Product> getProductByCode(
            @PathVariable String code,
            @RequestParam(name="pageNum", required = false, defaultValue = "0") int pageNum,
            @RequestParam(name="pageNum", required = false, defaultValue = "5") int pageSize
    ){
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return productService.getProductByCode(code, pageable);
    }

    @PostMapping("/products")
    public Product addNewProduct(
            @RequestBody Product requestProduct
    ){
        LocalDateTime update_time = LocalDateTime.now();
        requestProduct.setUpdated_at(update_time);
        return productService.addOrUpdateProduct(requestProduct);
    }

    @PutMapping("/products/{code}")
    public Product updateProduct(
            @PathVariable String code,
            @RequestBody Product requestProduct
    ){
        LocalDateTime update_time = LocalDateTime.now();
        requestProduct.setUpdated_at(update_time);
        return productService.addOrUpdateProduct(requestProduct);
    }

    @DeleteMapping("/products/{code}")
    public StringResponse deleteProduct(
            @RequestBody Product requestProduct
    ){
        productService.deleteProduct(requestProduct);
        StringResponse response = new StringResponse();
        response.setMessage("Product succesfully deleted.");
        return response;
    }

}
