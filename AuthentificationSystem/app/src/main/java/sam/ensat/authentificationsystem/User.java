package sam.ensat.authentificationsystem;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private String name ,sexe,email,mobile,password;
    Date dop;

    public User() {
    }

    public User(String name, String email, String mobile, String password) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public User(String name, String mobile, String sexe, Date dop,String email) {
        this.name = name;
        this.sexe = sexe;
        this.mobile = mobile;
        this.dop = dop;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Date getDop() {
        return dop;
    }

    public void setDop(Date dop) {
        this.dop = dop;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
