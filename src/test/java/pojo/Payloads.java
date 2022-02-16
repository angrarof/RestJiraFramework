package pojo;

import java.time.LocalDateTime;

public class Payloads {
    public static String addCommentPayload(String comment){
        return "{\n" +
                "    \"body\":\""+comment+ LocalDateTime.now() +"\"\n" +
                "}";
    }
}
