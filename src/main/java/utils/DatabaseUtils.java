package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DatabaseUtils {

    private static final String USER_DB = "postgres";
    private static final String USER_PWD = "";
    private static final String URL = "jdbc:postgresql://localhost:5433/weather";
    private static final String DRIVER = "org.postgresql.Driver";
    private final static Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);

    public static Connection getConnection(){
        Connection connection=null;
        try {
            Class.forName(DRIVER);//динамическая загрузка класса
            connection= DriverManager.getConnection(URL,USER_DB,USER_PWD);
            logger.info("Connection was created: "+connection);
        } catch (SQLException | ClassNotFoundException throwables) {
            logger.error("Error occurred during connection creating");
            throwables.printStackTrace();
        }
        return connection;
    }

    public static void releaseConnection(Connection connection){
        try {
            if(connection!=null){
                connection.close();
                logger.info("Connection: "+connection +" was successfully closed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void releaseStatement(Statement statement){
        if (statement!=null){
            try {
                statement.close();
                logger.info("Statement was closed manually");
            } catch (SQLException e) {
                logger.error("Statement was not closed" +e);
            }
        }else {
            logger.info("Statement is null");
        }

    }


}
