package com.binhdc.springbootcommonproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "secret_token")
public class SecretToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    Long id;

    @Column(name = "client_id")
    String clientId;

    @Column(name = "client_secret")
    String clientSecret;

    @Column(name = "user_id")
    String userId;
}
