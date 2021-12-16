package service;

import model.Category;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO{
    public ProductDAO() {
    }
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testmodule3?useSSL=false", "root", "123456");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
    @Override
    public void add(Product product) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("insert into product(name,price,quantity,color,description,categoryId) values (?,?,?,?,?,?)");) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3,product.getQuantity());
            preparedStatement.setString(4,product.getColor());
            preparedStatement.setString(5,product.getDescription());
            preparedStatement.setInt(6,product.getCategoryId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public Product findById(int id) {
        Product product = null;
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from product where id = ?");){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int id1 = rs.getInt("id");
                String name= rs.getString("name");
               double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color= rs.getString("color");
                String description = rs.getString("description");
                int categoryId = rs.getInt("categoryId");
                product=new Product(id1,name,price,quantity,color,description,categoryId);
            }
        }
        catch (SQLException e ){
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from product ");) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id1 = rs.getInt("id");
                String name= rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color= rs.getString("color");
                String description = rs.getString("description");
                int categoryId = rs.getInt("categoryId");
                products.add(new Product(id1,name,price,quantity,color,description,categoryId));
            }
        } catch (SQLException e) {
        }
        return products;
    }
    public List<Category> findAllCategory() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from category ");) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                categories.add(new Category(id, name));
            }
        } catch (SQLException e) {
        }
        return categories;
    }
    public List<Category> findCategoryByBlogs(List<Product> products){
        List<Category> list = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            list.add(findCategoryById(products.get(i).getCategoryId()));
        }
        return list;
    }
    public Category findCategoryById(int id){
        Category category = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from category where id = ? ");) {
            System.out.println(preparedStatement);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id1 = rs.getInt("id");
                String name = rs.getString("name");
                category = new Category(id1,name);
            }
        } catch (SQLException e) {
        }
        return category;
    }
    @Override
    public boolean delete(int id) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("delete from product where id = ?");) {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
        return false;
    }

    @Override
    public boolean edit(Product product) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("update product set name=?,price=?,quantity =?,color =?,description=?,categoryId=? where id = ?");) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3,product.getQuantity());
            preparedStatement.setString(4,product.getColor());
            preparedStatement.setString(5,product.getDescription());
            preparedStatement.setInt(6,product.getQuantity());
            preparedStatement.setInt(7,product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
        return false;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("select * from product where name like ?");) {
            System.out.println(preparedStatement);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id1 = rs.getInt("id");
                String name1= rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color= rs.getString("color");
                String description = rs.getString("description");
                int categoryId = rs.getInt("categoryId");
                products.add(new Product(id1,name1,price,quantity,color,description,categoryId));
            }
        } catch (SQLException ignored) {

        }
        return products;
    }
}

