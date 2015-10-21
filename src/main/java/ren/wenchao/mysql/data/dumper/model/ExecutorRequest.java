package ren.wenchao.mysql.data.dumper.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author rollenholt
 */
public class ExecutorRequest implements Serializable {

    private String database;

    private String sqlStatement;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getSqlStatement() {
        return sqlStatement;
    }

    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
