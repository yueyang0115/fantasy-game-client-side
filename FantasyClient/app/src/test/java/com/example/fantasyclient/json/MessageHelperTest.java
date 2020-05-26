package com.example.fantasyclient.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageHelperTest {
    @Test
    public void test_MessageHelper() {
        testSerialize();
        testDeserialize();
    }

    private void testSerialize(){
        String message = MessageHelper.serialize(new MessagesC2S(new LoginRequestMessage("hm171","1234")));
        assertEquals(message,
                "{\"loginRequestMessage\":{\"username\":\"hm171\",\"password\":\"1234\"}," +
                        "\"signUpRequestMessage\":null," +
                        "\"positionRequestMessage\":null}");
    }

    private void testDeserialize(){
        MessagesS2C m = new MessagesS2C();
        LoginResultMessage lm = new LoginResultMessage();
        lm.setStatus("success");
        lm.setError_msg(null);
        m.setLoginResultMessage(lm);
        ObjectMapper objectMapper = new ObjectMapper();
        String result = null;
        try {
            result = objectMapper.writeValueAsString(m);
            assertEquals(result,
                    "{\"positionResultMessage\":null," +
                            "\"loginResultMessage\":{\"wid\":0,\"error_msg\":null,\"status\":\"success\"}," +
                            "\"signUpResultMessage\":null}");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        MessagesS2C m1 = MessageHelper.deserialize(result);
        assertEquals(m1.getLoginResultMessage().getStatus(),"success");
    }
}