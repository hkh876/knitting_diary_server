package com.knitting.diary.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "pattern_image")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatternImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 파일 경로
    @Column(name = "file_path", length = 50, nullable = false)
    private String filePath;

    // 파일 명
    @Column(name = "file_name", length = 200, nullable = false)
    private String fileName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knitting_id")
    private Knitting knitting;

    @Builder
    public PatternImage(Long id, String filePath, String fileName, Knitting knitting) {
        this.id = id;
        this.filePath = filePath;
        this.fileName = fileName;
        this.knitting = knitting;
    }
}
