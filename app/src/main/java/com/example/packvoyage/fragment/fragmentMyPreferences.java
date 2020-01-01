package com.example.packvoyage.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.packvoyage.R;
import com.example.packvoyage.Singleton.SingletonDao;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.adapterRecyclerView.ActivityTagsAdapter;
import com.example.packvoyage.bindingModel.ProfilePictureToSaveBindingModel;
import com.example.packvoyage.model.ActivityTag;
import com.example.packvoyage.model.User;
import com.example.packvoyage.repository.AccountDao;
import com.example.packvoyage.repository.PackDao;
import com.example.packvoyage.repository.PreferencesDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class fragmentMyPreferences extends Fragment implements ActivityTagsAdapter.OnActivityTagCheckboxClick {
    public static final String TAG = "MY_PREFERENCES";
    public static final int MAX_ACTI_TAG_PREF = 3;
    private static final int IMAGE_REQUEST = 99;
    private static final String UPLOAD_PRESET = "dk0vdqaf";
    private static final String CLOUD_NAME = "etu36459";

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
    @BindView(R.id.my_profile_picture)
    public ImageView my_profile_picture;
    @BindView(R.id.my_username)
    public TextView my_username;
    private AccountDao accountDao;
    private Context context;
    private User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_my_preferences, container, false);
        ButterKnife.bind(this, v);

        context = getContext();
        accountDao = SingletonDao.getAccountDao();

        packVM.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            currentUser = user;
            my_username.setText(user.getUsername());
            Glide.with(Objects.requireNonNull(getContext())).load(user.getProfile_pic_uri()).apply(RequestOptions.circleCropTransform()).into(my_profile_picture);
        });

        Map config = new HashMap();
        config.put("cloud_name", CLOUD_NAME);
        try{
            MediaManager.init(Objects.requireNonNull(getContext()), config);
        }
        catch (IllegalStateException e){ }

        my_profile_picture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_REQUEST);
            }
        });

        packVM.getAllActivityTags().observe(getViewLifecycleOwner(), tags -> initRecyclerView(tags));
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        packVM = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PackDetailVM.class);
        packDao = SingletonDao.getPackDao();
        preferencesDao = SingletonDao.getPreferencesDao();

        preferencesDao.loadAllPreferences(packVM, getContext());
        sharedPref = getActivity().getSharedPreferences(getResources().getString(R.string.SHARED_PREF_FILE_KEY), Context.MODE_PRIVATE);
        savedActivityTags = sharedPref.getAll();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            onImageUploaded onImageUploaded = new onImageUploaded();
            MediaManager.get().upload(imageUri).unsigned(UPLOAD_PRESET).callback(onImageUploaded).dispatch();
        }
    }

    private class onImageUploaded implements UploadCallback {

        @Override
        public void onStart(String requestId) {

        }

        @Override
        public void onProgress(String requestId, long bytes, long totalBytes) {

        }

        @Override
        public void onSuccess(String requestId, Map resultData) {
            String url = (String)resultData.get("secure_url");
            currentUser.setProfile_pic_uri(url);
            packVM.setCurrentUser(currentUser);
            accountDao.setNewUserProfilePicture(new ProfilePictureToSaveBindingModel(url, null,
                            null, currentUser.getUser_id(), null), context, packVM);
        }

        @Override
        public void onError(String requestId, ErrorInfo error) {

        }

        @Override
        public void onReschedule(String requestId, ErrorInfo error) {

        }
    }
}
