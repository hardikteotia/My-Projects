package com.hardy.SpringEcom.controller;

import com.hardy.SpringEcom.model.Product;
import com.hardy.SpringEcom.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

//    @GetMapping("/products")
//    public List<Product> getProducts(){
//        return productService.getAllProducts();
//    } we can do like this also but when we have to send the response and the data then we use "ResponseEntity<>"

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK );
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product product = productService.getProductById(id);

        if(product == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product savedProduct = null;
        try {
            savedProduct = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]> getImageById(@PathVariable int productId){
        Product product = productService.getProductById(productId);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product.getImage(), HttpStatus.OK);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product updatedProduct = null;

        try{
            updatedProduct = productService.updateProduct(product, imageFile);
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        }
        catch(IOException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id){
        Product product = productService.getProductById(id);


            if(product == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            productService.deleteProductById(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);


    }

    @GetMapping("products/search")
    public ResponseEntity<List<Product>> searchPrdouct(@RequestParam String keyword){
        List<Product> products = productService.searchProduct(keyword);
        System.out.println("Searching with: " + keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
