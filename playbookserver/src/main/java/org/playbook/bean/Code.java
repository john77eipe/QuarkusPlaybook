package org.playbook.bean;

public class Code {

    private String filename;
    private String code;

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
    
}