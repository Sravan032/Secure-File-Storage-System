package com.sravan.Secure.File.Storage.model;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String role;

    public User(){}

    public User(String username, String password, String role){
        this.username =username;
        this.password=password;
        this.role=role;
    }

    public long getId(long id){
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName(String username){
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public String getRole(String role){
        return role;
    }
    public void setRole(String role){
        this.role=role;
    }
}
