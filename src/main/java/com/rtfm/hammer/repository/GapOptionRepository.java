package com.rtfm.hammer.repository;

import com.rtfm.hammer.model.GapOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GapOptionRepository extends JpaRepository<GapOption, Integer> {

    /**
     * Find all options for a specific gap field.
     *
     * @param gapId the gap field ID
     * @return list of gap options ordered by option order
     */
    List<GapOption> findByGapIdOrderByOptionOrder(Integer gapId);

    /**
     * Find all correct options for a specific gap field.
     *
     * @param gapId the gap field ID
     * @return list of correct gap options
     */
    List<GapOption> findByGapIdAndIsCorrectTrue(Integer gapId);
}
