package group.microserviceuser.user.service;

import group.microserviceuser.common.entity.user.User;
import group.microserviceuser.common.entity.user.UserRoleEnum;
import group.microserviceuser.common.exception.ConflictException;
import group.microserviceuser.common.exception.NotFoundException;
import group.microserviceuser.common.exception.UnauthorizedAccessException;
import group.microserviceuser.common.security.UserDetailsImpl;
import group.microserviceuser.user.dto.LoginRequestDto;
import group.microserviceuser.user.dto.SignupRequestDto;
import group.microserviceuser.user.dto.UserResponseDto;
import group.microserviceuser.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZaHgTBcXukeZygoC";

    public void signup(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new RuntimeException("중복된 username입니다.");
        }

        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new RuntimeException("중복된 email입니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if (ADMIN_TOKEN.equals(signupRequestDto.getAdminToken())) {
            role = UserRoleEnum.ADMIN;
        }
        userRepository.save(User.builder()
                .email(email)
                .username(username)
                .password(password)
                .role(role)
                .build());

    }

    public User login(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

}
