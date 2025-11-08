package cz.cvut.fel.sit.util;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderNumberGenerator {

    private final JdbcTemplate jdbcTemplate;

    private static final String ORDER_NUMBER_PREFIX = "ORD-";

    @PostConstruct
    public void initSequenceIfMissing() {
        String sql = """
            DO $$ BEGIN
                IF NOT EXISTS (
                    SELECT 1 FROM pg_class WHERE relkind = 'S' AND relname = 'order_number_seq'
                ) THEN
                    CREATE SEQUENCE order_number_seq
                    START WITH 100000
                    INCREMENT BY 1;
                END IF;
            END $$;
        """;
        jdbcTemplate.execute(sql);
    }

    public String generateOrderNumber() {
        Long nextValue = jdbcTemplate.queryForObject(
                "SELECT nextval('order_number_seq')", Long.class
        );
        if (nextValue == null) {
            throw new IllegalStateException("Failed to retrieve next order number from sequence");
        }
        return ORDER_NUMBER_PREFIX + nextValue;
    }
}
