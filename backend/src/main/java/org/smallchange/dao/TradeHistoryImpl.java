package org.smallchange.dao;
import org.smallchange.model.Trade;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TradeHistoryImpl implements TradeHistoryDao {
    private DataSource datasource;


    public TradeHistoryImpl(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public List<Trade> getTradeHistory(String userId) {
        String sql="hello";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, userId);
            ResultSet rs=stmt.executeQuery();
            List<Trade> tradeHistory=new ArrayList<>();
            int count=0;
            while(rs.next()) {
                count++;
                tradeHistory.add(getResult(rs));
            }
            System.out.println(count);
            return tradeHistory;
        }catch(SQLException e){
            throw new IllegalArgumentException("Database Access Error");
        }
    }

   public Trade getResult(ResultSet rs) {
        return null;
//       //set statements for trade history
   }
}
