package com.example.chivas.testorm.adapter.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chivas.testorm.R;
import com.example.chivas.testorm.adapter.base.CommonBaseAdapter;

public class SpinnerEditTextListAdapter extends CommonBaseAdapter<String> {

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClickItem(String item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SpinnerEditTextListAdapter(Context context) {
        super(context);
    }

    static class ViewHolder {
        TextView tvItem;

        ViewHolder(View convertView) {
            tvItem = (TextView) convertView.findViewById(R.id.tvItem);
        }

        private void setParamValesFromBean(String item) {
            tvItem.setText(item);
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_spn_edt_list_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String item = mList.get(position);
        holder.setParamValesFromBean(item);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    String item = mList.get(position);
                    onItemClickListener.onClickItem(TextUtils.isEmpty(item) ? "" : item);
                }
            }
        });
        return convertView;
    }
}