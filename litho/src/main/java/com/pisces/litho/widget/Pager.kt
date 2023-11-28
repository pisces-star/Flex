package com.pisces.litho.widget

import android.os.Handler
import android.os.Looper
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.facebook.litho.*
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.annotations.PropDefault
import com.facebook.litho.flexbox.positionType
import com.facebook.rendercore.primitives.FillLayoutBehavior
import com.facebook.rendercore.primitives.Primitive
import com.facebook.rendercore.primitives.ViewAllocator
import com.facebook.yoga.YogaAlign
import com.facebook.yoga.YogaPositionType
import com.pisces.core.context.PropsContext
import com.pisces.core.enums.IndicatorType
import com.pisces.core.enums.Orientation
import com.pisces.litho.toPx

@Suppress("DEPRECATION")
@LayoutSpec
object PagerSpec {
    @PropDefault
    val isCircular: Boolean = false

    @PropDefault
    val disableSwiping: Boolean = false

    @PropDefault
    val orientation: Orientation = Orientation.HORIZONTAL

    @PropDefault
    val timeSpan: Int = 2

    @PropDefault
    val indicatorEnable: Boolean = false

    @PropDefault
    val indicatorHeight: Int = 3.toPx()

    @PropDefault
    val indicatorWidth: Int = 8.toPx()

    @PropDefault
    val indicatorSpace: Int = 2.5.toPx()

    @PropDefault
    val indicatorMargin: Int = 5.toPx()

    @PropDefault
    val indicatorType: IndicatorType = IndicatorType.OVAL

    @PropDefault
    val indicatorSelected: String = PropsContext.Functions.Resource.drawable("indicator_light")

    @PropDefault
    val indicatorUnselected: String = PropsContext.Functions.Resource.drawable("indicator_black")


    @OnCreateLayout
    fun onCreateLayout(
        context: ComponentContext,
        @Prop(optional = true)
        isCircular: Boolean,
        @Prop(optional = true)
        disableSwiping: Boolean,
        @Prop(optional = true)
        orientation: Orientation,
        @Prop(optional = true)
        timeSpan: Int,
        @Prop(optional = true)
        indicatorEnable: Boolean,
        @Prop(optional = true)
        indicatorHeight: Int,
        @Prop(optional = true)
        indicatorWidth: Int,
        @Prop(optional = true)
        indicatorSpace: Int,
        @Prop(optional = true)
        indicatorMargin: Int,
        @Prop(optional = true)
        indicatorType: IndicatorType,
        @Prop(optional = true)
        indicatorUnselected: String,
        @Prop(optional = true)
        indicatorSelected: String,
        @Prop(varArg = "child")
        children: List<Component>
    ): Component {
        return KPager(
            isCircular,
            disableSwiping,
            orientation,
            timeSpan,
            indicatorEnable,
            indicatorHeight,
            indicatorWidth,
            indicatorSpace,
            indicatorMargin,
            indicatorType,
            indicatorUnselected,
            indicatorSelected,
            children
        )
    }
}

class KPager(
    private val isCircular: Boolean,
    private val disableSwiping: Boolean,
    private val orientation: Orientation,
    private val timeSpan: Int,
    private val indicatorEnable: Boolean,
    private val indicatorHeight: Int,
    private val indicatorWidth: Int,
    private val indicatorSpace: Int,
    private val indicatorMargin: Int,
    private val indicatorType: IndicatorType,
    private val indicatorUnselected: String,
    private val indicatorSelected: String,
    private val children: List<Component>
) : KComponent() {
    override fun ComponentScope.render(): Component? {
        val selectedIndex = useState { 0 }

        val primitiveComponent = PrimitivePager(isCircular, disableSwiping, orientation, timeSpan, children) {
            selectedIndex.update(it)
        }

        return if (indicatorEnable) {
            Column(alignItems = YogaAlign.CENTER, style = Style.positionType(YogaPositionType.RELATIVE)) {
                child(primitiveComponent)
                child(
                    Indicator(
                        indicatorHeight,
                        indicatorWidth,
                        indicatorSpace,
                        indicatorMargin,
                        indicatorType,
                        indicatorUnselected,
                        indicatorSelected,
                        selectedIndex.value,
                        children.size
                    )
                )
            }

        } else primitiveComponent
    }
}

class PrimitivePager(
    private val isCircular: Boolean,
    private val disableSwiping: Boolean,
    private val orientation: Orientation,
    private val timeSpan: Int,
    private val children: List<Component>,
    private val style: Style? = null,
    private val selectedCallback: ((Int) -> Unit)? = null
) : PrimitiveComponent() {
    var index = 0
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private val realOrientation =
        if (orientation == Orientation.HORIZONTAL) ViewPager2.ORIENTATION_HORIZONTAL else ViewPager2.ORIENTATION_VERTICAL

    private val onPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            index = position
            selectedCallback?.invoke(position)
        }
    }
    private val PrimitiveComponentScope.pagerPrimitive
        get() =
            Primitive(
                layoutBehavior = FillLayoutBehavior(1080, 240),
                mountBehavior =
                MountBehavior(ViewAllocator { context -> ViewPager2(context) }) {
                    bind(disableSwiping, realOrientation, onPageChangeCallback, timeSpan) { pager ->
                        val looperRunnable = object : Runnable {
                            override fun run() {
                                pager.currentItem = index + 1
                                handler.postDelayed(this, timeSpan * 1000L)
                            }

                        }
                        pager.orientation = realOrientation
                        pager.isUserInputEnabled = disableSwiping.not()
                        pager.registerOnPageChangeCallback(onPageChangeCallback)
                        pager.adapter = PagerAdapter(isCircular, children)

                        if (timeSpan != 0 && children.size > 1) {
                            handler.postDelayed(looperRunnable, timeSpan * 1000L)
                        }
                        onUnbind {
                            pager.unregisterOnPageChangeCallback(onPageChangeCallback)
                            handler.removeCallbacks(looperRunnable)
                        }
                    }
                })

    override fun PrimitiveComponentScope.render(): LithoPrimitive {
        return LithoPrimitive(primitive = pagerPrimitive, style = style)
    }
}

internal class PagerAdapter(
    private val isCircular: Boolean,
    private val children: List<Component>
) : LithoViewsAdapter() {
    override fun onBindViewHolder(holder: LithoViewHolder, position: Int) {
        val realPosition = position % children.size
        val component = children[realPosition]
        holder.lithoView.setComponent(component)
    }

    override fun getItemCount(): Int {
        return if (isCircular) Int.MAX_VALUE else children.size
    }

}