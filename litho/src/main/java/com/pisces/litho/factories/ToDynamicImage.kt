package com.pisces.litho.factories

import com.facebook.litho.ComponentContext
import com.pisces.core.build.*
import com.pisces.litho.factories.filler.PropsFiller
import com.pisces.litho.widget.DynamicImage

internal object ToDynamicImage : ToComponent<DynamicImage.Builder>() {

    override val propsFiller by PropsFiller
        .create<DynamicImage.Builder>(CommonProps) {
            enum("scaleType", DynamicImage.Builder::scaleType)
            value("blurRadius", DynamicImage.Builder::blurRadius)
            value("blurSampling", DynamicImage.Builder::blurSample)
            value("aspectRatio", DynamicImage.Builder::imageAspectRatio)
            pt("borderLeftTopRadius", DynamicImage.Builder::leftTopRadius)
            pt("borderRightTopRadius", DynamicImage.Builder::rightTopRadius)
            pt("borderRightBottomRadius", DynamicImage.Builder::rightBottomRadius)
            pt("borderLeftBottomRadius", DynamicImage.Builder::leftBottomRadius)
            text("src", DynamicImage.Builder::url)
        }

    override fun create(
        c: ComponentContext,
        visibility: Boolean,
        attrs: PropSet
    ): DynamicImage.Builder {
        return DynamicImage.create(c)
    }
}