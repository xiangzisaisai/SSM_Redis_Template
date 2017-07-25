package education.cs.scu.controller;

import education.cs.scu.entity.User;
import education.cs.scu.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by maicius on 2017/3/31.
 */
@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping(value="/userLogin", method= RequestMethod.POST)
    public User userLogin(HttpServletRequest request,
                             @RequestParam(value="username") String userName,
                             @RequestParam(value="password") String password) throws Exception{
        User user = new User(userName, password);
        System.out.println(userName);
        User loginUser = loginService.doUserLogin(user);
        HttpSession session = request.getSession();
        if(loginUser != null) {
            session.setAttribute("user", loginUser);
            System.out.println("finish:" + loginUser.getNickName());
            return loginUser;
        }else{
            User wrongUser = new User();
            wrongUser.setNickName("该用户不存在");
            session.setAttribute("user", wrongUser);
            return null;
        }
    }

    @RequestMapping(value="/userRegist", method= RequestMethod.GET)
    public int userRegist(HttpServletRequest request,
                           @RequestParam(value="username") String userName,
                           @RequestParam(value="password") String password) throws Exception{
        User user = new User(userName, password);
        System.out.println(userName);
        return loginService.doUserRegist(user);
    }

    @RequestMapping(value="/clickRegist", method= RequestMethod.GET)
    public ModelAndView clickRegist() throws Exception{
        //regist 为jsp文件名
        return new ModelAndView("regist");
    }
}
