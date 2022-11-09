package org.sc.backend.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;

public class CommonUtils {
    public static String getUserIdFromAuthorizationToken(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        //String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode payloadJson = null;
        try {
            payloadJson = mapper.readTree(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return payloadJson.get("sub").textValue();
    }
}
