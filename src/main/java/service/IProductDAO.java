package service;

import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProductDAO {
    public void add(Product product) throws SQLException;

    public Product findById(int id);

    public List<Product> findAll();

    public boolean delete(int id) throws SQLException;

    public boolean edit(Product product) throws SQLException;

    public List<Product> findByName(String name);
}
