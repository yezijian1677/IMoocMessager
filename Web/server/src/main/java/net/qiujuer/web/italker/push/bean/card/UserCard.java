package net.qiujuer.web.italker.push.bean.card;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.qiujuer.web.italker.push.bean.db.User;
import net.qiujuer.web.italker.push.utils.Hib;

import java.time.LocalDateTime;

/**
 * @author augenye
 * @date 2019-10-05 16:32
 */
public class UserCard {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    @SerializedName("account")
    private String phone;
    @Expose
    private String portrait;
    @Expose
    private String description;
    @Expose
    private int sex;
    //用户信息最后的更新时间
    @Expose
    private LocalDateTime modifyAt;
    //用户关注人的数量
    @Expose
    private int follows;
    //用户粉丝数量
    @Expose
    private int following;
    //我与当前用户的关注状态
    @Expose
    private boolean isFollow;

    public UserCard() {
    }

    public UserCard(final User user) {
        this(user, false);
    }

    public UserCard(User user, boolean isFollow) {
        this.isFollow = isFollow;

        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.portrait = user.getPortrait();
        this.description = user.getDescription();
        this.sex = user.getSex();
        this.modifyAt = user.getUpdateAt();

        Hib.queryOnly(session -> {
            session.load(user, user.getId());
            follows = user.getFollowers().size();
            following = user.getFollowing().size();
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public LocalDateTime getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(LocalDateTime modifyAt) {
        this.modifyAt = modifyAt;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public boolean getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }
}
