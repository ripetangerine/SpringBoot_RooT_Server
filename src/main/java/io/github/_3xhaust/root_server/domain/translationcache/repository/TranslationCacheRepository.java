package io.github._3xhaust.root_server.domain.translationcache.repository;

import io.github._3xhaust.root_server.domain.translationcache.entity.TranslationCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranslationCacheRepository extends JpaRepository<TranslationCache, String> {
    Optional<TranslationCache> findByHash(String hash);
    boolean existsByHash(String hash);
}

