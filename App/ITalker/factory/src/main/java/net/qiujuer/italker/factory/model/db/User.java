package net.qiujuer.italker.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import net.qiujuer.italker.common.factory.model.Author;

import java.util.Date;

@Table(database = AppDatabase.class)
public class User extends BaseModel implements Author {

    public static final int SEX_MAN = 1;
    public static final int SEX_WOMEN = 2;

    @PrimaryKey
    private String id;
    @Column
    private String name;
    @Column
    private String account;
    @Column
    private String portrait;
    @Column
    private String description;
    @Column
    private int sex;

    //备注信息
    @Column
    private String alias;
    //用户信息最后的更新时间
    @Column
    private Date modifyAt;
    //用户关注人的数量
    @Column
    private int follows;
    //用户粉丝数量
    @Column
    private int following;
    //我与当前用户的关注状态
    @Column
    private boolean isFollow;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
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

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}
