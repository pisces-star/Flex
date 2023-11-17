package com.pisces.litho.widget

import android.os.Handler
import android.os.Looper
import com.facebook.litho.*
import com.facebook.litho.annotations.*
import com.facebook.litho.annotations.State
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.common.DataDiffSection
import com.facebook.litho.sections.common.RenderEvent
import com.facebook.litho.sections.widget.PageSelectedEvent
import com.facebook.litho.sections.widget.RecyclerCollectionEventsController
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.RenderInfo
import com.facebook.yoga.YogaPositionType
import com.pisces.core.enums.Orientation
import com.pisces.core.enums.PagerStyle


@Suppress("DEPRECATION")
@LayoutSpec
object PagerSpec {

    @PropDefault
    val circular: Boolean = false

    @PropDefault
    val style: PagerStyle = PagerStyle.LIST

    @PropDefault
    val autoScroll: Boolean = false

    @PropDefault
    val space: Int = 0

    @PropDefault
    val spanCount: Int = 4

    @PropDefault
    val orientation: Orientation = Orientation.HORIZONTAL

    @OnCreateLayout
    fun onCreateLayout(
        context: ComponentContext,
        @Prop(optional = true)
        circular: Boolean,
        @Prop(optional = true)
        timeSpan: Float,
        @Prop(optional = true)
        orientation: Orientation,
        @Prop(optional = true)
        indicatorEnable: Boolean,
        @Prop(optional = true)
        indicatorSize: Int = 0,
        @Prop(optional = true)
        indicatorHeight: Float,
        @Prop(optional = true)
        indicatorSelected: String,
        @Prop(optional = true)
        indicatorUnselected: String,
        @Prop(varArg = "child")
        children: List<Component>,
        @State selectedIndex: Int,
        @State eventsController: RecyclerCollectionEventsController
    ): Component {
        return if (indicatorEnable) {
            Column.create(context)
                .positionType(YogaPositionType.ABSOLUTE)
                .child(buildViewPager(context, selectedIndex, circular, children, eventsController))
                .build()
        } else {
            buildViewPager(context, selectedIndex, circular, children, eventsController)
        }
    }


    private fun buildViewPager(
        context: ComponentContext,
        selectedIndex: Int,
        circular: Boolean,
        children: List<Component>,
        eventsController: RecyclerCollectionEventsController
    ): Component {
        return ViewPager2Component.create<Model>(context)
            .initialPageIndex(selectedIndex)
            .isCircular(circular)
            .dataDiffSection(
                buildDataDiffSection(context, children)
            )
            .pageSelectedEventHandler(Pager.onPageSelectedEvent(context))
            .visibleEventHandler(Pager.onVisibleEvent(context))
            .invisibleEventHandler(Pager.onInvisibleEvent(context))
            .eventsController(eventsController)
            .build()
    }

    private fun buildDataDiffSection(context: ComponentContext, children: List<Component>): DataDiffSection<Model>? {
        val dataDiffSection = DataDiffSection.create<Model>(SectionContext(context))
            .data(children.map { Model(it) })
            .renderEventHandler(Pager.onRenderEvent(context))
            .build()

        return dataDiffSection as DataDiffSection<Model>?
    }

    @OnCreateInitialState
    fun onCreateInitialState(
        context: ComponentContext,
        handler: StateValue<Handler>,
        runnable: StateValue<Runnable>,
        selectedIndex: StateValue<Int>,
        eventsController: StateValue<RecyclerCollectionEventsController>
    ) {
        Handler(Looper.getMainLooper()).also { handler.set(it) }
        val controller = RecyclerCollectionEventsController().also { eventsController.set(it) }
        Runnable { controller.requestScrollToNextPosition(true) }.also { runnable.set(it) }
        selectedIndex.set(0)
    }

    @OnEvent(VisibleEvent::class)
    fun onVisibleEvent(
        context: ComponentContext,
        @Prop(optional = true)
        timeSpan: Float,
        @State
        eventsController: RecyclerCollectionEventsController,
        @State
        handler: Handler,
        @State
        runnable: Runnable
    ) {
        if (timeSpan != 0f) {
            eventsController.recyclerView?.stopScroll()
            handler.removeCallbacks(runnable)
        }
    }

    @OnEvent(InvisibleEvent::class)
    fun onInvisibleEvent(
        context: ComponentContext,
        @Prop(optional = true)
        timeSpan: Float,
        @State
        handler: Handler,
        @State
        runnable: Runnable
    ) {
        if (timeSpan != 0f) {
            handler.postDelayed(runnable, (timeSpan * 1000L).toLong())
        }
    }

    @OnEvent(PageSelectedEvent::class)
    fun onPageSelectedEvent(context: ComponentContext, @FromEvent selectedPageIndex: Int) {
        Pager.updateSelectedIndex(context, selectedPageIndex)
    }

    @OnUpdateState
    fun updateSelectedIndex(selectedIndex: StateValue<Int>, @Param indexedValue: Int) {
        selectedIndex.set(indexedValue)
    }

    @OnEvent(RenderEvent::class)
    fun onRenderEvent(context: ComponentContext?, @FromEvent model: Model): RenderInfo {
        return ComponentRenderInfo.create()
            .component(model.content)
            .build()
    }


    class Model(
        val content: Component
    )

}