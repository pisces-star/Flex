package com.pisces.litho.widget

import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.getChildMeasureSpec
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.Row
import com.facebook.litho.Size
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayoutWithSizeSpec
import com.facebook.litho.annotations.Prop
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaPositionType
import kotlin.math.max

@Suppress("DEPRECATION")
@LayoutSpec
object StackSpec {
    @OnCreateLayoutWithSizeSpec
    fun onCreateLayoutWithSizeSpec(
        c: ComponentContext,
        widthSpec: Int,
        heightSpec: Int,
        @Prop(optional = true, varArg = "child")
        children: List<Component>?
    ): Component {
        val owner = Row.create(c)
        if (children.isNullOrEmpty()) {
            return owner.build()
        }

        val wrappers = children.map {
            Row.create(c).child(it)
                .positionType(YogaPositionType.ABSOLUTE)
                .positionPx(YogaEdge.ALL, 0)
                .build()
        }

        val sizes = wrappers.map { child ->
            val size = Size()
            val childWidthMeasureSpec = getChildMeasureSpec(
                widthSpec,
                0,
                LayoutParams.WRAP_CONTENT
            )
            val childHeightMeasureSpec = getChildMeasureSpec(
                heightSpec,
                0,
                LayoutParams.WRAP_CONTENT
            )
            child.measure(c, childWidthMeasureSpec, childHeightMeasureSpec, size)
            size
        }

        var maxHeight = 0
        var maxWidth = 0
        for (index in children.indices) {
            val size = sizes[index]
            maxWidth = max(maxWidth, size.width)
            maxHeight = max(maxHeight, size.height)
        }
        for (index in wrappers.indices) {
            owner.child(wrappers[index])
        }
        return owner.widthPx(maxWidth)
            .heightPx(maxHeight)
            .build()
    }
}