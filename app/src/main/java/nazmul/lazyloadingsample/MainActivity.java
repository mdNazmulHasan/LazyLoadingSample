package nazmul.lazyloadingsample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private ArrayList< User > mUsers = new ArrayList< >();
    private UserAdapter mUserAdapter;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("LoadMoreRecycleView");
        for (int i = 0; i < 30; i++) {
            User user = new User();
            user.setName("Name " + i);
            user.setEmail("alibaba" + i + "@gmail.com");
            mUsers.add(user);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUserAdapter = new UserAdapter(mRecyclerView,mUsers,this);
        mRecyclerView.setAdapter(mUserAdapter);
        mUserAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override public void onLoadMore() {
                Log.e("haint", "Load More");
                mUsers.add(null);
                mUserAdapter.notifyItemInserted(mUsers.size() - 1);
                //Load more data for reyclerview
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        Log.e("haint", "Load More 2");
                        //Remove loading item
                        mUsers.remove(mUsers.size() - 1);
                        mUserAdapter.notifyItemRemoved(mUsers.size());
                        //Load data
                        int index = mUsers.size();
                        int end = index + 20;
                        for (int i = index; i < end; i++) {
                            User user = new User();
                            user.setName("Name " + i);
                            user.setEmail("alibaba" + i + "@gmail.com");
                            mUsers.add(user);
                        }
                        mUserAdapter.notifyDataSetChanged();
                        mUserAdapter.setLoaded();
                    }
                }, 5000);
            }
        });
    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvEmailId;
        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvEmailId = (TextView) itemView.findViewById(R.id.tvEmailId);
        }
    }
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

}
