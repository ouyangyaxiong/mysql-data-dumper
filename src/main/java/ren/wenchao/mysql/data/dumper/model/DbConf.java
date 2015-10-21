package ren.wenchao.mysql.data.dumper.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author rollenholt
 */
public class DbConf {

    private String database;

    private String host;

    private String port;

    private String username;

    private String password;

    private DbConf(Builder builder) {
        setDatabase(builder.database);
        setHost(builder.host);
        setPort(builder.port);
        setUsername(builder.username);
        setPassword(builder.password);
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static final class Builder {
        private String database;
        private String host;
        private String port;
        private String username;
        private String password;

        private Builder() {
        }

        public Builder withDatabase(String val) {
            database = val;
            return this;
        }

        public Builder withHost(String val) {
            host = val;
            return this;
        }

        public Builder withPort(String val) {
            port = val;
            return this;
        }

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withPassword(String val) {
            password = val;
            return this;
        }

        public DbConf build() {
            return new DbConf(this);
        }
    }
}
