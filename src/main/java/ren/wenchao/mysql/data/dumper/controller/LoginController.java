package ren.wenchao.mysql.data.dumper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ren.wenchao.mysql.data.dumper.model.User;

/**
 * @author rollenholt
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(User user){
        if ("root".equalsIgnoreCase(user.getName()) && "root".equalsIgnoreCase(user.getPassword())) {
            return new ModelAndView("dumper", "user", user);
        }
        return new ModelAndView("/");
    }

}
