/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ConnectXAMPP {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "";
        try(Connection connect = DriverManager.getConnection(url, user, password)){
            System.out.println("KET NOI THANH CONG");
            System.out.println(connect.getCatalog());

        }catch(SQLException ex){
    Logger.getLogger(ConnectXAMPP.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
}
