package com.pisces.litho.drawable

import android.content.Context
import android.graphics.Canvas
import com.facebook.litho.drawable.ComparableDrawable
import com.facebook.litho.drawable.ComparableDrawableWrapper
import com.facebook.litho.fresco.NoOpDrawable
import com.pisces.litho.drawable.load.CornerRadius
import java.util.concurrent.atomic.AtomicBoolean

class LazyImageDrawable private constructor(
    private val context: Context,
    private val model: Any,
    private val radius: CornerRadius
) : ComparableDrawableWrapper(NoOpDrawable())  {

    constructor(
        context: Context,
        model: Any,
        leftTop: Float,
        rightTop: Float,
        rightBottom: Float,
        leftBottom: Float
    ) : this(
        context, model,
        CornerRadius(
            leftTop,
            rightTop,
            rightBottom,
            leftBottom
        )
    )

    constructor(
        context: Context,
        model: Any,
        radius: Float
    ) : this(context, model, CornerRadius(radius))

    constructor(
        context: Context,
        model: Any
    ) : this(context, model, 0f)

    private val isInit = AtomicBoolean(false)

    override fun isEquivalentTo(other: ComparableDrawable?): Boolean {
        return other is LazyImageDrawable
                && model == other.model
                && radius == other.radius
    }

    override fun draw(canvas: Canvas) {
        if (isInit.compareAndSet(false, true)) {
            TODO()
        } else {
            super.draw(canvas)
        }
    }
}