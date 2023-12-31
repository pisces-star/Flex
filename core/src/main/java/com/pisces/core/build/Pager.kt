package com.pisces.core.build

import androidx.annotation.RestrictTo
import com.pisces.core.enums.IndicatorType
import com.pisces.core.enums.Orientation

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object Pager : Widget() {
    override val dataBinding by DataBinding
        .create {
            bool("isCircular")
            bool("disableSwiping")
            enum(
                "orientation", mapOf(
                    "vertical" to Orientation.VERTICAL,
                    "horizontal" to Orientation.HORIZONTAL
                )
            )
            value("timeSpan")
            bool("indicatorEnable")
            value("indicatorHeight")
            value("indicatorWidth")
            value("indicatorSpace")
            value("indicatorMargin")
            enum(
                "indicatorType", mapOf(
                    "oval" to IndicatorType.OVAL,
                    "rectangle" to IndicatorType.RECTANGLE
                )
            )
            text("indicatorSelected")
            text("indicatorUnselected")
        }
}