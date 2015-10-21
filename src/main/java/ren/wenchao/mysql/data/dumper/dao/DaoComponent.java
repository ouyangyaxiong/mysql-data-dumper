package ren.wenchao.mysql.data.dumper.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ren.wenchao.mysql.data.dumper.model.ExecutorInfo;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @author rollenholt
 */
@Component
public class DaoComponent {

    private DataSource buildDataSource(ExecutorInfo executorInfo) {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        dataSource.setJdbcUrl(buildJdbcUrl(executorInfo.getIp(), executorInfo.getPort(),
                executorInfo.getDatabase()));
        dataSource.setUser(executorInfo.getUserName());
        dataSource.setPassword(executorInfo.getPassword());
        dataSource.setInitialPoolSize(2);
        dataSource.setMinPoolSize(2);
        dataSource.setMaxPoolSize(5);
        dataSource.setMaxIdleTime(600);
        dataSource.setMaxStatements(60);
        dataSource.setAutoCommitOnClose(true);
        return dataSource;
    }

    public JdbcTemplate buildJdbcTemplate(ExecutorInfo executorInfo) {
        DataSource dataSource = buildDataSource(executorInfo);
        return new JdbcTemplate(dataSource);
    }

    private String buildJdbcUrl(String ip, int port, String database) {
        return "jdbc:mysql://" + ip + ":" + port + "/" + database +
                "?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8";
    }
}
