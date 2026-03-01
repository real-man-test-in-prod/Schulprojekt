package com.rtfm.hammer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "GAP_FIELD")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GapField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gap_id")
    private Integer gapId;

    @Column(name = "question_id", nullable = false)
    private Integer questionId;

    @Column(name = "gap_index", nullable = false)
    private Integer gapIndex;

    @Column(name = "text_before", columnDefinition = "TEXT")
    private String textBefore;

    @Column(name = "text_after", columnDefinition = "TEXT")
    private String textAfter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Question question;

    @OneToMany(mappedBy = "gapField", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<GapOption> gapOptions;
}
