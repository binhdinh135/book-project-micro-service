package com.binhdc.springbootcommonproject.repository;

import com.binhdc.springbootcommonproject.entity.SecretToken;
import com.binhdc.springbootcommonproject.entity.User;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecretTokenRepository extends JpaRepository<SecretToken, Long> {
    @Query("""
           select distinct sec from SecretToken sec where sec.userId = :userId
           and sec.clientId = :clientId
           and sec.clientSecret = :clientSecret
           """)
    Optional<SecretToken> findByUserId(String userId, String clientId, String clientSecret);
}

