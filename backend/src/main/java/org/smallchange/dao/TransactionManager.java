package org.smallchange.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
public class TransactionManager {
    private DataSource dataSource;
    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void startTransaction() {
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DatabaseException("Unable to begin a transaction", e);
        }
    }
    public void rollbackTransaction() {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            connection.rollback();
        } catch (SQLException e) {
            throw new DatabaseException("Unable to rollback transaction", e);
        }
    }
    public void commitTransaction() {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseException("Unable to rollback transaction", e);
        }
    }
}
