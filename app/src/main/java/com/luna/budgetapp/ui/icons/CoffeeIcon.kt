package com.luna.budgetapp.ui.icons

/*
ISC License

Copyright (c) for portions of Lucide are held by Cole Bemis 2013-2023 as part of Feather (MIT). All other copyright (c) for Lucide are held by Lucide Contributors 2025.

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

---

The MIT License (MIT) (for portions derived from Feather)

Copyright (c) 2013-2023 Cole Bemis

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val CoffeeIcon: ImageVector
    get() {
        if (_FontAwesomeCoffee != null) return _FontAwesomeCoffee!!
        
        _FontAwesomeCoffee = ImageVector.Builder(
            name = "coffee",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 640f,
            viewportHeight = 512f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(192f, 384f)
                horizontalLineToRelative(192f)
                curveToRelative(53f, 0f, 96f, -43f, 96f, -96f)
                horizontalLineToRelative(32f)
                curveToRelative(70.6f, 0f, 128f, -57.4f, 128f, -128f)
                reflectiveCurveTo(582.6f, 32f, 512f, 32f)
                horizontalLineTo(120f)
                curveToRelative(-13.3f, 0f, -24f, 10.7f, -24f, 24f)
                verticalLineToRelative(232f)
                curveToRelative(0f, 53f, 43f, 96f, 96f, 96f)
                close()
                moveTo(512f, 96f)
                curveToRelative(35.3f, 0f, 64f, 28.7f, 64f, 64f)
                reflectiveCurveToRelative(-28.7f, 64f, -64f, 64f)
                horizontalLineToRelative(-32f)
                verticalLineTo(96f)
                horizontalLineToRelative(32f)
                close()
                moveToRelative(47.7f, 384f)
                horizontalLineTo(48.3f)
                curveToRelative(-47.6f, 0f, -61f, -64f, -36f, -64f)
                horizontalLineToRelative(583.3f)
                curveToRelative(25f, 0f, 11.8f, 64f, -35.9f, 64f)
                close()
            }
        }.build()
        
        return _FontAwesomeCoffee!!
    }

private var _FontAwesomeCoffee: ImageVector? = null

