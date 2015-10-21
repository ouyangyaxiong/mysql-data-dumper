package ren.wenchao.mysql.data.dumper.service;

import org.springframework.stereotype.Service;
import ren.wenchao.mysql.data.dumper.model.DbConf;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * @author rollenholt
 */
@Service
public class ExecutorPageRender {

    @Resource
    private DbConfigLoader dbConfigLoader;

    public Set<String> getConfigedDatabases() {
        Map<String, DbConf> dbConfMap = dbConfigLoader.getDbConfMap();
        return dbConfMap.keySet();
    }
}
