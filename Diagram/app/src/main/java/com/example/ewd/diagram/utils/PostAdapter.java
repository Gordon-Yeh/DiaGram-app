package com.example.ewd.diagram.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.ewd.diagram.R;
import com.example.ewd.diagram.model.local.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder> {

    //Data store
    private List<Post> mPostList;

    private Context context;

    private int[] colors = {R.color.colorPrimary};

    //Handling Clicks
    public interface ListItemClickListener {

        void onListItemClick(Post post);

    }

    private ListItemClickListener mOnclickListener;


    public PostAdapter(ListItemClickListener listener , Context context) {

        mOnclickListener = listener;
        this.context = context;

    }


    //ViewHolder Class for normal
    public class PostAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Views
        public @BindView(R.id.title)
        TextView titleTextView;
        public @BindView(R.id.body)
        TextView bodyTextView;
        public @BindView(R.id.date)
        TextView dateTextView;
        @BindView(R.id.user_type_image_view)
        ImageView userTypeImageView;


        public Post post;

        public PostAdapterViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            mOnclickListener.onListItemClick(mPostList.get(position));

        }


    }

    @NonNull
    @Override
    public PostAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.post_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new PostAdapterViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapterViewHolder holder, int position) {

        Post post = mPostList.get(position);
        holder.post = post;
        holder.titleTextView.setText(post.getTitle());
        holder.bodyTextView.setText(post.getBody());

        //TODO -> Change icon and color according to user type
        //Circular Icon
        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect( "P",
                        context.getResources().getColor(colors[0]), 70);
        holder.userTypeImageView.setImageDrawable(drawable);

    }
    @Override
    public int getItemCount () {

        if (mPostList == null)
            return 0;
        else
            return mPostList.size();
    }


    public void setPostsData (List<Post> postList) {

        mPostList = postList;
        notifyDataSetChanged();
    }

    public List<Post> getPosts () {
        return mPostList;
    }
}
