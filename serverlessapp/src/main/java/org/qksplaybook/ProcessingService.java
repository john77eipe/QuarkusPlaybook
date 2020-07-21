package org.qksplaybook;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;


@ApplicationScoped
public class ProcessingService {

    public OutputObject process(HashMap<String, String> input) {
        OutputObject output = new OutputObject();
        input.put("weight","34kg");
        output.setResult(input);
        return output;
    }
}
