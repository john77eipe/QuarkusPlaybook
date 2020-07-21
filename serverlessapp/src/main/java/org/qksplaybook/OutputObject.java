package org.qksplaybook;

import java.util.Map;

public class OutputObject {

    private Map<String, String> result;

    private String requestId;

    public Map<String, String> getResult() {
        return result;
    }

    public String getRequestId() {
        return requestId;
    }

    public OutputObject setResult(Map<String, String> result) {
        this.result = result;
        return this;
    }

    public OutputObject setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }
}
