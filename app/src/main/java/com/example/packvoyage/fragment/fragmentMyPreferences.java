package com.example.packvoyage.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.packvoyage.R;
import com.example.packvoyage.Singleton.SingletonDao;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.adapterRecyclerView.ActivityTagsAdapter;
import com.example.packvoyage.model.ActivityTag;
import com.example.packvoyage.repository.PackDao;
import com.example.packvoyage.repository.PreferencesDao;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class fragmentMyPreferences extends Fragment implements ActivityTagsAdapter.OnActivityTagCheckboxClick {
    public static final String TAG = "MY_PREFERENCES";

    public static final int MAX_ACTI_TAG_PREF = 3;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private PackDetailVM packVM;
    private PackDao packDao;
    @BindView(R.id.activity_tags_rv)
    public RecyclerView activity_tags_rv;
    private Map<String, ?> savedActivityTags;
    private ActivityTagsAdapter tagsAdapter;
    private boolean checkboxesLocked = false;
    private PreferencesDao preferencesDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_my_preferences, container, false);
        ButterKnife.bind(this, v);

        packVM.getAllActivityTags().observe(getViewLifecycleOwner(), tags -> initRecyclerView(tags));
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        packVM = ViewModelProviders.of(getActivity()).get(PackDetailVM.class);
        packDao = SingletonDao.getPackDao();
        preferencesDao = SingletonDao.getPreferencesDao();

        preferencesDao.loadAllPreferences(packVM, getContext());
        sharedPref = getActivity().getSharedPreferences(getResources().getString(R.string.SHARED_PREF_FILE_KEY), Context.MODE_PRIVATE);
        savedActivityTags = sharedPref.getAll();
        Log.i("Trip4", "size : " + savedActivityTags.size());
    }

    private void initRecyclerView(ArrayList<ActivityTag>tags){
        tagsAdapter = new ActivityTagsAdapter(tags, this, savedActivityTags, savedActivityTags.size() == MAX_ACTI_TAG_PREF);
        activity_tags_rv.setHasFixedSize(true);
        activity_tags_rv.setAdapter(tagsAdapter);
        activity_tags_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        checkboxesLocked = savedActivityTags.size() == MAX_ACTI_TAG_PREF;
    }

    @Override
    public void onActivityTagCheckboxClick(int tagId, String tagName, boolean isSelected) {
        if(isSelected){
            int sharedPrefSize = sharedPref.getAll().size();
            if(sharedPrefSize < MAX_ACTI_TAG_PREF){
                editor = sharedPref.edit();
                editor.putInt(tagName, tagId);
                editor.apply();
                sharedPrefSize += 1;
                if(sharedPrefSize == MAX_ACTI_TAG_PREF){
                    tagsAdapter.disableUncheckedCheckboxes();
                    checkboxesLocked = true;
                }
            }
            else {
                Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getResources().getString(R.string.acti_tag_pref_limit), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if(sharedPref.contains(tagName)){ // this should always be true but I check it for safety
                if(checkboxesLocked){
                    tagsAdapter.reactivateCheckboxes();
                    checkboxesLocked = false;
                }
                editor = sharedPref.edit();
                editor.remove(tagName);
                editor.apply();
            }
        }
    }
}
