package net.qiujuer.web.italker.push.service;

import com.google.common.base.Strings;
import net.qiujuer.web.italker.push.bean.api.account.AccountRspModel;
import net.qiujuer.web.italker.push.bean.api.account.LoginModel;
import net.qiujuer.web.italker.push.bean.api.account.RegisterModel;
import net.qiujuer.web.italker.push.bean.api.base.ResponseModel;
import net.qiujuer.web.italker.push.bean.card.UserCard;
import net.qiujuer.web.italker.push.bean.db.User;
import net.qiujuer.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

// 127.0.0.1/api/account/...
@Path("/account")
public class AccountService extends BaseService {

    @POST
    @Path("/bind/{pushId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //从请求头中获取token字段
    //pushId 从URL地址中获取
    public ResponseModel<AccountRspModel> bind(@HeaderParam("token") String token, @PathParam("pushId") String pushId) {
        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(pushId)) {
            return ResponseModel.buildParameterError();
        }
        //拿取数据
        User user = getSelf();
        return bind(user, pushId);
    }

    /**
     * 绑定token
     * @param self user
     * @param pushId pushId
     * @return
     */
    private ResponseModel<AccountRspModel> bind(User self, String pushId) {
        //进行设备id的绑定操作
        User user = UserFactory.bindPushId(self, pushId);
        if (user == null) {
            //绑定失败
            return ResponseModel.buildServiceError();
        }
        //返回当前账户，并且已经绑定
        AccountRspModel rspModel = new AccountRspModel(user, true);
        return ResponseModel.buildOk(rspModel);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> login(LoginModel model) {
        if (!LoginModel.check(model)) {
            return ResponseModel.buildParameterError();
        }
        User user = UserFactory.login(model.getAccount(), model.getPassword());
        if (user != null) {
            //如果携带pushId
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                //todo
                return bind(user, model.getPushId());
            }
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        } else {
            return ResponseModel.buildLoginError();
        }
    }


    /**
     * 注册
     * @param model model
     * @return model
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> register(RegisterModel model) {
        if (!RegisterModel.check(model)) {
            return ResponseModel.buildParameterError();
        }
        User user = UserFactory.findByPhone(model.getAccount().trim());
        if (user != null) {
            return ResponseModel.buildAccountError();
        }

        user = UserFactory.findByName(model.getName());
        if (user != null) {
            return ResponseModel.buildHaveNameError();
        }

        user = UserFactory.register(model.getAccount(), model.getPassword(), model.getName());
        if (user != null) {
            //绑定pushId
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                //todo
                return bind(user, model.getPushId());
            }

            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        } else {
            return ResponseModel.buildRegisterError();
        }

    }

}
