package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import model.User;
import org.apache.struts2.StrutsStatics;
import service.AppService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Map;

public class LoginAction extends ActionSupport {
    private int id;

    private String name;
    private String pwd;

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private AppService appService;

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public String login() throws Exception {
        User result = appService.queryUser(name, pwd);
        Map<String, Object> usersession = ActionContext.getContext().getSession();
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
        System.out.println(request.getServletPath());
        if (result != null) {
            setId(result.getId());
            usersession.put("username", name);
            usersession.put("cart", new HashSet<>());
        }
        return SUCCESS;
    }

    public String loginstatus() throws Exception {
        Map<String, Object> usersession = ActionContext.getContext().getSession();
        setName((String) usersession.get("username"));
        if (name != null) {
            User result = appService.getUserByName(name);
            setId(result.getId());
        }
        return "success";
    }
}
