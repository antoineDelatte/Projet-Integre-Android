package com.example.packvoyage.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.packvoyage.R;
import com.example.packvoyage.adapterRecyclerView.TagsToSelectAdapter;
import com.example.packvoyage.model.Tag;
import com.example.packvoyage.viewModel.TagViewModel;

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
        TagViewModel viewModel = new ViewModelProviders.of(ChoosePreferedTags.this).get(TagViewModel.class);
        TagsToSelectAdapter adapter = new TagsToSelectAdapter();
        viewModel.getTags().observe(this, adapter::setTags);
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
