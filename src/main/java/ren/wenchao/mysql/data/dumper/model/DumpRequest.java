package ren.wenchao.mysql.data.dumper.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author rollenholt
 */
public class DumpRequest implements Serializable{

    private String sourceDatabase;

    private String tableNames;

    private String destinationDatabase;

    private String destinationHost;

    private String destinationPort;

    private String destinationDatabaseUser;

    private String destinationDatabasePassword;

    public String getSourceDatabase() {
        return sourceDatabase;
    }

    public void setSourceDatabase(String sourceDatabase) {
        this.sourceDatabase = sourceDatabase;
    }

    public String getTableNames() {
        return tableNames;
    }

    public void setTableNames(String tableNames) {
        this.tableNames = tableNames;
    }

    public String getDestinationDatabase() {
        return destinationDatabase;
    }

    public void setDestinationDatabase(String destinationDatabase) {
        this.destinationDatabase = destinationDatabase;
    }

    public String getDestinationHost() {
        return destinationHost;
    }

    public void setDestinationHost(String destinationHost) {
        this.destinationHost = destinationHost;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public String getDestinationDatabaseUser() {
        return destinationDatabaseUser;
    }

    public void setDestinationDatabaseUser(String destinationDatabaseUser) {
        this.destinationDatabaseUser = destinationDatabaseUser;
    }

    public String getDestinationDatabasePassword() {
        return destinationDatabasePassword;
    }

    public void setDestinationDatabasePassword(String destinationDatabasePassword) {
        this.destinationDatabasePassword = destinationDatabasePassword;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
