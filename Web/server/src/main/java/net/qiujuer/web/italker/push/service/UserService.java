package net.qiujuer.web.italker.push.service;

import com.google.common.base.Strings;
import com.sun.org.apache.regexp.internal.RE;
import net.qiujuer.web.italker.push.bean.api.base.ResponseModel;
import net.qiujuer.web.italker.push.bean.api.user.UpdateInfoModel;
import net.qiujuer.web.italker.push.bean.card.UserCard;
import net.qiujuer.web.italker.push.bean.db.User;
import net.qiujuer.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author augenye
 * @date 2019-10-06 15:01
 */
@Path("/user")
public class UserService extends BaseService {

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

    /**
     * 拉去联系人
     *
     * @return 返回联系人列表
     */
    @GET
    @Path("/contact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<UserCard>> contact() {
        User self = getSelf();

        List<User> users = UserFactory.contact(self);
        //转换为userCard
        List<UserCard> userCards = users.stream()
                .map(user -> new UserCard(user, true))
                .collect(Collectors.toList());

        return ResponseModel.buildOk(userCards);
    }

    //关注双方其实是双方同时关注的操作
    @PUT
    @Path("/follow/{followId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> follow(@PathParam("followId") String followId) {
        User self = getSelf();
        //不能关注自己
        if (self.getId().equalsIgnoreCase(followId) || Strings.isNullOrEmpty(followId)) {
            //返回参数异常
            return ResponseModel.buildParameterError();
        }
        //找到我也关注的人
        User followUser = UserFactory.findById(followId);
        if (followUser == null) {
            //找不到此用户
            return ResponseModel.buildNotFoundUserError(null);
        }

        followUser = UserFactory.follow(self, followUser, null);
        if (followUser == null) {
            //关注失败
            return ResponseModel.buildServiceError();
        }
        //todo 通知我关注的人 我关注了他
        //返回被关注人的信息
        return ResponseModel.buildOk(new UserCard(followUser, true));

    }

    //获取某人的信息
    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> getUser(@PathParam("id") String id) {
        if (Strings.isNullOrEmpty(id)) {
            return ResponseModel.buildParameterError();
        }

        User self = getSelf();
        if (self.getId().equalsIgnoreCase(id)) {
            return ResponseModel.buildOk(new UserCard(self, true));
        }
        User user = UserFactory.findById(id);
        //没找到用户
        if (user == null) {
            return ResponseModel.buildNotFoundUserError(null);
        }
        //如果已经有记录，则已经关注
        boolean isFollow = UserFactory.getUserFollow(self, user) != null;

        return ResponseModel.buildOk(new UserCard(user, isFollow));
    }

    //搜索用户
    @GET
    @Path("/search/{name:(.*)?}")//正则，名字为任意字符，可以为空
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<UserCard>> search(@DefaultValue("") @PathParam("name") String name) {
        User self = getSelf();

        List<User> searchUsers = UserFactory.search(name);
        //查询的人转换为userCard
        //还需要判断查询的人是否已经在我的关注中
        //如果已经关注返回的信息应该是已经关注的
        //取出自己的关注列表
        final List<User> contacts = UserFactory.contact(self);
        //
        List<UserCard> userCards = searchUsers.stream()
                .map(user ->{
                    //联系人的任意匹配，查询是否已经有关注
                    boolean isFollow = user.getId().equalsIgnoreCase(self.getId())
                            || contacts.stream().anyMatch(
                            contactsUser -> contactsUser.getId().equalsIgnoreCase(user.getId())
                    );
                    return new UserCard(user, isFollow);
                })
                .collect(Collectors.toList());

        return ResponseModel.buildOk(userCards);
    }
}
