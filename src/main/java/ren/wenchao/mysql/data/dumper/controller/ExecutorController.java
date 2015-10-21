package ren.wenchao.mysql.data.dumper.controller;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ren.wenchao.mysql.data.dumper.model.ExecutorInfo;
import ren.wenchao.mysql.data.dumper.model.Result;
import ren.wenchao.mysql.data.dumper.service.ExecutorComponent;
import ren.wenchao.mysql.data.dumper.service.SqlStatementHandlerManager;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author rollenholt
 */
@Controller
@RequestMapping(value = "/executor")
public class ExecutorController {

    @Resource
    private ExecutorComponent executorComponent;

    @Resource
    private SqlStatementHandlerManager sqlStatementHandlerManager;

    @RequestMapping(value = "/render", method = RequestMethod.GET)
    public String renderDumperPage() {
        return "executor";
    }

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    @ResponseBody
    public Result execute(ExecutorInfo executorInfo) {
        Preconditions.checkNotNull(executorInfo);
//        List<String> messages = sqlStatementHandlerManager.doIntercept(executorInfo.getSqlStatement());
//        if (!CollectionUtils.isEmpty(messages)) {
//            Result<List<String>> result = new Result<>(false, "please check you sql!", messages);
//            return new ModelAndView("executor", "result", result);
//        }
        List<Map<String, Object>> values = executorComponent.doExecute(executorInfo);
        return new Result<>(true, "execute successful", values);
    }
}
