package com.pisces.litho.widget

import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.facebook.litho.*
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.annotations.PropDefault
import com.facebook.litho.flexbox.positionType
import com.facebook.litho.visibility.onVisibilityChanged
import com.facebook.litho.widget.SnapUtil.SNAP_TO_CENTER
import com.facebook.litho.widget.collection.*
import com.facebook.rendercore.px
import com.facebook.yoga.YogaAlign
import com.facebook.yoga.YogaPositionType
import com.pisces.core.context.PropsContext.Functions.Resource
import com.pisces.core.enums.IndicatorType
import com.pisces.core.enums.RecyclerStyle
import com.pisces.core.enums.RecyclerStyle.*
import com.pisces.core.enums.Orientation
import com.pisces.litho.toPx


@Suppress("DEPRECATION")
@LayoutSpec
object RecyclerSpec {
    @PropDefault
    val enableScroll: Boolean = false

    @PropDefault
    val autoScroll: Boolean = false

    @PropDefault
    val recyclerStyle: RecyclerStyle = LIST

    @PropDefault
    val orientation: Orientation = Orientation.HORIZONTAL

    @PropDefault
    val verticalSpace: Int = 2.toPx()

    @PropDefault
    val horizontalSpace: Int = 2.toPx()

    @PropDefault
    val columns: Int = 2

    @PropDefault
    val delayTime: Int = 2

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
    val indicatorSelected: String = Resource.drawable("indicator_light")

    @PropDefault
    val indicatorUnselected: String = Resource.drawable("indicator_black")

    @OnCreateLayout
    fun onCreateLayout(
        context: ComponentContext,
        @Prop(optional = true)
        enableScroll: Boolean,
        @Prop(optional = true)
        autoScroll: Boolean,
        @Prop(optional = true)
        recyclerStyle: RecyclerStyle,
        @Prop(optional = true)
        orientation: Orientation,
        @Prop(optional = true)
        verticalSpace: Int,
        @Prop(optional = true)
        horizontalSpace: Int,
        @Prop(optional = true)
        columns: Int,
        @Prop(optional = true)
        delayTime: Int,
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
        children: List<Component>,
    ): Component {
        return KRecycler(
            enableScroll,
            autoScroll,
            recyclerStyle,
            orientation,
            verticalSpace,
            horizontalSpace,
            columns,
            delayTime,
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

    class KRecycler(
        private val enableScroll: Boolean = false,
        private val autoScroll: Boolean = false,
        private val recyclerStyle: RecyclerStyle = LIST,
        private val orientation: Orientation = Orientation.HORIZONTAL,
        private val verticalSpace: Int = 2.toPx(),
        private val horizontalSpace: Int = 2.toPx(),
        private val columns: Int = 2,
        private val delayTime: Int = 2,
        private val timeSpan: Int = 2,
        private val indicatorEnable: Boolean = false,
        private val indicatorHeight: Int = 3.toPx(),
        private val indicatorWidth: Int = 8.toPx(),
        private val indicatorSpace: Int = 2.5.toPx(),
        private val indicatorMargin: Int = 5.toPx(),
        private val indicatorType: IndicatorType = IndicatorType.OVAL,
        private val indicatorUnselected: String = "",
        private val indicatorSelected: String = "",
        private val components: List<Component>,
    ) : KComponent() {
        override fun ComponentScope.render(): Component? {
            val selectedIndex = useState { 0 }
            val collectionController = useState { LazyCollectionController() }.value
            val realOrientation =
                if (orientation == Orientation.HORIZONTAL) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
            val handler = Handler(Looper.getMainLooper())
            val runnable = Runnable {
                collectionController.recyclerView?.let {
                    smoothScrollToIndex(
                        it,
                        realOrientation,
                        components.lastIndex,
                        (timeSpan * 1000L).toFloat()
                    )
                }
            }
//            val looperRunnable = Runnable { selectedIndex.update { (it + 1) % components.size } }
            val style = Style.onVisibilityChanged {
                if (autoScroll && recyclerStyle != VIEW_PAGER) {
                    if (it.percentVisibleHeight == 100f && it.percentVisibleWidth == 100f) {
                        handler.postDelayed(runnable, delayTime * 1000L)
                    } else {
                        collectionController.recyclerView?.stopScroll()
                        handler.removeCallbacks(runnable)
                    }
                }

                /*if (timeSpan != 0 && listStyle == VIEW_PAGER) {
                    if (it.percentVisibleHeight > 0f || it.percentVisibleWidth > 0f && components.size > 1) {
                        collectionController.smoothScrollToIndex(selectedIndex.value)
                        handler.postDelayed(looperRunnable, timeSpan * 1000L)
                    } else {
                        collectionController.recyclerView?.stopScroll()
                        handler.removeCallbacks(looperRunnable)
                    }
                }*/
            }
            val pager = when (recyclerStyle) {
                LIST -> {
                    LazyList(
                        orientation = realOrientation,
                        lazyCollectionController = collectionController,
                        style = style,
                        nestedScrollingEnabled = enableScroll,
                        itemDecoration = LinearSpacing(all = horizontalSpace.px)
                    ) {
                        children(components, id = { components.indexOf(it) }) { it }
                    }
                }

                GRID -> {
                    LazyGrid(
                        orientation = realOrientation,
                        style = style,
                        columns = columns,
                        lazyCollectionController = collectionController,
                        nestedScrollingEnabled = enableScroll,
                        itemDecoration = LinearSpacing(all = horizontalSpace.px)
                    ) {
                        children(components, id = { components.indexOf(it) }) { it }
                    }
                }

                VIEW_PAGER -> {
                    LazyList(
                        orientation = realOrientation,
                        snapMode = SNAP_TO_CENTER,
                        style = style,
                        onViewportChanged = { firstVisibleIndex, _, _, _, _ ->
                            if (timeSpan == 0 && firstVisibleIndex != selectedIndex.value) {
                                selectedIndex.update(firstVisibleIndex)
                            }
                        },
                        lazyCollectionController = collectionController,
                        nestedScrollingEnabled = enableScroll
                    ) {
                        children(components, id = { components.indexOf(it) }) { it }
                    }
                }

                STAGGERED_GRID -> {
                    LazyStaggeredGrid(
                        orientation = realOrientation,
                        spans = columns,
                        style = style,
                        lazyCollectionController = collectionController,
                        nestedScrollingEnabled = enableScroll,
                        itemDecoration = LinearSpacing(all = horizontalSpace.px)
                    ) {
                        children(components, id = { components.indexOf(it) }) { it }
                    }
                }
            }
            if (indicatorEnable) {
                return Column(alignItems = YogaAlign.CENTER, style = Style.positionType(YogaPositionType.RELATIVE)) {
                    child(pager)
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
                            components.size
                        )
                    )
                }
            }

            return pager
        }


        private fun smoothScrollToIndex(
            recyclerView: RecyclerView,
            @RecyclerView.Orientation orientation: Int,
            position: Int,
            scrollDuration: Float
        ) {
            val smoothScroller = object : LinearSmoothScroller(recyclerView.context) {
                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
                    val scrollRange =
                        if (orientation == RecyclerView.HORIZONTAL) recyclerView.computeHorizontalScrollRange() else recyclerView.computeVerticalScrollRange()
                    return scrollDuration / scrollRange
                }
            }
            smoothScroller.targetPosition = position
            recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
        }

    }
}