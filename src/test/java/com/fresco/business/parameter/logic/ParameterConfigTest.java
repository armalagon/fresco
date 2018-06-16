package com.fresco.business.parameter.logic;

import static com.fresco.business.jooq.public_.Tables.PARAMETER;
import static com.fresco.business.jooq.public_.Tables.PARAMETER_CONSTRAINT;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.Test;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class ParameterConfigTest {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/fresco?ApplicationName=IntegrationTest";
    private static final String JDBC_USER = "fresco";
    private static final String JDBC_PASSWORD = "fresco";

    @Test
    public void testParameterConfig() throws SQLException {
        try (Connection cnn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            Result<?> query = DSL.using(cnn)
                    .select(
                            PARAMETER.ID,
                            PARAMETER.CODE,
                            PARAMETER.DATA_TYPE_ENUM,
                            PARAMETER.UNIT_OF_MEASUREMENT_ENUM,
                            PARAMETER.VALUE,
                            PARAMETER_CONSTRAINT.asterisk()
                    )
                    .from(PARAMETER)
                    .join(PARAMETER_CONSTRAINT)
                    .on(PARAMETER.ID.eq(PARAMETER_CONSTRAINT.ID))
                    .orderBy(PARAMETER.ID.asc())
                    .fetch();

            System.out.println(">>>>>>>>>>>>>> " + query.size());
            query.getValues(PARAMETER.CODE).stream()
                    .forEach(code -> System.out.println(">>>>>>>>>>>>>> " + code));
        }
    }

}
