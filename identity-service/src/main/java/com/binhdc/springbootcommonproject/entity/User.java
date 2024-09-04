package com.binhdc.springbootcommonproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbd_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "username"
            /*
            unique = true để xác định rằng với cột username chỉ có duy nhất 1 giá trị tốn tại.
            Không truùng giá trị. Với các bài toàn nhiều người dùng cùng một lúc gọi API tạo user/pass
            -> "unique = true" sẽ check và chỉ duy nhất 1 request tạo ra tài khoản.
            Chúng ta có thể test bằng Apache Jmeter
             */
            , unique = true
//            , columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci"
    )
    String username;

    String password;
    String firstName;
    LocalDate dob;
    String lastName;

    @ManyToMany
    Set<Role> roles;

//    Set<String> roles;


}
