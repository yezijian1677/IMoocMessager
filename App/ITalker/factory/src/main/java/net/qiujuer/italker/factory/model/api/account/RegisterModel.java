package net.qiujuer.italker.factory.model.api.account;

public class RegisterModel {
    private String phone;
    private String name;
    private String password;
    private String pushId;


    public RegisterModel(String phone, String password, String name) {
        this(phone, name, password, null);
    }

    public RegisterModel(String phone, String password, String name, String pushId) {
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.pushId = pushId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", pushId='" + pushId + '\'' +
                '}';
    }
}
