package ren.wenchao.mysql.data.dumper.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ren.wenchao.mysql.data.dumper.model.DbConf;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Charsets.UTF_8;

/**
 * @author rollenholt
 */
@Component
public class DbConfigLoader {

    private final String dbConfigFileName = "db-conf";

    private int dbConfigFileLineItemCount = 5;

    private Map<String, DbConf> dbConfMap = Maps.newHashMap();

    private final Logger logger = LoggerFactory.getLogger(DbConfigLoader.class);

    @PostConstruct
    public void loadDbConfig() throws IOException {
        String configFilePath = this.getClass().getResource("/").getPath().concat(dbConfigFileName);

        LineProcessor<Map<String, DbConf>> lineProcessor = new LineProcessor<Map<String, DbConf>>() {
            Map<String, DbConf> dbConfMap = Maps.newHashMap();

            @Override
            public boolean processLine(String line) throws IOException {
                if (line.startsWith("#") || Strings.isNullOrEmpty(line)) {
                    return true;
                }
                List<String> items = Lists.newArrayList(line.split("\\s+"));
                if (items.size() != dbConfigFileLineItemCount) {
                    logger.error("parse db config file fail, please check dbConf file");
                    throw new RuntimeException("parse db config file fail, please check dbConf file");
                }
                String password;
                if(items.get(4).equals("*")){
                    password = null;
                } else {
                    password = items.get(4);
                }
                DbConf dbConf = DbConf.newBuilder()
                        .withDatabase(items.get(0))
                        .withHost(items.get(1))
                        .withPort(items.get(2))
                        .withUsername(items.get(3))
                        .withPassword(password)
                        .build();
                dbConfMap.put(items.get(0), dbConf);
                return true;
            }

            @Override
            public Map<String, DbConf> getResult() {
                return dbConfMap;
            }
        };


        dbConfMap = Files.readLines(new File(configFilePath), UTF_8, lineProcessor);
    }

    public DbConf getByDatabase(String database) {
        DbConf dbConf = dbConfMap.get(database);
        if (dbConf == null) {
            String message = String.format("根据数据库名称:[%s]没有找到数据库配置", database);
            throw new RuntimeException(message);
        }
        return dbConf;
    }

    public Map<String, DbConf> getDbConfMap() {
        return dbConfMap;
    }
}
