package net.qiujuer.web.italker.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author augenye
 * @date 2019-10-05 15:35
 */
@Entity
@Table(name = "TB_APPLY")
public class Apply {

    public static final int TYPE_ADD_USER = 1;
    public static final int TYPE_ADD_GROUP = 2;

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

    //申请信息做的描述
    //eg：添加好友的验证消息，不允许为空
    @Column(nullable = false)
    private String description;

    //附件，图片或者其他
    @Column(columnDefinition = "TEXT")
    private String attach;

    @Column(nullable = false)
    private int type;

    //添加目标的id if targetId -> user -> userId, Group - groupId
    //不进行强关联
    private String targetId;

    //定义为创建时间戳，创建时写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义为更新时间戳，创建时写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    //申请人
    //一个人可以有很多个申请所以多个apply对应一个人
    @ManyToOne
    @JoinColumn(name = "applicantId")
    private User applicant;
    @Column(updatable = false, insertable = false)
    private String applicantId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
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

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }
}
