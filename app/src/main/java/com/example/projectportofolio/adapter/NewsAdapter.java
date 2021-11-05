package com.example.projectportofolio.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectportofolio.NewsDetail;
import com.example.projectportofolio.R;
import com.example.projectportofolio.WebsiteView;
import com.example.projectportofolio.model.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ListViewHolder> implements Filterable {
    private ArrayList<NewsModel> dataList;
    private ArrayList<NewsModel> dataListFiltered;
    private Context mContext;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String character = charSequence.toString();
                if (character.isEmpty()){
                    dataListFiltered = dataList;
                } else {
                    ArrayList<NewsModel> dataListfilter = new ArrayList<>();
                    for (NewsModel row: dataList){
                        if (row.getNewsTitle().toLowerCase().contains(character.toLowerCase())){
                            dataListfilter.add(row);
                        }
                    }
                    dataListFiltered = dataListfilter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dataListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataListFiltered = (ArrayList<NewsModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public NewsAdapter(Context mContext, ArrayList<NewsModel> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.dataListFiltered = dataList;
    }

    @NonNull
    @Override
    public NewsAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ListViewHolder holder, int position) {
        final NewsModel model = dataListFiltered.get(position);
        holder.txt_title.setText(model.getNewsTitle());
        holder.txt_desc.setText(model.getNewsDesc());
        if (dataList.get(position).getNewsImg().isEmpty()) {
            holder.img_news.setImageResource(R.drawable.ic_baseline_error_24);
        } else{
            Picasso.get().load(dataList.get(position).getNewsImg()).into(holder.img_news);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewsDetail.class);
                intent.putExtra("title",model.getNewsTitle());
                intent.putExtra("author",model.getNewsAuthor());
                intent.putExtra("publish",model.getNewsPublish());
                intent.putExtra("img",model.getNewsImg());
                intent.putExtra("content",model.getNewsContent());
                intent.putExtra("url",model.getNewsUrl());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataListFiltered.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_title, txt_desc;
        private ImageView img_news;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.tv_title_list);
            txt_desc = itemView.findViewById(R.id.tv_desc_list);
            img_news = itemView.findViewById(R.id.img_list);
        }
    }
}
