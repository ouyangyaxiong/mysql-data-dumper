package ren.wenchao.mysql.data.dumper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author rollenholt
 */
@Controller
public class DumperController {

    @RequestMapping(value = "/dumper", method = RequestMethod.GET)
    public String renderDumperPage(){
        return "dumper";
    }
}
