package group.microserviceuser.user.dto;

import group.microserviceuser.common.entity.user.UserRoleEnum;
import lombok.Getter;


@Getter
public class UserResponseDto {

    private String username;
    private String email;
    private UserRoleEnum role;

    public UserResponseDto(String username, String email, UserRoleEnum role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }
}