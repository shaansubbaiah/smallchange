package org.smallchange.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;
import org.smallchange.model.User;
import org.smallchange.functionality.UserDetail;
public class UserAuthenticationDaoImpl implements UserAuthenticationDao{
    private DataSource datasource;
    public UserAuthenticationDaoImpl(DataSource datasource) {
        this.datasource = datasource;
    }
    @Override
    public UserDetail loginAuthenticationService(String email, String password) {
        UserDetail userDetails = new UserDetail();
        String sql="null";
        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs=stmt.executeQuery();
            int count=0;
            while(rs.next()) {
                count++;
            }
            String message;
            if(count!=0) {
                message = "authentication successful";
            }
            else {
                message = "client mail or password incorrect";
            }
            userDetails.setMessage(message);
            return userDetails;
        }catch(SQLException e){
            throw new IllegalArgumentException("Database Access Error");
        }
    }
    @Override
    public UserDetail registrationAuthenticationService(String name, String email,
                                                                   String password) {
        UserDetail userDetails= new UserDetail();
        User userDao = new User();
        if( name == null || name.trim().length()==0) {
            String message = "Name cannot be empty or null";
            userDetails.setMessage(message);
            return userDetails;
        }
        if(email == null || email.trim().length()==0) {
            String message = "Mail cannot be empty or null";
            userDetails.setMessage(message);
            return userDetails;
        }
        if(password == null || password.trim().length()==0) {
            String message = "Password cannot be empty or null";
            userDetails.setMessage(message);
            return userDetails;
        }
        String sql="null";
        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, name);
            ResultSet rs=stmt.executeQuery();
            int count=0;
            while(rs.next()) {
                count++;
            }
            System.out.println(count);
            String message;
            if(count!=0) {
                message = "client mail already exists.";
                userDetails.setMessage(message);
                return userDetails;
            }
            else {
                String sql1="null";
                PreparedStatement stmt1 = conn.prepareStatement(sql1);
                //set statements for new user
                stmt1.executeUpdate();
                message = "registered successfully";
            }
            userDetails.setMessage(message);
            return userDetails;
        }catch(SQLException e){
            throw new IllegalArgumentException("Database Access Error");
        }
    }
}
