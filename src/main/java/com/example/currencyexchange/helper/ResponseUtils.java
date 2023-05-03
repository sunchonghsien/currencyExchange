package com.example.currencyexchange.helper;

import com.alibaba.fastjson2.JSONObject;
import com.example.currencyexchange.model.resp.Message;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ResponseUtils {

    public static ResponseEntity<?> ok(String msg) {
        return response(HttpStatus.valueOf(Constant.RESPONSE_CODE.ok), new HttpHeaders(), Message.builder().code(true).msg(msg).data(new ArrayList<>()).build());
    }

    public static ResponseEntity<?> error(String msg) {
        return response(HttpStatus.valueOf(Constant.RESPONSE_CODE.ok), new HttpHeaders(), Message.builder().code(false).msg(msg).data(new ArrayList<>()).build());
    }

    public static ResponseEntity<?> data(Object data) {
        return response(HttpStatus.valueOf(Constant.RESPONSE_CODE.ok), new HttpHeaders(), Message.builder().code(true).msg(Constant.RESPONSE_MSG.ok).data(data).build());
    }

    public static ResponseEntity<?> printImage(Object data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return response(HttpStatus.valueOf(Constant.RESPONSE_CODE.ok), headers, data);
    }

    public static ResponseEntity<?> data(Object data, boolean isSuccess) {
        return response(HttpStatus.valueOf(Constant.RESPONSE_CODE.ok), new HttpHeaders(), Message.builder().code(isSuccess).msg(Constant.RESPONSE_MSG.error).data(data).build());
    }

    public static void writeError(HttpServletResponse response, String msg) throws IOException {
        response.setHeader("Content-Type", "application/json; charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(ResponseEntity.status(200).body(Message.builder().code(false).msg(msg).data(new ArrayList<>()).build()).getBody()));
    }

    private static ResponseEntity<?> response(HttpStatus statusCode, HttpHeaders headers, Object data) {
        return new ResponseEntity<>(data, headers, statusCode);
    }
}
