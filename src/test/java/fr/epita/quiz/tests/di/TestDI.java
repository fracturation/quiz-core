package fr.epita.quiz.tests.di;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class) //dependencies to be updated
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestDI {

    @Inject
    @Named("firstQuery")
    String query;

    @Inject
    @Named("dataSource") //this is only needed if there is any ambiguity
    DataSource data; //class is DriverManagerDataSource but can use the interface instead

    @Test
    public void testFirstIntegration() {
        Assert.assertNotNull(query);
    }

    @Test
    public void testScndIntegration() throws SQLException {
        Connection c = data.getConnection();
        String schema = c.getSchema();
        System.out.println(schema);

        Assert.assertEquals("PUBLIC", schema);
    }
}
