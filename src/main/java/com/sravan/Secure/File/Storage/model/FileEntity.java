package com.sravan.Secure.File.Storage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String filename;
    private String filepath;
    private long userid;

    public FileEntity(){}

    public FileEntity(long id, String filename, String filepath,long userid){
        this.id = id;
        this.filename = filename;
        this.filepath = filepath;
        this.userid = userid;
    }

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getFilename(){
        return filename;
    }
    public void setFilename(String filename){
        this.filename= filename;
    }
    public String getFilepath(){
        return filepath;
    }
    public void setFilepath(String filepath){
        this.filepath = filepath;
    }
    public long getUserid(){
        return userid;
    }
    public void setUserid(long userid){
        this.userid = userid;
    }
}
