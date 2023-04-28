package com.example.currencyexchange.controller;

import com.example.currencyexchange.enums.GenderEnum;
import com.example.currencyexchange.helper.UserInfo;
import com.example.currencyexchange.model.entity.User;
import com.example.currencyexchange.model.req.CompleteInfoVo;
import com.example.currencyexchange.model.resp.PersonalInfoRsp;
import com.example.currencyexchange.service.impl.UserSatisfactionService;
import com.example.currencyexchange.service.impl.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Setter(onMethod_ = {@Autowired})
    public UserService userService;
    @Setter(onMethod_ = {@Autowired})
    public UserSatisfactionService satisfactionService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/completeInfo")
    public ResponseEntity<?> completeInfo(@Valid CompleteInfoVo completeInfoVo) {
        userService.completeInfo(completeInfoVo);
        return ok("保存成功");
    }

    @ResponseBody
    @RequestMapping("personalInfo")
    public ResponseEntity<?> personalInfo() {
        User user = UserInfo.cacheGet();
        Map<String, Object> stringObjectMap = satisfactionService.countAndSatisfy(user.getId());
        return data(PersonalInfoRsp.builder()
                .satisfy(Integer.valueOf(stringObjectMap.get("satisfy").toString()))
                .comments(Integer.valueOf(stringObjectMap.get("commentNum").toString()))
                .realName(UserInfo.hiddenFormat(user.getRealName()))
                .nickName(user.getNickName())
                .selfDesc(user.getSelfDesc())
                .gender(GenderEnum.value(user.getGender()))
                .idCard(UserInfo.hiddenFormat(user.getIdCard()))
                .mail(UserInfo.hiddenFormat(user.getMail()))
                .phoneNumber(UserInfo.hiddenFormat(user.getPhoneNumber()))
                .city(user.getCity())
                .device(user.getDevice()).build());
    }

    @ResponseBody
    @RequestMapping("comments")
    public ResponseEntity<?> comments(Integer page, Integer satisfy) {
        if (page < 0) {
            throw new RuntimeException("參數錯誤");
        }
        return data(satisfactionService.comments(satisfy, page));
    }
}
