package com.pisces.litho.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.facebook.litho.*
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.annotations.PropDefault
import com.facebook.litho.core.height
import com.facebook.litho.core.margin
import com.facebook.litho.core.width
import com.facebook.litho.flexbox.positionType
import com.facebook.litho.kotlin.widget.Image
import com.facebook.litho.visibility.onVisibilityChanged
import com.facebook.litho.widget.SnapUtil.SNAP_TO_CENTER
import com.facebook.litho.widget.collection.*
import com.facebook.rendercore.px
import com.facebook.yoga.YogaAlign
import com.facebook.yoga.YogaJustify
import com.facebook.yoga.YogaPositionType
import com.pisces.core.build.UrlType
import com.pisces.core.context.PropsContext.Functions.Resource
import com.pisces.core.enums.IndicatorType
import com.pisces.core.enums.Orientation
import com.pisces.core.enums.PagerStyle
import com.pisces.core.enums.PagerStyle.*
import com.pisces.litho.drawable.LazyImageDrawable
import com.pisces.litho.toPx
import android.graphics.drawable.GradientDrawable.Orientation as GradientOrientation


@Suppress("DEPRECATION")
@LayoutSpec
object PagerSpec {
    @PropDefault
    val circular: Boolean = false

    @PropDefault
    val enableScroll: Boolean = false

    @PropDefault
    val autoScroll: Boolean = false

    @PropDefault
    val pagerStyle: PagerStyle = LIST

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
        circular: Boolean,
        @Prop(optional = true)
        enableScroll: Boolean,
        @Prop(optional = true)
        autoScroll: Boolean,
        @Prop(optional = true)
        pagerStyle: PagerStyle,
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
        return KPager(
            circular,
            enableScroll,
            autoScroll,
            pagerStyle,
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
        /*return if (indicatorEnable) {
            Stack.create(context)
                .child(
                    buildRecycler(
                        context,
                        style,
                        selectedIndex,
                        children,
                        buildRecyclerConfiguration(style, orientation, circular, numColumns),
                        eventsController
                    )
                )
                .build()
        } else {
            buildRecycler(
                context,
                style,
                selectedIndex,
                children,
                buildRecyclerConfiguration(style, orientation, circular, numColumns),
                eventsController
            )
        }*/
    }


    /*private fun buildRecycler(
        context: ComponentContext,
        style: PagerStyle,
        selectedIndex: Int,
        children: List<Component>,
        recyclerConfiguration: RecyclerConfiguration,
        eventsController: RecyclerCollectionEventsController
    ): Component {

        return RecyclerCollectionComponent.create(context)
            .flexGrow(1f)
            .disablePTR(true)
            .section(buildDataDiffSection(style, context, children, selectedIndex))
            .eventsController(eventsController)
            .visibilityChangedHandler(Pager.onVisibilityChanged(context))
            .touchHandler(Pager.onSwipeDisabledTouchEvent(context))
            .onScrollListener(object : OnScrollListener() {
            })
            .recyclerConfiguration(recyclerConfiguration)
            .build()
    }


    private fun buildRecyclerConfiguration(
        style: PagerStyle,
        orientation: Orientation,
        isCircular: Boolean,
        numColumns: Int
    ): RecyclerConfiguration {
        return when (style) {
            LIST -> buildListRecyclerConfiguration(isCircular, orientation)
            GRID -> buildGridRecyclerConfiguration(isCircular, orientation, numColumns)
            VIEW_PAGER -> buildViewPagerConfiguration(isCircular)
            STAGGERED_GRID -> buildStaggeredGridRecyclerConfiguration(isCircular, orientation, numColumns)
        }
    }

    private fun buildViewPagerConfiguration(isCircular: Boolean): ListRecyclerConfiguration {
        return ListRecyclerConfiguration.create()
            .orientation(LinearLayoutManager.HORIZONTAL)
            .snapMode(SnapUtil.SNAP_TO_CENTER)
            .recyclerBinderConfiguration(buildRecyclerBinderConfiguration(isCircular = isCircular))
            .linearLayoutInfoFactory { context, orientation, reverseLayout, stackFromEnd ->
                ViewPagerLinearLayoutInfo(
                    context,
                    orientation,
                    reverseLayout
                )
            }
            .build()
    }


    private fun buildListRecyclerConfiguration(
        isCircular: Boolean,
        orientation: Orientation
    ): ListRecyclerConfiguration {
        return ListRecyclerConfiguration.create()
            .orientation(if (orientation == Orientation.HORIZONTAL) LinearLayoutManager.HORIZONTAL else LinearLayoutManager.VERTICAL)
            .snapMode(SnapUtil.SNAP_NONE)
            .recyclerBinderConfiguration(
                buildRecyclerBinderConfiguration(isCircular = isCircular)
            )
            .build()
    }

    private fun buildGridRecyclerConfiguration(
        isCircular: Boolean,
        orientation: Orientation,
        numColumns: Int
    ): GridRecyclerConfiguration {
        return GridRecyclerConfiguration.create()
            .orientation(if (orientation == Orientation.HORIZONTAL) GridLayoutManager.HORIZONTAL else GridLayoutManager.VERTICAL)
            .snapMode(SnapUtil.SNAP_NONE)
            .numColumns(numColumns)
            .recyclerBinderConfiguration(
                buildRecyclerBinderConfiguration(dynamicHeight = true)
            )
            .build()
    }

    private fun buildStaggeredGridRecyclerConfiguration(
        isCircular: Boolean,
        orientation: Orientation,
        numColumns: Int
    ): StaggeredGridRecyclerConfiguration {
        return StaggeredGridRecyclerConfiguration.create()
            .orientation(if (orientation == Orientation.HORIZONTAL) StaggeredGridLayoutManager.HORIZONTAL else StaggeredGridLayoutManager.VERTICAL)
            .numSpans(numColumns)
            .recyclerBinderConfiguration(
                buildRecyclerBinderConfiguration()
            )
            .build()
    }

    private fun buildRecyclerBinderConfiguration(
        isCircular: Boolean = false,
        dynamicHeight: Boolean = false
    ): RecyclerBinderConfiguration {
        return RecyclerBinderConfiguration.create()
            .hasDynamicItemHeight(dynamicHeight)
            .isCircular(isCircular)
            .build()
    }

    private fun buildDataDiffSection(
        style: PagerStyle,
        context: ComponentContext,
        children: List<Component>,
        initialPageIndex: Int
    ): Section {
        val dataDiffSection = DataDiffSection.create<Model>(SectionContext(context))
            .data(children.map { Model(it) })
            .renderEventHandler(Pager.onRender(context))
            .build()

        return when (style) {
            VIEW_PAGER -> ViewPagerHelperSection.create<Model>(SectionContext(context))
                .delegateSection(dataDiffSection as DataDiffSection<Model>?)
                .pageSelectedEventEventHandler(
                    Pager.onPageSelected(context)
                )
//                .initialPageIndex(initialPageIndex)
                .build()

            else -> dataDiffSection
        }
    }

    @OnCreateInitialState
    fun onCreateInitialState(
        context: ComponentContext,
        handler: StateValue<Handler>,
        runnable: StateValue<Runnable>,
        looperRunnable: StateValue<Runnable>,
        selectedIndex: StateValue<Int>,
        eventsController: StateValue<RecyclerCollectionEventsController>,
        @Prop(varArg = "child")
        children: List<Component>
    ) {
        val internalHandler = Handler(Looper.getMainLooper()).also { handler.set(it) }
        val controller = RecyclerCollectionEventsController().also { eventsController.set(it) }
        Runnable { controller.requestScrollToPosition(children.lastIndex, true) }.also { runnable.set(it) }
        val internalLooperRunnable = object : Runnable {
            override fun run() {
                controller.requestScrollToNextPosition(true)
                internalHandler.postDelayed(this, (timeSpan * 1000L).toLong())
            }
        }
        looperRunnable.set(internalLooperRunnable)
        selectedIndex.set(0)
    }

    @OnEvent(VisibilityChangedEvent::class)
    fun onVisibilityChanged(
        context: ComponentContext,
        @FromEvent percentVisibleWidth: Float,
        @FromEvent percentVisibleHeight: Float,
        @State handler: Handler,
        @State runnable: Runnable,
        @State looperRunnable: Runnable,
        @State
        eventsController: RecyclerCollectionEventsController,
        @Prop(varArg = "child")
        children: List<Component>,
        @Prop(optional = true)
        style: PagerStyle,
        @Prop(optional = true)
        autoScroll: Boolean,
        @Prop(optional = true)
        timeSpan: Float,
        @Prop(optional = true)
        delayTime: Float
    ) {
        if (autoScroll && style != VIEW_PAGER) {
            if (percentVisibleHeight == 100f && percentVisibleWidth == 100f) {
                handler.postDelayed(runnable, (delayTime * 1000L).toLong())
            } else {
                eventsController.recyclerView?.stopScroll()
                handler.removeCallbacks(runnable)
            }
        }

        if (timeSpan != 0f && style == VIEW_PAGER) {
            if (percentVisibleHeight > 0f || percentVisibleWidth > 0f && children.size > 1) {
                handler.postDelayed(looperRunnable, (timeSpan * 1000L).toLong())
            } else {
                eventsController.recyclerView?.stopScroll()
                handler.removeCallbacks(looperRunnable)
            }
        }

    }


    @OnEvent(PageSelectedEvent::class)
    fun onPageSelected(context: ComponentContext, @FromEvent selectedPageIndex: Int) {
        Pager.updateSelectedIndex(context, selectedPageIndex)
    }

    @OnUpdateState
    fun updateSelectedIndex(selectedIndex: StateValue<Int>, @Param indexedValue: Int) {
//        selectedIndex.set(indexedValue)
    }

    @OnEvent(RenderEvent::class)
    fun onRender(context: ComponentContext?, @FromEvent model: Model): RenderInfo {
        return ComponentRenderInfo.create()
            .component(model.content)
            .build()
    }

    @OnEvent(TouchEvent::class)
    @JvmStatic
    fun onSwipeDisabledTouchEvent(c: ComponentContext?, @FromEvent view: View?): Boolean {
        return true
    }


    class Model(
        val content: Component
    )

    private class ViewPagerLinearLayoutInfo(context: Context?, orientation: Int, reverseLayout: Boolean) :
        LinearLayoutInfo(context, orientation, reverseLayout) {
        override fun getChildWidthSpec(widthSpec: Int, renderInfo: RenderInfo): Int {
            val hscrollWidth = SizeSpec.getSize(widthSpec)
            return SizeSpec.makeSizeSpec(hscrollWidth, SizeSpec.EXACTLY)
        }
    }*/

}

class KPager(
    private val circular: Boolean = false,
    private val enableScroll: Boolean = false,
    private val autoScroll: Boolean = false,
    private val pagerStyle: PagerStyle = LIST,
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
        val collectionController = useState { LazyCollectionController() }
        val realOrientation =
            if (orientation == Orientation.HORIZONTAL) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            collectionController.value.recyclerView?.let {
                smoothScrollToIndex(
                    it,
                    realOrientation,
                    components.lastIndex,
                    (timeSpan * 1000L).toFloat()
                )
            }
        }
        val looperRunnable = Runnable { selectedIndex.update { (it + 1) % components.size } }
        val style = Style.onVisibilityChanged {
            if (autoScroll && pagerStyle != VIEW_PAGER) {
                if (it.percentVisibleHeight == 100f && it.percentVisibleWidth == 100f) {
                    handler.postDelayed(runnable, delayTime * 1000L)
                } else {
                    collectionController.value.recyclerView?.stopScroll()
                    handler.removeCallbacks(runnable)
                }
            }

            if (timeSpan != 0 && pagerStyle == VIEW_PAGER) {
                if (it.percentVisibleHeight > 0f || it.percentVisibleWidth > 0f && components.size > 1) {
                    collectionController.value.smoothScrollToIndex(selectedIndex.value)
                    handler.postDelayed(looperRunnable, timeSpan * 1000L)
                } else {
                    collectionController.value.recyclerView?.stopScroll()
                    handler.removeCallbacks(looperRunnable)
                }
            }
        }
        val pager = when (pagerStyle) {
            LIST -> {
                LazyList(
                    orientation = realOrientation,
                    lazyCollectionController = collectionController.value,
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
                    lazyCollectionController = collectionController.value,
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
                    lazyCollectionController = collectionController.value,
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
                    lazyCollectionController = collectionController.value,
                    nestedScrollingEnabled = enableScroll,
                    itemDecoration = LinearSpacing(all = horizontalSpace.px)
                ) {
                    children(components, id = { components.indexOf(it) }) { it }
                }
            }
        }
        if (indicatorEnable) {
            val indicatorSelectedDrawable = loadDrawable(context.androidContext, indicatorSelected)
            val indicatorUnselectedDrawable = loadDrawable(context.androidContext, indicatorUnselected)
            return Column(style = Style.positionType(YogaPositionType.RELATIVE)) {
                child(pager)
                child(
                    Row(
                        alignContent = YogaAlign.CENTER,
                        justifyContent = YogaJustify.CENTER,
                        style = Style
                            .positionType(YogaPositionType.RELATIVE)
                            .margin(bottom = indicatorMargin.px)
                    ) {
                        if (indicatorSelectedDrawable != null && indicatorUnselectedDrawable != null) {
                            for (index in components.indices) {
                                child(
                                    Image(
                                        drawable = if (index == selectedIndex.value) indicatorSelectedDrawable else indicatorSelectedDrawable,
                                        style = Style.height(indicatorHeight.px).width(indicatorWidth.px)
                                            .margin(horizontal = (indicatorSpace / 2).px)
                                    )
                                )
                            }
                        }
                    }
                )
            }
        }

        return pager
    }

    private fun loadDrawable(c: Context, url: String?): Drawable? {
        if (url.isNullOrEmpty()) {
            return null
        } else {
            val (type, args) = UrlType.parseUrl(c, url)
            when (type) {
                UrlType.COLOR -> {
                    return ColorDrawable(args[0] as Int)
                }

                UrlType.GRADIENT -> {
                    return GradientDrawable(
                        args[0] as GradientOrientation,
                        args[1] as IntArray
                    )
                }

                UrlType.URL, UrlType.RESOURCE -> {
                    return LazyImageDrawable(c, args[0])
                }

                else -> {
                    return null
                }
            }
        }
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