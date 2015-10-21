package ren.wenchao.mysql.data.dumper.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author rollenholt
 */
public class ExecutorInfo implements Serializable{

    private String ip;

    private int port;

    private String userName;

    private String password;

    private String database;

    private String sqlStatement;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
