package com.rtfm.hammer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "gap_option")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GapOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gap_option_id")
    private Integer gapOptionId;

    @Column(name = "gap_id", nullable = false)
    private Integer gapId;

    @Column(name = "option_text", nullable = false, columnDefinition = "TEXT")
    private String optionText;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @Column(name = "option_order", nullable = false)
    private Integer optionOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gap_id", insertable = false, updatable = false)
    @ToString.Exclude
    private GapField gapField;
}
