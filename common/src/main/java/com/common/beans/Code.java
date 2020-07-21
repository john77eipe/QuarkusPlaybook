package com.common.beans;

import java.io.Serializable;

public class Code implements Serializable {
    
    
    private String inputSample;
    private String filename;
    private String code;
    private String id;
    
    public Code() {
    }
    
    public Code(String filename, String code, String id) {
        this.filename = filename;
        this.code = code;
        this.id = id;
    }
    
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
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return "Code [code=" + code + ", filename=" + filename + ", id=" + id + ", inputSample=" + inputSample + "]";
    }

    public String getInputSample() {
        return inputSample;
    }

    public void setInputSample(String inputSample) {
        this.inputSample = inputSample;
    }
}
