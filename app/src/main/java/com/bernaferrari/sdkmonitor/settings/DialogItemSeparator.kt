package com.bernaferrari.sdkmonitor.settings

import com.bernaferrari.sdkmonitor.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.settings_item_separator.*

/**
 * Used to create a separator between settings items.
 */
class DialogItemSeparator(val title: String) : Item() {

    override fun getLayout(): Int = R.layout.settings_item_separator

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text = title
    }
}
