package io.github._3xhaust.root_server.domain.translationcache.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationRequest {

    @NotBlank(message = "번역할 텍스트는 필수입니다.")
    private String text;

    @NotBlank(message = "대상 언어 코드는 필수입니다.")
    private String targetLanguage;
}

