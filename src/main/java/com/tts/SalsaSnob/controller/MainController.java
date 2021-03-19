package com.tts.SalsaSnob.controller;

import com.tts.SalsaSnob.model.Product;
import com.tts.SalsaSnob.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@ControllerAdvice // This makes the `@ModelAttribute`s of this controller available to all
// controllers, for the navbar.
public class MainController {
    @Autowired
    ProductService productService;

    // public Product(int quantity, float price, String description,
    // String name, String brand, String category,String image) {

    public void addNew() {
        List<Product> allProducts = productService.findAll();

        if (allProducts.isEmpty()) {

            List<Product> newProducts = new ArrayList<Product>();

            newProducts.add(new Product(16, (float) 7.00, "Medium: Combination of ripe tomatoes, fresh jalapenos, onions and cilantro", "Salsa Roja", "Roja", "Salsa",
                    "salsa-roja.jpeg"));

            newProducts.add(new Product(16, (float) 7.00, "Hot:Roasted tomatillos, spicy serrano chiles, onions and cilantro", "Salsa Tomatillo", "Tomatillo", "Salsa",
                    "tomatillo-salsa.jpeg"));

            newProducts.add(new Product(16, (float) 7.00, "Bold and Spicy: Roasted tomatillos, tomatoes, chiles de arbol, garlic, onion, cilantro", "Salsa Campechana", "Campechana", "Salsa",
                    "salsa-campechana.jpeg"));



            for (Product product : newProducts) {
                productService.save(product);
            }
        } else {

            System.out.println("You don't need more items!");
        }
    }

    @GetMapping("/")
    public String main() {
        addNew();
        return "main";
    }

    @ModelAttribute("products")
    public List<Product> products() {
        return productService.findAll();
    }

    @ModelAttribute("categories")
    public List<String> categories() {
        return productService.findDistinctCategories();
    }

    @ModelAttribute("brands")
    public List<String> brands() {
        return productService.findDistinctBrands();
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) String category, @RequestParam(required = false) String brand,
                         Model model) {
        List<Product> filtered = productService.findByBrandAndOrCategory(brand, category);
        model.addAttribute("products", filtered); // Overrides the @ModelAttribute above.
        return "main";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
