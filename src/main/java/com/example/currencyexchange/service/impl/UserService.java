package com.example.currencyexchange.service.impl;

import com.example.currencyexchange.dao.repository.UserRepository;
import com.example.currencyexchange.helper.FileUtils;
import com.example.currencyexchange.helper.UserInfo;
import com.example.currencyexchange.model.entity.User;
import com.example.currencyexchange.model.req.CompleteInfoVo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.Optional;

@Service
public class UserService {

    @Setter(onMethod_ = {@Autowired})
    UserRepository userRepository;

    public synchronized boolean existsMailToRegister(String mail) {
        return userRepository.countByMail(mail) > 0;
    }

    public boolean existsMail(String mail) {
        return userRepository.countByMail(mail) > 0;
    }

    public Optional<User> findByMailAndPassword(String mail, String password) {
        return userRepository.findByMailAndPassword(mail, password);
    }

    public Optional<User> findByPhoneAndPassword(String number, String password) {
        return userRepository.findByPhoneNumberAndPassword(number, password);
    }

    public void completeInfo(CompleteInfoVo completeInfoVo) {
        User user = UserInfo.cacheGet();
        if (user.getRealName() == null)
            user.setRealName(completeInfoVo.getRealName());
        if (completeInfoVo.getNickName() != null)
            user.setNickName(completeInfoVo.getNickName());
        if (user.getIdCard() == null) {
            user.setIdCard(completeInfoVo.getIdCard());
        }

        user.setAge(completeInfoVo.getAge());
        user.setGender(completeInfoVo.getGender());
        user.setSelfDesc(completeInfoVo.getSelfDesc());
        save(user);
    }

    public void saveImage(MultipartFile file, String directory) throws Exception {
        User user = UserInfo.cacheGet();
        Method setMethod = user.getClass().getDeclaredMethod("set" + StringUtils.capitalize(directory), String.class);
        Method getMethod = user.getClass().getDeclaredMethod("get" + StringUtils.capitalize(directory));
        Object decode = getMethod.invoke(user);
        String saveValue = FileUtils.delAndSave(file, directory, decode);
        setMethod.invoke(user, saveValue);
        save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void save(User user) {
        userRepository.save(user);
        UserInfo.cacheUpdate(user);
    }
}
