package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

public class DatabaseConnector {

    // This makes sure we connect our Database.

    private SQLServerDataSource dataSource = new SQLServerDataSource();

    public DatabaseConnector() {
        dataSource.setDatabaseName("MovieCollectionSystem");
        dataSource.setUser("CSe21B_16");
        dataSource.setPassword("CSe21B_16");
        dataSource.setPortNumber(1433);
        dataSource.setServerName("10.176.111.31");
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }
}