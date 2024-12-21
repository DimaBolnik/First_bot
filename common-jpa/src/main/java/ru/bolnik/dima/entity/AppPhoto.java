package ru.bolnik.dima.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_photo")
@Entity
public class AppPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telegramFileId;

    @OneToOne
    private BinaryContent binaryContent;

    private Integer fileSize;
}

