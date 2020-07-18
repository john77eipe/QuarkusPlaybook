package org.playbook.bean;

public class Code {

    private String filename;
    private String code;
    private String id;
    
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Code [code=" + code + ", filename=" + filename + "]";
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
}