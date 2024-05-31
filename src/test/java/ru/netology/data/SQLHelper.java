package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {
    }

    //Подключение к БД
    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    //получить код верификации
    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var conn = getConn();
        return QUERY_RUNNER.query(conn, codeSQL, new BeanHandler<>(DataHelper.VerificationCode.class));
    }

    //Очищает БД
    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "DELETE FROM auth_codes");
        QUERY_RUNNER.execute(connection, "DELETE FROM card_transactions");
        QUERY_RUNNER.execute(connection, "DELETE FROM cards");
        QUERY_RUNNER.execute(connection, "DELETE FROM users");
    }

    //Очищает таблицу auth_codes
    @SneakyThrows
    public static void cleanAuthCodes() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "DELETE FROM auth_codes");
    }
}