package io.github._3xhaust.root_server.domain.user.dto.res;

import io.github._3xhaust.root_server.domain.image.entity.Image;
import io.github._3xhaust.root_server.domain.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String email;
    private String name;
    private Image profileImage;
    private Short rating;
    private String language;
    private Instant createdAt;

    public static UserResponseDTO of(UserDTO userDTO) {
        return UserResponseDTO.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .profileImage(userDTO.getProfileImage())
                .rating(userDTO.getRating())
                .language(userDTO.getLanguage())
                .createdAt(userDTO.getCreatedAt())
                .build();
    }
}

