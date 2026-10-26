package com.noop.ui

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * The padding step-down behind [SegmentedPillControl]'s intrinsic mode.
 *
 * The case that drove it: the Today heart-rate card puts six window labels ("Today", "24h", "12h", "6h",
 * "3h", "1h", about 122dp of text) in a card that is 272dp wide on a 360dp phone. At the shipped 12dp the
 * track needed 294dp, so the row squeezed the last segment until "1h" broke over two lines. The same card
 * on a 411dp phone is 323dp wide and must keep the shipped padding.
 */
class SegmentPaddingThatFitsTest {

    private val hrLabelsDp = 122f

    @Test
    fun aRowThatFitsKeepsTheShippedPadding() {
        assertEquals(12f, segmentPaddingThatFitsDp(hrLabelsDp, segments = 6, availableDp = 323f))
    }

    @Test
    fun aNarrowRowStepsDownToTheWidestPaddingThatFits() {
        assertEquals(8f, segmentPaddingThatFitsDp(hrLabelsDp, segments = 6, availableDp = 272f))
    }

    /** The boundary is inclusive: a row that fits exactly is a row that fits. 122 + 28 chrome + 144 = 294. */
    @Test
    fun anExactFitKeepsThePaddingItWasMeasuredAt() {
        assertEquals(12f, segmentPaddingThatFitsDp(hrLabelsDp, segments = 6, availableDp = 294f))
    }

    /** Nothing fits: take the narrowest step rather than fall off the end of the list. */
    @Test
    fun aRowThatFitsAtNoStepUsesTheNarrowest() {
        assertEquals(4f, segmentPaddingThatFitsDp(hrLabelsDp, segments = 6, availableDp = 150f))
    }

    @Test
    fun aSingleSegmentHasNoGapsToCount() {
        // 40 + 8 inset + 24 padding = 72.
        assertEquals(12f, segmentPaddingThatFitsDp(40f, segments = 1, availableDp = 72f))
    }
}
