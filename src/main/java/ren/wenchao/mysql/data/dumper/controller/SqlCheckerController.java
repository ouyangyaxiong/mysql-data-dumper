package ren.wenchao.mysql.data.dumper.controller;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ren.wenchao.mysql.data.dumper.model.Result;
import ren.wenchao.mysql.data.dumper.service.SqlStatementHandlerManager;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rollenholt
 */
@Controller
public class SqlCheckerController {

    @Resource
    private SqlStatementHandlerManager sqlStatementHandlerManager;

    private final Joiner joiner = Joiner.on("\n");

    private final Logger logger = LoggerFactory.getLogger(SqlCheckerController.class);

    @RequestMapping(value = "/sql/checker", method = RequestMethod.POST)
    @ResponseBody
    public Result checkSql(@RequestBody String sqlStatement) {
        if (Strings.isNullOrEmpty(sqlStatement)) {
            return new Result<>(false, "sql statement can not be null", sqlStatement);
        }

        List<String> messages = null;
        try {
            messages = sqlStatementHandlerManager.doIntercept(sqlStatement);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "system error, please retry or contact system admin", sqlStatement);
        }
        if (!CollectionUtils.isEmpty(messages)) {
            String msg = joiner.join(messages);
            return new Result<>(false, msg, null);
        }
        return new Result<>(true, "check ok", sqlStatement);
    }
}
