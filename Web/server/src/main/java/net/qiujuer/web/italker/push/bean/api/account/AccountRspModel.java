package net.qiujuer.web.italker.push.bean.api.account;

import com.google.gson.annotations.Expose;
import net.qiujuer.web.italker.push.bean.card.UserCard;
import net.qiujuer.web.italker.push.bean.db.User;

/**
 * @author augenye
 * @date 2019-10-05 19:25
 */
public class AccountRspModel {

    //用户基本信息
    @Expose
    private UserCard user;
    //当前登陆账号
    @Expose
    private String accout;
    //登陆后获取token
    //通过token获取用户信息
    @Expose
    private String token;
    //是否绑定到了设备的pushId
    @Expose
    private boolean isBind;

    public AccountRspModel(User user) {
        this(user, false);
    }

    public AccountRspModel(User user, boolean isBind) {
        this.user = new UserCard(user);
        this.accout = user.getPhone();
        this.token = user.getToken();
        this.isBind = isBind;
    }

    public UserCard getUser() {
        return user;
    }

    public void setUser(UserCard user) {
        this.user = user;
    }

    public String getAccout() {
        return accout;
    }

    public void setAccout(String accout) {
        this.accout = accout;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }
}
