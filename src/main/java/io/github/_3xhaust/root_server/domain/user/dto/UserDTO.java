package io.github._3xhaust.root_server.domain.user.dto;

import io.github._3xhaust.root_server.domain.image.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private Image profileImage;
    private Short rating;
    private String language;
    private Instant createdAt;
}

