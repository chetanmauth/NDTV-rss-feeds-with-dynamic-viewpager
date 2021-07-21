package com.example.newsfeeds.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsfeeds.Model.NdtvModel;
import com.example.newsfeeds.R;
import com.example.newsfeeds.WebViewDisp;

import java.util.List;


public class NdtvAdapter extends RecyclerView.Adapter<NdtvAdapter.MyViewHolder> {
    Context context;
    List<NdtvModel> data;

    public NdtvAdapter(Context context, List<NdtvModel> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public NdtvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ndtv_listview, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NdtvAdapter.MyViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final NdtvModel current = data.get(position);
        myViewHolder.title.setText(current.title);
        // myViewHolder.link.setText(current.link);
        myViewHolder.updatedAt.setText(current.updatedAt);
        //  myViewHolder.pubDate.setText(current.pubDate);

        Glide.with(context).load(Uri.parse(current.fullimage)).apply(new RequestOptions().override(400,300)).into(holder.fullimage);

        myViewHolder.category.setText(current.category);
        myViewHolder.description.setText(current.description);
        //  myViewHolder.fullimage.setText(current.fullimage);
        myViewHolder.source.setText(current.source);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("key",current.link); // Put anything what you want

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment wv = new WebViewDisp();
                wv.setArguments(bundle);
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                //ft.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                ft.setCustomAnimations(R.anim.slide_in_up,0,0, R.anim.slide_out_up);
                ft.addToBackStack("1");

                ft.replace(R.id.drawer_layout, wv);
                ft.commit();


            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, link, updatedAt, pubDate, category, description, source;
        ImageView fullimage, StoryImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            //link = (TextView) itemView.findViewById(R.id.link);
            updatedAt = (TextView) itemView.findViewById(R.id.updatedat);
            //  pubDate = (TextView) itemView.findViewById(R.id.pubat);
            fullimage = (ImageView) itemView.findViewById(R.id.image);
            category = (TextView) itemView.findViewById(R.id.category);
            description = (TextView) itemView.findViewById(R.id.description);
            //fullimage = (TextView) itemView.findViewById(R.id.fullimage);
            source = (TextView) itemView.findViewById(R.id.source);

        }


    }
}
