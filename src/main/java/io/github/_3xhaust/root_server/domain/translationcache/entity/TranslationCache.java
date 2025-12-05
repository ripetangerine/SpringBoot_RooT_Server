package io.github._3xhaust.root_server.domain.translationcache.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Table(name = "translation_cache")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TranslationCache {
    @Id
    @Column(length = 64)
    private String hash;

    @Column(nullable = false)
    private String targetLanguage;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String originText;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String translatedText;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Builder
    public TranslationCache(String hash, String targetLanguage, String originText, String translatedText) {
        this.hash = hash;
        this.targetLanguage = targetLanguage;
        this.originText = originText;
        this.translatedText = translatedText;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}

