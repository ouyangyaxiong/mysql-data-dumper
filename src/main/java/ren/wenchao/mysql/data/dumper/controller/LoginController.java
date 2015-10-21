package ren.wenchao.mysql.data.dumper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ren.wenchao.mysql.data.dumper.model.User;

/**
 * @author rollenholt
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user) {
        if ("root".equalsIgnoreCase(user.getName()) && "root".equalsIgnoreCase(user.getPassword())) {
            return "executor";
        }
        return "/";
    }

}
