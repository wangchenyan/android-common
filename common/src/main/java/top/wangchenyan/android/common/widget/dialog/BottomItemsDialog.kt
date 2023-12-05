package top.wangchenyan.android.common.widget.dialog

import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.LinearLayout
import top.wangchenyan.android.common.R
import top.wangchenyan.android.common.databinding.CommonDialogBottomItemsItemBinding

/**
 * Created by wcy on 2019-09-14.
 */
class BottomItemsDialog internal constructor(builder: BottomItemsDialogBuilder) :
    BaseBottomDialog(builder.context) {
    private val items: List<CharSequence>?
    private val selectedPosition: Int
    private val isCancelable: Boolean
    private val onClickListener: DialogInterface.OnClickListener?

    init {
        items = builder.items
        selectedPosition = builder.selectedPosition
        isCancelable = builder.isCancelable
        onClickListener = builder.onClickListener

        setContentView(R.layout.common_dialog_bottom_items)
        initItems()
    }

    private fun initItems() {
        if (items.isNullOrEmpty()) return
        val itemsContainer: LinearLayout = findViewById(R.id.itemsContainer)
        itemsContainer.removeAllViews()
        items.forEachIndexed { index, charSequence ->
            val itemBinding = CommonDialogBottomItemsItemBinding.inflate(
                LayoutInflater.from(context),
                itemsContainer,
                true
            )
            itemBinding.tvItemText.text = charSequence
            itemBinding.root.isSelected = (index == selectedPosition)
            itemBinding.root.setOnClickListener {
                dismiss()
                onClickListener?.onClick(this, index)
            }
        }
    }
}