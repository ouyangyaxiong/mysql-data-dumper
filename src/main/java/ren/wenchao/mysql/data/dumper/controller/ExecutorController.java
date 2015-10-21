package ren.wenchao.mysql.data.dumper.controller;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ren.wenchao.mysql.data.dumper.model.ExecutorRequest;
import ren.wenchao.mysql.data.dumper.model.Result;
import ren.wenchao.mysql.data.dumper.service.ExecutorComponent;
import ren.wenchao.mysql.data.dumper.service.ExecutorPageRender;
import ren.wenchao.mysql.data.dumper.service.SqlStatementHandlerManager;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author rollenholt
 */
@Controller
@RequestMapping(value = "/executor")
public class ExecutorController {

    private final Joiner joiner = Joiner.on("\n");

    @Resource
    private ExecutorComponent executorComponent;

    @Resource
    private ExecutorPageRender executorPageRender;

    @Resource
    private SqlStatementHandlerManager sqlStatementHandlerManager;

    @RequestMapping(value = "/render", method = RequestMethod.GET)
    public String renderDumperPage() {
        return "executor";
    }

    @RequestMapping(value = "/databases", method = RequestMethod.GET)
    @ResponseBody
    public Set<String> getConfigedDatabases() {
        return executorPageRender.getConfigedDatabases();
    }

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    @ResponseBody
    public Result execute(ExecutorRequest executorRequest) {
        Preconditions.checkNotNull(executorRequest);
        if (Strings.isNullOrEmpty(executorRequest.getDatabase())) {
            return new Result<>(false, "database can not be null", executorRequest.getDatabase());
        }

        if (Strings.isNullOrEmpty(executorRequest.getSqlStatement())) {
            return new Result<>(false, "sql statement can not be null", executorRequest.getSqlStatement());
        }
        List<String> messages = sqlStatementHandlerManager.doIntercept(executorRequest.getSqlStatement());
        if (!CollectionUtils.isEmpty(messages)) {
            String msg = joiner.join(messages);
            return new Result<>(false, msg, null);
        }

        try {
            List<Map<String, Object>> values = executorComponent.doExecute(executorRequest);
            return new Result<>(true, "execute successful", values);
        } catch (Exception e) {
            return new Result<>(false, e.getMessage(), null);
        }
    }
}
