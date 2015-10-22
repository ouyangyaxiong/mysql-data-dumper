package ren.wenchao.mysql.data.dumper.controller;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ren.wenchao.mysql.data.dumper.model.DumpRequest;
import ren.wenchao.mysql.data.dumper.model.Result;
import ren.wenchao.mysql.data.dumper.service.DumpService;

import javax.annotation.Resource;

/**
 * @author rollenholt
 */
@Controller
@RequestMapping(value = "/dumper")
public class DumpController {

    @Resource
    private DumpService dumpService;

    @RequestMapping(value = "/render")
    public String renderDumper() {
        return "dumper";
    }

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    public Result generate(DumpRequest dumpRequest) {
        Preconditions.checkNotNull(dumpRequest);
        String script = dumpService.generateDumpScript(dumpRequest);
        return new Result<>(true, "generate success.", script);
    }
}
