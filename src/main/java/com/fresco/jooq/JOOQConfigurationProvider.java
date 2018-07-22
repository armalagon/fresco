package com.fresco.jooq;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
@ApplicationScoped
public class JOOQConfigurationProvider {

    @Resource
    DataSource dataSource;

    @Produces
    public DSLContext produceDSLContext() {
        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }

}
