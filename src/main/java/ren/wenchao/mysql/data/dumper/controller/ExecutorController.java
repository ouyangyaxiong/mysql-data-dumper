package ren.wenchao.mysql.data.dumper.controller;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ren.wenchao.mysql.data.dumper.model.ExecutorRequest;
import ren.wenchao.mysql.data.dumper.model.Result;
import ren.wenchao.mysql.data.dumper.service.ExecutorComponent;
import ren.wenchao.mysql.data.dumper.service.ExecutorPageRender;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author rollenholt
 */
@Controller
@RequestMapping(value = "/executor")
public class ExecutorController {

    private final Joiner commaJoiner = Joiner.on(",");

    private final String lineSeparator = System.getProperty("line.separator");

    private final Logger logger = LoggerFactory.getLogger(ExecutorController.class);

    @Resource
    private ExecutorComponent executorComponent;

    @Resource
    private ExecutorPageRender executorPageRender;

    @RequestMapping(value = "/render", method = RequestMethod.GET)
    public String renderDumperPage() {
        return "executor";
    }

    @RequestMapping(value = "/databases", method = RequestMethod.GET)
    @ResponseBody
    public Set<String> getConfigedDatabases() {
        return executorPageRender.getConfigedDatabases();
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Result execute(ExecutorRequest executorRequest) {
        Result msg = check(executorRequest);
        if (msg != null) return msg;

        try {
            List<Map<String, Object>> values = executorComponent.doExecute(executorRequest);
            return new Result<>(true, "execute successful", values);
        } catch (Exception e) {
            return new Result<>(false, e.getMessage(), null);
        }
    }

    @RequestMapping(value = "/downloadCsv", method = RequestMethod.POST)
    public void downloadCsv(ExecutorRequest executorRequest, HttpServletResponse response) {
        response.reset();
        response.setContentType("text/csv;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"data.csv\"");

        try {
            List<Map<String, Object>> values = executorComponent.doExecute(executorRequest);
            StringBuilder buffer = new StringBuilder();
            if (!values.isEmpty()) {
                Set<String> titles = values.get(0).keySet();
                buffer.append(commaJoiner.join(titles));
                for (Map<String, Object> map : values) {
                    buffer.append(lineSeparator);
                    buffer.append(commaJoiner.join(map.values()));
                }
                try (InputStream in = new ByteArrayInputStream(buffer.toString().getBytes("UTF-8"));
                     ServletOutputStream outputStream = response.getOutputStream();) {
                    IOUtils.copy(in, outputStream);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Result check(ExecutorRequest executorRequest) {
        Preconditions.checkNotNull(executorRequest);
        if (Strings.isNullOrEmpty(executorRequest.getDatabase())) {
            return new Result<>(false, "database can not be null", executorRequest.getDatabase());
        }

        if (Strings.isNullOrEmpty(executorRequest.getSqlStatement())) {
            return new Result<>(false, "sql statement can not be null", executorRequest.getSqlStatement());
        }
        return null;
    }
}
