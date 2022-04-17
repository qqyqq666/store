package com.example.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class StoreApplicationTests {
    @Autowired
    DataSource dataSource;
    @Test
    void contextLoads() {
    }
    @Test
    /**
     * 数据库连接池
     * 1.DBCP
     * 2.C3P0
     * 3.HikariProxyConnectio（Springboot默认连接池，世界最快的）底层还是C3P0
     * HikariProxyConnection@94354228 wrapping com.mysql.cj.jdbc.ConnectionImpl@1b6924cb
     */
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
}
