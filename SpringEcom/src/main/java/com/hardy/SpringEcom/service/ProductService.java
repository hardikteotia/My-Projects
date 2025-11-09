package com.hardy.SpringEcom.service;

import com.hardy.SpringEcom.model.Product;
import com.hardy.SpringEcom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {


    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(int id) {
        return productRepo.findById(id).get();
    }

    public Product addProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());//to get the name of the imageFile
        product.setImageType(image.getContentType());//to get the type of the image
        product.setImage(image.getBytes());//to get the image in the byte format
        return productRepo.save(product);

    }

    public Product updateProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());//to get the name of the imageFile
        product.setImageType(image.getContentType());//to get the type of the image
        product.setImage(image.getBytes());//to get the image in the byte format
        return productRepo.save(product);

    }

    public void deleteProductById(int id) {
        productRepo.deleteById(id);

    }

    public List<Product> searchProduct(String keyword) {
        return productRepo.searchProducts(keyword);
    }
}
