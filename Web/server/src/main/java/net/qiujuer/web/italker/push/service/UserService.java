package net.qiujuer.web.italker.push.service;

import com.google.common.base.Strings;
import net.qiujuer.web.italker.push.bean.api.account.AccountRspModel;
import net.qiujuer.web.italker.push.bean.api.base.ResponseModel;
import net.qiujuer.web.italker.push.bean.api.user.UpdateInfoModel;
import net.qiujuer.web.italker.push.bean.card.UserCard;
import net.qiujuer.web.italker.push.bean.db.User;
import net.qiujuer.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 * @author augenye
 * @date 2019-10-06 15:01
 */
@Path("/user")
public class UserService extends BaseService{

    //用户信息更改接口
    //返回自己的个人信息
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(UpdateInfoModel model) {
        if (!UpdateInfoModel.check(model)) {
            return ResponseModel.buildParameterError();
        }
        //通过token获取信息
        User user = getSelf();
        //更新用户信息
        //填充需要更改的用户信息
        user = model.updateToUser(user);
        //数据库更新
        user = UserFactory.update(user);
        UserCard card = new UserCard(user, true);
        return ResponseModel.buildOk(card);

    }
}
