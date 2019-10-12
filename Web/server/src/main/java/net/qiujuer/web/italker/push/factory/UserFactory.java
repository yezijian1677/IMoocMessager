package net.qiujuer.web.italker.push.factory;

import com.google.common.base.Strings;
import net.qiujuer.web.italker.push.bean.db.User;
import net.qiujuer.web.italker.push.bean.db.UserFollow;
import net.qiujuer.web.italker.push.utils.Hib;
import net.qiujuer.web.italker.push.utils.TextUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 */
public class UserFactory {

    public static void main(String[] args) {
        User user = login("1234567", "111111");
        System.out.println(user.getCreateAt());
    }

    //只能查询自己的
    public static User findByToken(String token) {
        return Hib.query(session -> (User)session
                .createQuery("from User where token=:token")
                .setParameter("token", token)
                .uniqueResult());
    }
    /**
     * 根据手机查找
     * @param phone
     * @return
     */
    public static User findByPhone(String phone) {
        return Hib.query(session -> (User)session
                    .createQuery("from User where phone=:phone")
                    .setParameter("phone", phone)
                    .uniqueResult());
    }

    /**
     * 名字查找
     * @param name name
     * @return User
     */
    public static User findByName(String name) {
        return Hib.query(session -> (User)session
                .createQuery("from User where name=:name")
                .setParameter("name", name)
                .uniqueResult());
    }

    /**
     * 给当前账户绑定pushId
     * @param user 当前user
     * @param pushId 自己设备的id
     * @return user
     */
    public static User bindPushId(User user, String pushId) {
        //第一步查询是否有其他账户绑定了此设备
        //取消绑定，避免推送混乱
        Hib.queryOnly(session -> {
            @SuppressWarnings("unchecked")
            List<User> userList = session.createQuery("from User where lower(pushId)=:pushId and id!=:userId")
                    .setParameter("pushId", pushId)
                    .setParameter("userId", user.getId())
                    .list();

            for (User u : userList) {
                u.setPushId(null);
                session.saveOrUpdate(u);
            }
        });
        //当前用户已经存在该pushId
        if (pushId.equalsIgnoreCase(user.getPushId())) {
            return user;
        } else {
            //当前账户之前的设备id，和需要绑定的不同
            //那么需要单点登录，让之前的设备退出账户
            //给之前的设备推送一个退出消息
            if (Strings.isNullOrEmpty(user.getPushId())) {
                //TODO 推送一个退出消息
            }
            //更新新的设备id
            user.setPushId(pushId);
            return update(user);
        }
    }

    public static User login(String account, String password) {
        String accountStr = account.trim();
        String passwordEncode = encodePassword(password);

        User user = Hib.query(session -> (User) session
                .createQuery("from User where phone=:phone and password=:password")
                .setParameter("phone", accountStr)
                .setParameter("password", passwordEncode)
                .uniqueResult());
        //进行token 登陆
        if (user != null) {
            user = login(user);
        }

        return user;
    }

    /**
     * 用户注册
     * @param account 账户
     * @param password 密码
     * @param name 用户名
     * @return user
     */
    public static User register(String account, String password, String name) {
        //去除首尾空格
        account = account.trim();
        password = encodePassword(password);
        User user = createUser(account, password, name);
        if (user != null) {
            user = login(user);
        }
        return user;
    }

    /**
     * 登陆
     * 本质上对token进行操作
     */
    private static User login(User user) {
        //使用一个随机的UUID值充当token
        String newToken = UUID.randomUUID().toString();
        //base64 加密token
        newToken = TextUtil.encodeBase64(newToken);
        user.setToken(newToken);
        return update(user);
    }

    /**
     * 创建一个用户
     * @param account 账号
     * @param password 密码
     * @param name 名字
     * @return user
     */
    private static User createUser(String account, String password, String name){
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        //账户为手机
        user.setPhone(account);
        return Hib.query(session -> {
            session.save(user);
            return user;
        });
    }

    /**
     * 加密后的密码
     * @param password 加密
     * @return 返回加密的密码
     */
    private static String encodePassword(String password) {
        //先非对称md5加密，加盐会更安全，盐也需要存储
        //然后对称base64加密
        password = password.trim();
        password = TextUtil.getMD5(password);
        return TextUtil.encodeBase64(password);
    }

    /**
     * 更新用户信息
     * @param user user
     * @return user
     */
    public static User update(User user) {
        return Hib.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }
}
