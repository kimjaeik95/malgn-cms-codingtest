package com.malgn.malgncms.users.controller;

import com.malgn.malgncms.users.dto.UserRequest;
import com.malgn.malgncms.users.dto.UserResponse;
import com.malgn.malgncms.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.malgn.malgncms.users.controller
 * fileName       : UserController
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.signUp(userRequest);
        return ResponseEntity.ok().body(userResponse);
    }

    @PostMapping("/admin/signup")
    public ResponseEntity<UserResponse> createAdmin(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.signUpAdmin(userRequest);
        return ResponseEntity.ok().body(userResponse);
    }
}
