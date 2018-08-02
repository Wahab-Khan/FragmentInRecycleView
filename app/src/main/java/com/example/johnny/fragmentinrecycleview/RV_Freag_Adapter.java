package com.example.johnny.fragmentinrecycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RV_Freag_Adapter extends RecyclerView.Adapter<RV_Freag_Adapter.FragViewHolder> {

    FragmentManager fragmentManager;
    Context context;
    List<ImageModel> data;


    public RV_Freag_Adapter(Context context ,List<ImageModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public FragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_item, parent, false);

        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        return new FragViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragViewHolder holder, int position) {

        Frag fragment = Frag.newInstance(data);
        fragmentManager.beginTransaction().replace(R.id.fram_layout, fragment).commit();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class FragViewHolder extends RecyclerView.ViewHolder{

        public FragViewHolder(View itemView) {
            super(itemView);
        }
    }
}
