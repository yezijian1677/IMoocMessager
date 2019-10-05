package net.qiujuer.web.italker.push.bean.db;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户关系表
 * @author augenye
 * @date 2019-10-04 23:28
 */
@Entity
@Table(name = "TB_USER_FOLLOW")
public class UserFollow {
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

    //定义一个发起人，关注某人
    //User 对应多个UserFollow
    //一条记录一定要有一个关注人
    @ManyToOne(optional = false)
    //定义关联的表的字段名为originId，对应的是User表中的id字段
    @JoinColumn(name = "originId")
    private User origin;
    @Column(nullable = false, updatable = false, insertable = false)
    private String originId;

    //定义关注的目标
    //可以被很多人关注
    //多个userFollow -> user
    @ManyToOne(optional = false)
    @JoinColumn(name = "targetId")
    private User target;
    @Column(nullable = false, updatable = false, insertable = false)
    private String targetId;

    //别名， 备注, 可以为空
    @Column
    @JoinColumn(name = "targetId")
    private String alisa;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义为更新时间戳，创建时写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) {
        this.origin = origin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getAlisa() {
        return alisa;
    }

    public void setAlisa(String alisa) {
        this.alisa = alisa;
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
