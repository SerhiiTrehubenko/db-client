package test;

import com.tsa.dbclient.domain.services.ResultViewerImpl;
import com.tsa.dbclient.domain.services.ResultWriterImpl;
import com.tsa.dbclient.domain.util.DbConnector;
import com.tsa.dbclient.domain.util.PropertyParser;
import com.tsa.dbclient.domain.util.QueryProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DbClientTest {

    private final String query = "SELECT * FROM user u";
    private final String query2 = "SELECT u.user_name, u.user_salary FROM user u";

    @DisplayName("class: PropertyParser, method: getProperties()")
    @Test
    void testMain() {
        Properties properties = PropertyParser.getProperties();
        assertEquals("{hostName=localhost, password=password, port=3306, dbName=db_jpwh, userName=TSA}",
                properties.toString());
    }

    @DisplayName("class: PropertyParser, method: getProperties(); Default properties")
    @Test
    public void testGetPropertiesDefault() {
        try (Connection connection = DbConnector.getMySqlConnection(PropertyParser.getProperties())) {
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @DisplayName("class: QueryProcessor, method: select();")
    @Test
    void testSelect() {
        List<?> list = QueryProcessor.select(DbConnector.getMySqlConnection(PropertyParser.getProperties()),
                query);
        assertEquals(5, list.size());
    }
    @DisplayName("class: ResultViewerImpl, method: view();")
    @Test
    void testView() {
        try (Connection connection = DbConnector.getMySqlConnection(PropertyParser.getProperties())) {
            List<?> list = QueryProcessor.select(connection, query2);
            var viewer = new ResultViewerImpl(list);
            viewer.view();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @DisplayName("class: ResultWriterImpl, method: write();")
    @Test
    void testWrite() {
        try (Connection connection = DbConnector.getMySqlConnection(PropertyParser.getProperties())) {
            List<?> list = QueryProcessor.select(connection, query2);
            var writer = new ResultWriterImpl(Path.of("").toAbsolutePath().toString());
            writer.write(query2, list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}