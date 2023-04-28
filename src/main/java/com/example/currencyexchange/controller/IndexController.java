package com.example.currencyexchange.controller;

import com.example.currencyexchange.helper.*;
import com.example.currencyexchange.model.entity.User;
import com.example.currencyexchange.model.json.BankOfTaiwan;
import com.example.currencyexchange.model.mail.RegisterMail;
import com.example.currencyexchange.model.req.LoginVo;
import com.example.currencyexchange.model.req.RegisterVo;
import com.example.currencyexchange.service.impl.IndexService;
import com.example.currencyexchange.service.impl.UserService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Setter;
import org.python.google.common.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Setter(onMethod_ = {@Autowired})
    UserService userService;

    @Setter(onMethod_ = {@Autowired})
    IndexService indexService;

    @Setter(onMethod_ = {@Autowired})
    SendMail sendMail;


    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/rates")
    public ResponseEntity<?> exchangeRate() {
        String rateJson = RedisUtils.getValue(Constant.REDIS_KEY.BANKOFTAIWAN).toString();
        return data(new Gson().fromJson(rateJson, new TypeToken<List<BankOfTaiwan>>() {
        }.getType()));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<?> register(@Valid RegisterVo register, HttpServletRequest request) {
        if (!register.getMail().isBlank()) {
            if (userService.existsMail(register.getMail())) {
                return error("信箱已存在");
            }
            if (register.getMailCode().isBlank() || !new RegisterMail(register.getMail()).getMailCode().equals(register.getMailCode())) {
                return error("驗證碼錯誤");
            }
        } else if (!register.getPhoneNumber().isBlank() && Pattern.matches("^09\\d{8}$", register.getPhoneNumber())) {
            return error("請輸入正確的號碼");
        }

        if (!register.getPassword().equals(register.getConfirmPassword())) {
            return error("密碼不一致");
        }

        indexService.register(register);
        return ok("註冊成功!記得完善個人資料才能發帖");
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> login(@Valid LoginVo loginVo, HttpServletRequest request) throws IOException {
        Optional<User> optionalUser = Optional.empty();
        if (loginVo.getType() == LoginVo.EMAIL_TYPE && !loginVo.getMail().isBlank())
            optionalUser = userService.findByMailAndPassword(loginVo.getMail(), EncryptionUtils.hmacMD5(loginVo.getPassword()));
        else if (loginVo.getType() == LoginVo.PHONE_TYPE && !loginVo.getPhoneNumber().isBlank() && Pattern.matches("^09\\d{8}$", loginVo.getPhoneNumber()))
            optionalUser = userService.findByPhoneAndPassword(loginVo.getPhoneNumber(), EncryptionUtils.hmacMD5(loginVo.getPassword()));


        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return data(new HashMap<String, String>() {{
                put("Authorization", indexService.login(request, user));
            }});
        } else {
            return error("登入失敗");
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/sendMail")
    public ResponseEntity<?> sendMail(@Email @NotBlank(message = "信箱必填") String mail) {
        if (!userService.existsMailToRegister(mail)) {
            sendMail.setEmailFactory(new RegisterMail(mail)).send();
            return ok("發送成功");
        } else {
            return error("信箱已註冊");
        }
    }
}

