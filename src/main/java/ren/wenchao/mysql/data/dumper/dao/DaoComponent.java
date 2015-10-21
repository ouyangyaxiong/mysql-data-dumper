package ren.wenchao.mysql.data.dumper.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ren.wenchao.mysql.data.dumper.model.DbConf;
import ren.wenchao.mysql.data.dumper.model.ExecutorRequest;
import ren.wenchao.mysql.data.dumper.service.DbConfigLoader;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @author rollenholt
 */
@Component
public class DaoComponent {

    @Resource
    private DbConfigLoader dbConfigLoader;

    private DataSource buildDataSource(ExecutorRequest executorRequest) {

        String database = executorRequest.getDatabase();
        DbConf dbConf = dbConfigLoader.getByDatabase(database);

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        dataSource.setJdbcUrl(buildJdbcUrl(dbConf.getHost(), dbConf.getPort(),
                executorRequest.getDatabase()));
        dataSource.setUser(dbConf.getUsername());
        dataSource.setPassword(dbConf.getPassword());
        dataSource.setInitialPoolSize(2);
        dataSource.setMinPoolSize(2);
        dataSource.setMaxPoolSize(5);
        dataSource.setMaxIdleTime(600);
        dataSource.setMaxStatements(60);
        dataSource.setAutoCommitOnClose(true);
        return dataSource;
    }

    public JdbcTemplate buildJdbcTemplate(ExecutorRequest executorRequest) {
        DataSource dataSource = buildDataSource(executorRequest);
        return new JdbcTemplate(dataSource);
    }

    private String buildJdbcUrl(String ip, String port, String database) {
        return "jdbc:mysql://" + ip + ":" + port + "/" + database +
                "?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8";
    }
}
