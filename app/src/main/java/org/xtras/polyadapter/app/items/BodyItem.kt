package org.xtras.polyadapter.app.items

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

sealed class BodyItem
data class IconCategory(
    @DrawableRes val iconResId: Int,
    val name: String
) : BodyItem()
data class TextCategory(
    val text: String
) : BodyItem()
data class BackgroundCategory(
    @ColorInt val backgroundColor: Int
) : BodyItem()
object LoadingItem : BodyItem()
