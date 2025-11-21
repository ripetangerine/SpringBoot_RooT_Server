package io.github._3xhaust.root_server.domain.auth.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "언어 코드는 필수 값입니다.")
    @Size(max = 5, message = "언어 코드는 최대 5자리까지 가능합니다.")
    private String language;

    @NotBlank(message = "이름은 필수 값입니다.")
    private String name;

    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @NotBlank(message = "비밀번호는 필수 값입니다.")
    private String password;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수 값입니다.")
    private String email;
}
