package com.example.johnny.fragmentinrecycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StudentViewHolder> {

    private List<ImageModel> students;

    Animation animZoomIn, a;

    private int lastPosition = -1;

    Context context;

    public StoryAdapter(Context context, List<ImageModel> students) {
        this.students = students;
        this.context = context;
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView sourceText, discription, popUpText;
        ImageView icon, soundGif;

        //        CardView cardView;
        private StudentViewHolder(View itemView) {
            super(itemView);
            sourceText = itemView.findViewById(R.id.source);
            discription = itemView.findViewById(R.id.discription);
            popUpText = itemView.findViewById(R.id.popup_text);
            icon = itemView.findViewById(R.id.icon);
            soundGif = itemView.findViewById(R.id.sound_gif);
        }

        public void clearAnimation() {
            itemView.clearAnimation();
        }
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        ImageModel student = students.get(position);
        holder.sourceText.setText(student.getSource());
        holder.discription.setText(student.getDiscription());
        holder.icon.setImageResource(students.get(position).getImage_drawable());
        holder.popUpText.setText(student.getDiscription());

        //load and show sound gif when its video
        Glide
                .with(context)
                .load(R.drawable.equilizer)
                .into(holder.soundGif);

        // Here you apply the animation when the view is bound for only first element
        //after that animation always trigred on scrolled form where recyle view initilized
//
//        if (position == 0) {
//            setAnimation(holder.itemView);
//        }
    }

    private void setAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);
        // If the bound view wasn't previously displayed on screen, it's animated
//            animZoomIn = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
//        animZoomIn.setAnimationListener(this);
//            imageView.startAnimation(animZoomIn);

        //text fading animation
        Animation a = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        a.setStartOffset(3000);
        viewToAnimate.findViewById(R.id.popup_text).startAnimation(a);

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale);
        anim.setStartOffset(1500);
        viewToAnimate.findViewById(R.id.icon).startAnimation(anim);

        Animation RtoL = AnimationUtils.loadAnimation(context, R.anim.right_to_left);
        RtoL.setStartOffset(1500);
        viewToAnimate.findViewById(R.id.source).startAnimation(RtoL);

    }

    @Override
    public void onViewRecycled(@NonNull StudentViewHolder holder) {
        super.onViewRecycled(holder);
        setAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull StudentViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }
}
