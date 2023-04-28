package com.example.currencyexchange.controller;

import com.example.currencyexchange.helper.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    public ResponseEntity<?> ok(String msg) {
        return ResponseUtils.ok(msg);
    }

    public ResponseEntity<?> error(String msg) {
        return ResponseUtils.error(msg);
    }

    public ResponseEntity<?> data(Object data) {
        return ResponseUtils.data(data);
    }
}
