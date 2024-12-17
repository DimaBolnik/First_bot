package ru.bolnik.dima.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.bolnik.dima.entity.enums.UserState;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long telegramUserId;

    @CreationTimestamp
    private LocalDateTime firstLoginDate;

    private String firstName;
    private String lastName;
    private String userName;
    private String email;

    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private UserState state;
}
