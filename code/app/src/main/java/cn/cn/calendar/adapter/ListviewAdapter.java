package cn.cn.calendar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.cn.calendar.R;

/**
 * author weiwei on 2016/11/18 0018 17:32.
 */
public class ListviewAdapter extends BaseAdapter {
    private Context mContext;
    private String data;

    public ListviewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : 8;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.listview_item, null);
            holder = new ViewHolder();
            holder.msg = (TextView) convertView.findViewById(R.id.msg);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.msg.setText(data);
        convertView.setTag(holder);
        return convertView;
    }

    public void setData(String text) {
        data = text;
        notifyDataSetChanged();
    }

    class ViewHolder {
        public TextView msg;
    }
}
