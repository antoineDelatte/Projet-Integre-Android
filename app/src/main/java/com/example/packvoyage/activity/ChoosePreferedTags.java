package com.example.packvoyage.activity;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.adapterRecyclerView.TagsToSelectAdapter;
import com.example.packvoyage.model.Tag;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoosePreferedTags extends AppCompatActivity {


    @BindView(R.id.tags_to_select)
    public RecyclerView tagsToSelectRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_prefered_tags);

        ButterKnife.bind(this);
        TagsToSelectAdapter adapter = new TagsToSelectAdapter();
        tagsToSelectRecyclerView.setHasFixedSize(true);
        tagsToSelectRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        tagsToSelectRecyclerView.setAdapter(adapter);
    }

    private class getUserTask extends AsyncTask<Tag, Void, Void>{


        @Override
        protected Void doInBackground(Tag... tags) {
            return null;
        }




    }





}
