package com.pisces.core.build

import android.text.TextUtils
import com.pisces.core.enums.TextStyle
import com.pisces.core.enums.Vertical
import com.pisces.core.enums.Horizontal


internal object AbsText : Widget() {

    override val dataBinding by DataBinding
        .create(CommonDefine) {
            enum("verticalGravity", mapOf(
                "top" to Vertical.TOP,
                "bottom" to Vertical.BOTTOM,
                "center" to Vertical.CENTER
            ))
            enum("horizontalGravity", mapOf(
                "left" to Horizontal.LEFT,
                "right" to Horizontal.RIGHT,
                "center" to Horizontal.CENTER
            ))
            enum("ellipsize", mapOf(
                "start" to TextUtils.TruncateAt.START,
                "end" to TextUtils.TruncateAt.END,
                "middle" to TextUtils.TruncateAt.MIDDLE,
                "marquee" to TextUtils.TruncateAt.MARQUEE
            ))
            value("maxLines", fallback = Int.MAX_VALUE.toFloat())
            value("minLines", fallback = Int.MIN_VALUE.toFloat())
            value("textSize", fallback = 13.0f)
            enum("textStyle", mapOf(
                "normal" to TextStyle.NORMAL,
                "bold" to TextStyle.BOLD
            ))
        }
}