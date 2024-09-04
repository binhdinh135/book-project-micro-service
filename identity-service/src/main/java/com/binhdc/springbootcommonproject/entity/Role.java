package com.binhdc.springbootcommonproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
// Permission (Privilege)
// - createPost
// - updatePost
/*
Role:
    - ADMIN
    - STAFF
    - USER
một user gồm có nhiều Role: User -> many Role : ngược lại, một Role có thể gán cho nhiều user.
một Role gôm có nhều permission: Role -> many Permission : ngược lại, một Permission có thể gán cho nhiều Role.
Role - User: là quan hệ n - n
Role có pk: id, User có pk: id -> cần bảng user_roles ở giữa với 2 pk là role_id và user_id
Role - Permission: là quan hệ n - n

*/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "role")
public class Role {
    @Id
    String name;

    String description;

    @ManyToMany
    Set<Permission> permissions;
}


