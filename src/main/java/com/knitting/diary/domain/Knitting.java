package com.knitting.diary.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "knitting")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Knitting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    // 도안 이름과 크기
    @Column(name = "pattern_name_size", length = 100, nullable = false)
    private String patternNameSize;

    // 디자이너
    @Column(name = "designer", length = 30, nullable = false)
    private String designer;

    // 원작 실
    @Column(name = "origin_yarn", length = 30, nullable = false)
    private String originYarn;

    // 원작 게이지
    @Column(name = "origin_gauge", length = 10, nullable = false)
    private String originGauge;

    // 원작 바늘 크기
    @Column(name = "origin_needle_size", length = 10, nullable = false)
    private String originNeedleSize;

    // 원작 실 사용량
    @Column(name = "origin_yardage", length = 10, nullable = false)
    private String originYardage;

    // 실
    @Column(name = "yarn", length = 30, nullable = false)
    private String yarn;

    // 바늘
    @Column(name = "needles", length = 30, nullable = false)
    private String needles;

    // 게이지
    @Column(name = "gauge", length = 10, nullable = false)
    private String gauge;

    // 실 사용량
    @Column(name = "yardage", length = 10, nullable = false)
    private String yardage;

    // 내용
    @Column(name = "contents", length = 1000)
    private String contents;

    // 시작일
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    // 종료일
    @Column(name = "end_date")
    private LocalDate endDate;

    // 도안 사진
    @OneToOne(mappedBy = "knitting", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private PatternImage patternImage;

    @OneToOne(mappedBy = "knitting", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private YarnNeedleImage yarnNeedleImage;

    @OneToMany(mappedBy = "knitting", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Picture> pictures;

    @OneToOne(mappedBy = "knitting", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private PatternPdf patternFile;

    @Builder
    public Knitting(Long id, String patternNameSize, String designer, String originYarn,
        String originGauge, String originNeedleSize, String originYardage, String yarn,
        String needles, String gauge, String yardage, String contents,
        LocalDate startDate, LocalDate endDate
    ) {
        this.id = id;
        this.patternNameSize = patternNameSize;
        this.designer = designer;
        this.originYarn = originYarn;
        this.originGauge = originGauge;
        this.originNeedleSize = originNeedleSize;
        this.originYardage = originYardage;
        this.yarn = yarn;
        this.needles = needles;
        this.gauge = gauge;
        this.yardage = yardage;
        this.contents = contents;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void update(String patternNameSize, String designer, String originYarn,
       String originGauge, String originNeedleSize, String originYardage, String yarn,
       String needles, String gauge, String yardage, String contents,
       LocalDate startDate, LocalDate endDate
    ) {
        if (!Objects.equals(this.patternFile, patternNameSize)) {
            this.patternNameSize = patternNameSize;
        }

        if (!Objects.equals(this.designer, designer)) {
            this.designer = designer;
        }

        if (!Objects.equals(this.originYarn, originYarn)) {
            this.originYarn = originYarn;
        }

        if (!Objects.equals(this.originGauge, originGauge)) {
            this.originGauge = originGauge;
        }

        if (!Objects.equals(this.originNeedleSize, originNeedleSize)) {
            this.originNeedleSize = originNeedleSize;
        }

        if (!Objects.equals(this.originYardage, originYardage)) {
            this.originYardage = originYardage;
        }

        if (!Objects.equals(this.yarn, yarn)) {
            this.yarn = yarn;
        }

        if (!Objects.equals(this.needles, needles)) {
            this.needles = needles;
        }

        if (!Objects.equals(this.gauge, gauge)) {
            this.gauge = gauge;
        }

        if (!Objects.equals(this.yardage, yardage)) {
            this.yardage = yardage;
        }

        if (!Objects.equals(this.contents, contents)) {
            this.contents = contents;
        }

        if (!Objects.equals(this.startDate, startDate)) {
            this.startDate = startDate;
        }

        if (!Objects.equals(this.endDate, endDate)) {
            this.endDate = endDate;
        }
    }
}
