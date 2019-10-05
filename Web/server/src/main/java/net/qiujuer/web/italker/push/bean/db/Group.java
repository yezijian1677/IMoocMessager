package net.qiujuer.web.italker.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author augenye
 * @date 2019-10-05 10:42
 */
@Entity
@Table(name = "TB_GROUP")
public class Group {

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

    //群名称
    @Column(nullable = false)
    private String name;

    //群描述
    @Column(nullable = false)
    private String description;

    //群头像
    @Column(nullable = false)
    private String picture;

    //定义为创建时间戳，创建时写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义为更新时间戳，创建时写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    //群的创建者
    //急加载，加载群的时候就加载创建者的信息。
    //cascade 联级的级别，所有的更改都将进行关系更新
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ownerId")
    private User owner;
    @Column(nullable = false, updatable = false, insertable = false)
    private String ownerId;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
