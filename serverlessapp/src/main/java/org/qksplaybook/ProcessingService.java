package org.qksplaybook;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProcessingService {

    public static final String CAN_ONLY_GREET_NICKNAMES = "Can only greet nicknames";

    public OutputObject process(InputObject input) {
        if (input.getName().equals("Stuart")) {
            throw new IllegalArgumentException(CAN_ONLY_GREET_NICKNAMES);
        }
        String result = input.getGreeting() + " " + input.getName();
        OutputObject output = new OutputObject();
        result="my name SK";
        output.setResult(result);
        return output;
    }
}
