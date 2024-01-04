package top.wangchenyan.common.widget.dialog

/**
 * 按钮点击
 * @return 是否消息点击事件，true: 消费，弹窗不会关闭，false: 不消费，弹窗将会关闭
 */
typealias OnButtonClickListener = (dialog: CenterDialog, which: Int) -> Unit