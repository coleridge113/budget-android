package com.luna.budgetapp.ui.icons
/*
MIT License

Copyright (c) Tailwind Labs, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

*/
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val BillsIcon: ImageVector
    get() {
        if (_HeroiconsNewspaper != null) return _HeroiconsNewspaper!!
        
        _HeroiconsNewspaper = ImageVector.Builder(
            name = "newspaper",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Transparent),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1.5f,
                strokeLineJoin = StrokeJoin.Miter
            ) {
                moveTo(12f, 7.5f)
                horizontalLineToRelative(1.5f)
                moveToRelative(-1.5f, 3f)
                horizontalLineToRelative(1.5f)
                moveToRelative(-7.5f, 3f)
                horizontalLineToRelative(7.5f)
                moveToRelative(-7.5f, 3f)
                horizontalLineToRelative(7.5f)
                moveToRelative(3f, -9f)
                horizontalLineToRelative(3.375f)
                curveToRelative(0.621f, 0f, 1.125f, 0.504f, 1.125f, 1.125f)
                verticalLineTo(18f)
                arcToRelative(2.25f, 2.25f, 0f, false, true, -2.25f, 2.25f)
                moveTo(16.5f, 7.5f)
                verticalLineTo(18f)
                arcToRelative(2.25f, 2.25f, 0f, false, false, 2.25f, 2.25f)
                moveTo(16.5f, 7.5f)
                verticalLineTo(4.875f)
                curveToRelative(0f, -0.621f, -0.504f, -1.125f, -1.125f, -1.125f)
                horizontalLineTo(4.125f)
                curveTo(3.504f, 3.75f, 3f, 4.254f, 3f, 4.875f)
                verticalLineTo(18f)
                arcToRelative(2.25f, 2.25f, 0f, false, false, 2.25f, 2.25f)
                horizontalLineToRelative(13.5f)
                moveTo(6f, 7.5f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(3f)
                horizontalLineTo(6f)
                verticalLineToRelative(-3f)
                close()
            }
        }.build()
        
        return _HeroiconsNewspaper!!
    }

private var _HeroiconsNewspaper: ImageVector? = null

