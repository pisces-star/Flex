package com.pisces.core.build

import androidx.annotation.RestrictTo
import com.pisces.core.enums.FlexJustify
import com.pisces.core.enums.FlexWrap
import com.pisces.core.enums.FlexAlign
import com.pisces.core.enums.FlexDirection


@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object Flex : Widget() {
    override val dataBinding by DataBinding
        .create(CommonDefine) {
            enum("flexWrap", mapOf(
                "wrap" to FlexWrap.WRAP,
                "noWrap" to FlexWrap.NO_WRAP,
                "wrapReverse" to FlexWrap.WRAP_REVERSE
            ))
            enum("justifyContent", mapOf(
                "flexStart" to FlexJustify.FLEX_START,
                "flexEnd" to FlexJustify.FLEX_END,
                "center" to FlexJustify.CENTER,
                "spaceBetween" to FlexJustify.SPACE_BETWEEN,
                "spaceAround" to FlexJustify.SPACE_AROUND
            ))
            enum("alignItems", mapOf(
                "auto" to FlexAlign.AUTO,
                "flexStart" to FlexAlign.FLEX_START,
                "flexEnd" to FlexAlign.FLEX_END,
                "center" to FlexAlign.CENTER,
                "baseline" to FlexAlign.BASELINE,
                "stretch" to FlexAlign.STRETCH
            ))
            enum("alignContent", mapOf(
                "auto" to FlexAlign.AUTO,
                "flexStart" to FlexAlign.FLEX_START,
                "flexEnd" to FlexAlign.FLEX_END,
                "center" to FlexAlign.CENTER,
                "baseline" to FlexAlign.BASELINE,
                "stretch" to FlexAlign.STRETCH
            ))
            enum("flexDirection", mapOf(
                "row" to FlexDirection.ROW,
                "column" to FlexDirection.COLUMN,
                "rowReverse" to FlexDirection.ROW_REVERSE,
                "columnReverse" to FlexDirection.COLUMN_REVERSE,
                "stack" to FlexDirection.STACK
            ))
        }
}