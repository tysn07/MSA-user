package group.microserviceuser.user.controller;

import group.microserviceuser.user.dto.LoginRequestDto;
import group.microserviceuser.user.dto.SignupRequestDto;
import group.microserviceuser.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.share.config.global.entity.user.User;
import org.example.share.config.global.jwt.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;


    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return ResponseEntity.ok().body("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto,
                                        HttpServletResponse response) {
        User loginedUser = userService.login(loginRequestDto);
        String token = jwtUtil.createToken(loginedUser.getId(), loginedUser.getEmail(),
                loginedUser.getUsername(), loginedUser.getRole());
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        jwtUtil.addJwtToCookie(token, response);

        return ResponseEntity.ok().body(loginedUser.getRole().toString());
    }
}
