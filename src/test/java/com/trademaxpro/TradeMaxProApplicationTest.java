package com.trademaxpro;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
        "spring.data.mongodb.uri=mongodb://localhost:27017/test-db",
        "spring.data.mongodb.auto-index-creation=false"
})
@ActiveProfiles("test")
class TradeMaxProApplicationTest {

    @Test
    void contextLoads() {
        // Application context should load successfully in test mode
    }
}
