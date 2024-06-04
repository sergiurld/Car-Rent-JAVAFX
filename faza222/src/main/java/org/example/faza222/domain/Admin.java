package org.example.faza222.domain;

import java.util.Objects;

public class Admin extends Entity<Long> {
    private String username;
    private String password;
    private String uniqueCode;
    private String cnp;
    private String name;
    private String address;
    private String phone;

    public Admin(String username, String password, String uniqueCode, String cnp, String name, String address, String phone) {
        this.username = username;
        this.password = password;
        this.uniqueCode = uniqueCode;
        this.cnp = cnp;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", cnp='" + cnp + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Admin admin = (Admin) o;
        return Objects.equals(uniqueCode, admin.uniqueCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username);
    }

}
