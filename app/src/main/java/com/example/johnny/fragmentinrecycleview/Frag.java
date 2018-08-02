package com.example.johnny.fragmentinrecycleview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Frag extends Fragment implements StoryStatusView.UserInteractionListener,ViewSwitcher.ViewFactory{

    //custom progress bar
    StoryStatusView storyStatusView;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private StoryAdapter adapter;

    //for create image animation on background of stroy
    ImageSwitcher imageSwitcher;

    //track the current position of recycle view
    int currentPosition = 0;
    //check either its auto scrolled or scrolled by user
    boolean isUserScrolled = false,isautoScrolled = false;
    //for data set for story
    private List<ImageModel> students = new ArrayList<>();

    public static Frag newInstance(List<ImageModel> data) {

        Bundle args = new Bundle();

        Frag fragment = new Frag();
//        fragment.setArguments(data);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        storyStatusView = view.findViewById(R.id.storiesStatus);

        imageSwitcher = (ImageSwitcher) view.findViewById(R.id.image_switcher);
        imageSwitcher.setFactory(Frag.this);

        recyclerView = view.findViewById(R.id.stroy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(7);

        //create affect live view pager on recycle view
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        //make recycle view horizontal
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        init();

        storyStatusView.playStories();
        storyStatusView.pause();

        recyclerView.setAdapter(adapter);
        return view;
    }


    protected void init(){
        //seting data
        students.add(new ImageModel(R.drawable.bad_idea,"this is mySource",
                "this is first shit .. bla bla bla bla qbla ablab alb alb alba"));
        students.add(new ImageModel(R.drawable.shit,"this is mySource",
                "this is 2nd shit "));
        students.add(new ImageModel(R.drawable.ic_launcher_background,"this is mySource",
                "this is 3rd shit .. bla bla "));
        students.add(new ImageModel(R.drawable.shit,"this is mySource",
                "this is 4th shit .. bla bla bla bla qbla ablab alb alb alba  qbla ablab alb alb alba"));


        //set adapter
        adapter = new StoryAdapter(getActivity(),students);
//        imageSwitcher.setImageResource(students.get(0).getImage_drawable());
        Glide
                .with(getActivity())
                .load(students.get(0).getImage_drawable())
                .into((ImageView) imageSwitcher.getCurrentView());

        //status View
        storyStatusView.setUserInteractionListener(Frag.this);
        storyStatusView.setStoriesCount(students.size());
        storyStatusView.setStoryDuration(7000L);



        // scroll listener for restart animation on page change
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (RecyclerView.SCROLL_STATE_DRAGGING == newState){
                    isautoScrolled = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int pos = layoutManager.findFirstVisibleItemPosition();
                Log.i("Position",pos+"");

                //picture swaping animation
                final Animation LeftIn = AnimationUtils.loadAnimation(getActivity(), R.anim.left_to_right_in);
                final Animation LeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.left_to_right_out);

                final Animation RightIn = AnimationUtils.loadAnimation(getActivity(), R.anim.right_to_left_in);
                final Animation RightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.right_to_left_out);

                if (!isautoScrolled) {
                    if (currentPosition != pos) {
                        isUserScrolled = true;
                        //if page scrolled forward
                        if (currentPosition < pos) {
                            currentPosition = pos;
                            storyStatusView.skip();
                            imageSwitcher.setInAnimation(RightOut);
                            imageSwitcher.setOutAnimation(RightIn);
                            Glide
                                    .with(getActivity())
                                    .load(students.get(pos).getImage_drawable())
                                    .into((ImageView) imageSwitcher.getCurrentView());
//                            imageSwitcher.setImageResource(students.get(pos).getImage_drawable());
                        }//if page scrolled backword
                        else if (currentPosition > pos) {
                            currentPosition = pos;
                            storyStatusView.reverse();
                            imageSwitcher.setInAnimation(LeftIn);
                            imageSwitcher.setOutAnimation(LeftOut);
//                            imageSwitcher.setImageResource(students.get(pos).getImage_drawable());
                            Glide
                                    .with(getActivity())
                                    .load(students.get(pos).getImage_drawable())
                                    .into((ImageView) imageSwitcher.getCurrentView());
                        }
                    }
                }
                tryAnimation(layoutManager.findViewByPosition(pos));
            }

            //set Animation
            private void tryAnimation(View viewByPosition) {
                //animation for icon
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
                animation.setStartOffset(1500);
                //animation for source
                Animation RtoL = AnimationUtils.loadAnimation(getActivity(), R.anim.right_to_left);
                RtoL.setStartOffset(1500);
                //fade in the text
                Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
                a.setStartOffset(2000);

                //zoom animation for background image
                Animation animZoomIn = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
                animZoomIn.setStartOffset(700);

                if (viewByPosition != null) {
                    viewByPosition.findViewById(R.id.icon).startAnimation(animation);
                    viewByPosition.findViewById(R.id.source).startAnimation(RtoL);
                    viewByPosition.findViewById(R.id.popup_text).startAnimation(a);
                    imageSwitcher.startAnimation(animZoomIn);

                }
            }
        });
    }
    @Override
    public View makeView() {
        ImageView myView = new ImageView(getActivity());
        myView.setScaleType(ImageView.ScaleType.FIT_XY);

        FrameLayout.LayoutParams params = new ImageSwitcher.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        myView.setLayoutParams(params);
        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
        storyStatusView.resume();
    }

    @Override
    public void onNext() {
        if (!isUserScrolled) {
            currentPosition++;
            recyclerView.smoothScrollToPosition(currentPosition);
            Animation RightIn = AnimationUtils.loadAnimation(getActivity(), R.anim.right_to_left_in);
            Animation RightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.right_to_left_out);
            imageSwitcher.setInAnimation(RightOut);
            imageSwitcher.setOutAnimation(RightIn);
            Glide
                    .with(getActivity())
                    .load(students.get(currentPosition).getImage_drawable())
                    .transition(GenericTransitionOptions.with(R.anim.right_to_left_out))
                    .transition(GenericTransitionOptions.with(R.anim.right_to_left_in))
                    .into((ImageView) imageSwitcher.getCurrentView());
            isautoScrolled = true;
        }
        isUserScrolled = false;
    }

    @Override
    public void onPrev() {
        isUserScrolled = false;
    }

    @Override
    public void onComplete() {
        Animation LeftIn = AnimationUtils.loadAnimation(getActivity(), R.anim.left_to_right_in);
        Animation LeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.left_to_right_out);
        imageSwitcher.setInAnimation(LeftIn);
        imageSwitcher.setOutAnimation(LeftOut);
        imageSwitcher.setImageResource(students.get(0).getImage_drawable());
        storyStatusView.playStories();
        currentPosition = 0;
        recyclerView.scrollToPosition(0);
        isUserScrolled = false;
        isautoScrolled = false;
    }
}
