package com.pisces.litho.widget

import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.RoundingParams
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.fresco.FrescoImage

@Suppress("DEPRECATION")
@LayoutSpec
object DynamicImageSpec {
    @OnCreateLayout
    fun onCreateLayout(
        context: ComponentContext,
        @Prop(optional = true)
        scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.CENTER,
        @Prop(optional = true, isCommonProp = true)
        aspectRatio: Float = 1.0f,
        @Prop
        url: Any = "",
        @Prop(optional = true)
        blurRadius: Float = 0f,
        @Prop(optional = true)
        blurSample: Float = 0f,
        @Prop(optional = true)
        leftTopRadius: Float = 0f,
        @Prop(optional = true)
        rightTopRadius: Float = 0f,
        @Prop(optional = true)
        rightBottomRadius: Float = 0f,
        @Prop(optional = true)
        leftBottomRadius: Float = 0f
    ): Component {
        val controller = Fresco.newDraweeControllerBuilder()
            .setUri(url.toString())
            .build()
        return FrescoImage.create(context)
            .actualImageScaleType(scaleType)
            .imageAspectRatio(aspectRatio)
            .roundingParams(
                RoundingParams.fromCornersRadii(
                    leftTopRadius,
                    rightTopRadius,
                    leftBottomRadius,
                    rightBottomRadius
                )
            )
            .controller(controller)
            .build()
    }
}