package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.User;
import com.sergey.zhuravlev.salon.dto.KeyAndPasswordDto;
import com.sergey.zhuravlev.salon.dto.ManagedUserDto;
import com.sergey.zhuravlev.salon.dto.PasswordChangeDto;
import com.sergey.zhuravlev.salon.dto.UserDto;
import com.sergey.zhuravlev.salon.exception.EmailAlreadyUsedException;
import com.sergey.zhuravlev.salon.exception.EmailNotFoundException;
import com.sergey.zhuravlev.salon.exception.InvalidPasswordException;
import com.sergey.zhuravlev.salon.repository.UserRepository;
import com.sergey.zhuravlev.salon.security.SecurityUtils;
import com.sergey.zhuravlev.salon.service.MailService;
import com.sergey.zhuravlev.salon.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final MailService mailService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserDto managedUserDto) {
        if (!checkPasswordLength(managedUserDto.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(managedUserDto, managedUserDto.getPassword());
        mailService.sendActivationEmail(user);
    }

    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }

    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    @GetMapping("/account")
    public UserDto getAccount() {
        return userService.getUserWithAuthorities()
            .map(UserDto::new)
            .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDto userDto) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDto.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(userDto.getEmail(), userDto.getLangKey());
    }

    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
        mailService.sendPasswordResetMail(
            userService.requestPasswordReset(mail)
                .orElseThrow(EmailNotFoundException::new)
        );
    }

    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordDto keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this reset key");
        }
    }

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserDto.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserDto.PASSWORD_MAX_LENGTH;
    }

}
