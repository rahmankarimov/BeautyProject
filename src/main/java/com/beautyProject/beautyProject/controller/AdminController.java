package com.beautyProject.beautyProject.controller;

import com.beautyProject.beautyProject.model.entity.Product;
import com.beautyProject.beautyProject.model.entity.User;
import com.beautyProject.beautyProject.service.OrderService;
import com.beautyProject.beautyProject.service.ProductService;
import com.beautyProject.beautyProject.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    public AdminController(UserService userService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        // Burada admin paneli üçün statistikalar toplana bilər
        // Məsələn: son bir ay ərzində satışlar, məhsul inventarı, istifadəçi sayı və s.

        // Numunə statistika
        Map<String, Object> stats = Map.of(
                "totalUsers", userService.getAllUsers().size(),
                "totalProducts", productService.getAllProducts().size(),
                "lowStockProducts", productService.getAllProducts().stream().filter(p -> p.getStock() < 10).count()
        );

        return ResponseEntity.ok(stats);
    }
}