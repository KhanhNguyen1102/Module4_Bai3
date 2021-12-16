package controller;
import model.Category;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.ProductDAO;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductDAO productDAO;
    @GetMapping("")
    public String showList(Model model,@RequestParam(defaultValue = "") String key) {
        List<Product> products;
        if (key == "") {
            products = productDAO.findAll();
        } else {
            products = productDAO.findByName(key);
        }
        model.addAttribute("products",products);
        List<Category> categories = productDAO.findCategoryByBlogs(products);
        model.addAttribute("categories",categories) ;
        return "index";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model){
        List<Category> categories = productDAO.findAllCategory();
        model.addAttribute("categories",categories) ;
        model.addAttribute("product", new Product());
        return "create";
    }
    @PostMapping("/create")
    public String create(Product product) throws SQLException {
        product.setId(1);
        productDAO.add(product);
        return "redirect:/product";
    }
    @GetMapping("/{id}/delete")
    public String delete (Model model,@PathVariable int id) throws SQLException {
        productDAO.delete(id);
        return "redirect:/product";
    }
    @GetMapping("/{id}/edit")
        public String showEditForm(Model model ,@PathVariable int id){
        List<Category> categories = productDAO.findAllCategory();
        model.addAttribute("product", productDAO.findById(id));
        model.addAttribute("categories",categories) ;
         return "edit";
        }
    @PostMapping("/edit")
    public String edit(Product product) throws SQLException {
        productDAO.edit(product);
        return "redirect:/product";
    }
    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("product", productDAO.findById(id));
        model.addAttribute("category",productDAO.findCategoryById(productDAO.findById(id).getCategoryId()));
        return "/view";
    }
}
