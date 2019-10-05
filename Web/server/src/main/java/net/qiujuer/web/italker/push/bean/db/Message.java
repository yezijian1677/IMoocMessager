package net.qiujuer.web.italker.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author augenye
 * @date 2019-10-05 10:11
 */
@Entity
@Table(name = "TB_MESSAGE")
public class Message {
    public static final int TYPE_STR = 1; //字符串
    public static final int TYPE_PIC = 2; //图片
    public static final int TYPE_FILE = 3; //文件
    public static final int TYPE_AUDIO = 4; //语音类型
    //主键
    @Id
    @PrimaryKeyJoinColumn
    //自增生成为uuid
    //@GeneratedValue(generator = "uuid")
    //uuid定义为uuid2，uuid2是常规的uuid toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //不允许更改，不允许为null
    @Column(updatable = false, nullable = false)
    private String id;

    //内容不允许为空， 类型为text
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column
    private String attach;

    @Column
    private int type;

    //多个消息对应一个User
    @JoinColumn(name = "senderId")
    @ManyToOne(optional = false)
    private User sender;
    @Column(nullable = false, updatable = false, insertable = false)
    private String senderId;

    @ManyToOne
    @JoinColumn(name = "receiverId")
    private User Receiver;
    @Column(updatable = false, insertable = false)
    private String receiverId;

    //群和消息的关系
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;
    @Column(updatable = false, insertable = false)
    private String groupId;

    //定义为创建时间戳，创建时写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义为更新时间戳，创建时写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public User getReceiver() {
        return Receiver;
    }

    public void setReceiver(User receiver) {
        Receiver = receiver;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
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
