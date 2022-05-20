/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.plant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.utils.DBUtils;

/**
 *
 * @author PHT
 */
public class PlantDAO {

    public PlantDAO() {
        DBUtils.loadDriver();
    }

    //get plants by name
    public List<Plant> getPlants(String name) {
        List<Plant> list = null;
        try {
            //Connecting to a database
            Connection con = DriverManager.getConnection(DBUtils.URL);
            //Creating and executing sql statements            
            //String sql = "select p.*, c.name as category from plant p join category c on p.categoryId=c.id where p.name like ?";
            String sql = "select p.*, c.name as category "
                            + "from plant p join category c on p.categoryId=c.id where p.name like ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + name + "%");
            ResultSet rs = stm.executeQuery();
            //Loading data into the list      
            list = new ArrayList<>();
            while (rs.next()) {
                Plant plant = new Plant();
                plant.setId(rs.getInt("id"));
                plant.setName(rs.getString("name"));
                plant.setPrice(rs.getInt("price"));
                plant.setImgPath(rs.getString("imgPath"));
                plant.setDescription(rs.getString("description"));
                plant.setStatus(rs.getInt("status"));
                plant.setCategoryId(rs.getInt("categoryId"));
                plant.setCategory(rs.getString("category"));
                list.add(plant);
            }
            //Closing the connection
            con.close();
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return list;
    }

    //get a plant by id
    public Plant getPlant(int id) {
        Plant plant = null;
        try {
            //Connecting to a database
            Connection con = DriverManager.getConnection(DBUtils.URL);
            //Executing the stm
            //Creating and executing sql statements            
            String sql = "select p.*, c.name as category "
                            + "from plant p join category c on p.categoryId=c.id "
                            + "where p.id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            //Loading data into the list                  
            if (rs.next()) {
                plant = new Plant();
                plant.setId(rs.getInt("id"));
                plant.setName(rs.getString("name"));
                plant.setPrice(rs.getInt("price"));
                plant.setImgPath(rs.getString("imgPath"));
                plant.setDescription(rs.getString("description"));
                plant.setStatus(rs.getInt("status"));
                plant.setCategoryId(rs.getInt("categoryId"));
                plant.setCategory(rs.getString("category"));
            }
            //Closing the connection
            con.close();
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        return plant;
    }

    public int updateStatus(int id, int status) {
        int count = 0;
        try {
            //Connecting to a database
            Connection con = DriverManager.getConnection(DBUtils.URL);
            //Creating and executing sql statements            
            String sql = "update plant set status=? where id=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, status);
            stm.setInt(2, id);
            count = stm.executeUpdate();
            //Closing the connection
            con.close();
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        return count;
    }
}
