package ren.wenchao.mysql.data.dumper.service;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ren.wenchao.mysql.data.dumper.dao.DaoComponent;
import ren.wenchao.mysql.data.dumper.model.ExecutorInfo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author rollenholt
 */
@Service
public class ExecutorComponent {

    @Resource
    private DaoComponent daoComponent;

    private final Logger logger = LoggerFactory.getLogger(ExecutorComponent.class);

    public List<Map<String, Object>> doExecute(ExecutorInfo executorInfo) {
        Preconditions.checkNotNull(executorInfo);
        JdbcTemplate jdbcTemplate = daoComponent.buildJdbcTemplate(executorInfo);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(executorInfo.getSqlStatement());
        logger.info(maps.toString());
        return maps;
    }
}
