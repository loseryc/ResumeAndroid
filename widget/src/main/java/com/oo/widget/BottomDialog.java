package com.oo.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class BottomDialog {
    private Dialog dialog;
    private TextView txt_title;
    private View txt_title_layout;
    private LinearLayout lLayout_content;
    private List<SheetItem> sheetItemList;
    private boolean hasShow;

    private final Context context;
    private final DisplayMetrics display;

    public BottomDialog(Context context) {
        this.context = context;
        display = context.getResources().getDisplayMetrics();
    }

    public BottomDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_bottom, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.widthPixels);

        // 获取自定义Dialog布局中的控件
        lLayout_content = view.findViewById(R.id.lLayout_content);
        txt_title_layout = view.findViewById(R.id.txt_title_layout);
        txt_title = view.findViewById(R.id.txt_title);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.START | Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.x = 0;
            lp.y = 0;
            dialogWindow.setAttributes(lp);
        }
        return this;
    }

    public BottomDialog setTitle(String title) {
        txt_title_layout.setVisibility(View.VISIBLE);
        txt_title.setText(title);
        return this;
    }

    public BottomDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public BottomDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * @param strItem  条目名称
     *                 条目字体颜色，设置null则默认蓝色
     * @param listener listener
     * @return ActionSheetDialog
     */
    public BottomDialog addSheetItem(String strItem,
                                     OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<>();
        }
        sheetItemList.add(new SheetItem(strItem, listener));
        return this;
    }

    /**
     * 设置条目布局
     */
    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();
        int margin = (int) (8 * Resources.getSystem().getDisplayMetrics().density);


        // 循环添加条目
        for (int i = 1; i <= size; i++) {
            final int index = i;
            SheetItem sheetItem = sheetItemList.get(i - 1);
            String strItem = sheetItem.name;
            final OnSheetItemClickListener listener = sheetItem.itemClickListener;

            TextView textView = new TextView(context);
            textView.setText(strItem);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
            textView.setGravity(Gravity.CENTER);

            // 字体颜色
            textView.setTextColor(Color.BLACK);

            // 高度
            float scale = context.getResources().getDisplayMetrics().density;
            int height = (int) (45 * scale + 0.5f);
            textView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, height));

            // 点击事件
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(index);
                    dialog.dismiss();
                }
            });

            View line = new View(context);
            line.setBackgroundColor(Color.parseColor("#e1e1e1"));
            LayoutParams lineParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT, 1);
            lineParams.setMargins(margin, 0, margin, 0);
            line.setLayoutParams(lineParams);
            lLayout_content.addView(textView);
            lLayout_content.addView(line);
        }
    }

    public void show() {
        if (!hasShow) {
            setSheetItems();
            hasShow = true;
        }
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }

    public static class SheetItem {
        private final String name;
        private final OnSheetItemClickListener itemClickListener;

        public SheetItem(String name,
                         OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.itemClickListener = itemClickListener;
        }
    }
}
