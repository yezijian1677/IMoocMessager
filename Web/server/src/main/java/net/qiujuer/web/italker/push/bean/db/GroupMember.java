package net.qiujuer.web.italker.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author augenye
 * @date 2019-10-05 11:17
 */
@Entity
@Table(name = "TB_GROUP_MEMBER")
public class GroupMember {

    public static final int PERMISSION_TYPE_NONE = 0;//普通
    public static final int PERMISSION_TYPE_ADMIN = 1;//管理员
    public static final int PERMISSION_TYPE_ADMIN_SU = 100;//群主

    public static final int NOTIFY_LEVEL_INVALID = -1;//默认不接收
    public static final int NOTIFY_LEVEL_NONE = 0;//默认通知
    public static final int NOTIFY_LEVEL_CLOSE = 1;//接受不提醒

    //主键
    @Id
    @PrimaryKeyJoinColumn
    //自增生成为uuid
    @GeneratedValue(generator = "uuid")
    //uuid定义为uuid2，uuid2是常规的uuid toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //不允许更改，不允许为null
    @Column(updatable = false, nullable = false)
    private String id;

    @Column
    private String alisa;

    @Column(nullable = false)
    private int notifyLevel = NOTIFY_LEVEL_NONE;

    @Column(nullable = false)
    private int permissonType = PERMISSION_TYPE_NONE;

    //定义为创建时间戳，创建时写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义为更新时间戳，创建时写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    @JoinColumn(name = "userId")
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;
    @Column(nullable = false, updatable = false, insertable = false)
    private String userId;

    //群信息
    @JoinColumn(name = "groupId")
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Group group;
    @Column(nullable = false, updatable = false, insertable = false)
    private String groupId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlisa() {
        return alisa;
    }

    public void setAlisa(String alisa) {
        this.alisa = alisa;
    }

    public int getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(int notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    public int getPermissonType() {
        return permissonType;
    }

    public void setPermissonType(int permissonType) {
        this.permissonType = permissonType;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
