package com.green.instagramclone.src.main.home.createfeedorstory

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class TextIndicator : LinearLayout {
    // TODO : TextIndicator구현하기 (게시물, 스토리, 릴스, 라이브)
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)
}