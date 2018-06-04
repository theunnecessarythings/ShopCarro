package com.example.meghamoondra.navigationbar.view.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.meghamoondra.navigationbar.R;

import java.util.List;

public class SearchSuggestionAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mSuggestions;

    public SearchSuggestionAdapter(Context mContext, List<String> mSuggestions) {
        super();
        this.mContext = mContext;
        this.mSuggestions = mSuggestions;
    }

    @Override
    public int getCount() {
        return mSuggestions.size();
    }

    @Override
    public Object getItem(int position) {
        return mSuggestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_search_suggestion, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvSuggestion = convertView.findViewById(R.id.tv_suggestion);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tvSuggestion.setText(mSuggestions.get(position));
        return convertView;
    }

    public class ViewHolder {
        public TextView tvSuggestion;

        public TextView getTvSuggestion() {
            return tvSuggestion;
        }

        public void setTvSuggestion(TextView tvSuggestion) {
            this.tvSuggestion = tvSuggestion;
        }
    }
}
