package org.truonghatsts.registration.controller;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.truonghatsts.registration.domain.UserDTO;
import org.truonghatsts.registration.domain.UserEntity;
import org.truonghatsts.registration.domain.UserMapper;
import org.truonghatsts.registration.service.RegistrationException;
import org.truonghatsts.registration.service.UserService;

import javax.validation.Valid;

@Controller
@Log
@AllArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "homepage";
    }

    @GetMapping("/account")
    public String showAccount(ModelMap map) {
        UserEntity userEntity = userService.getCurrentUser();
        UserDTO userDto = userMapper.toUserDto(userEntity);
        map.put("account", userDto);
        return "account";
    }

    @GetMapping("/signup")
    public String showSignupPage(ModelMap map) {
        map.put("account", new UserDTO());
        return "account";
    }

    @PostMapping("/signup")
    public String signup(ModelMap map,
                         @Valid @ModelAttribute("account") UserDTO userDto,
                         BindingResult result) {
        if (result.hasErrors()) {
            log.warning("There are validation errors: " + result.getAllErrors());
            return "account";
        }
        UserEntity newUser = userMapper.toUserEntity(userDto);
        try {
            UserEntity user = userService.merge(newUser);
            map.put("account", user);
            map.put("info", "Your info was created or updated successfully!");
            return "account";
        } catch (RegistrationException e) {
            map.put("error", e.getMessage());
            return "account";
        }
    }
}

