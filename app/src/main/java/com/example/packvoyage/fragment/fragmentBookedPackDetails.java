package com.example.packvoyage.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.packvoyage.R;
import com.example.packvoyage.Singleton.SingletonDao;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.activity.IMainActivity;
import com.example.packvoyage.adapterRecyclerView.CommentsAdapter;
import com.example.packvoyage.bindingModel.CommentCreationBindingModel;
import com.example.packvoyage.model.Comment;
import com.example.packvoyage.model.User;
import com.example.packvoyage.repository.PackDao;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class fragmentBookedPackDetails extends Fragment implements CommentsAdapter.OnCommentClick {

    public static final String TAG = "BOOKED_PACK_DETAILS";
    public static final String SECRET_CODE = "ROLL";

    private PackDetailVM packVM;
    private PackDao packDao;
    private ArrayList<Comment>comments = new ArrayList<>();
    private User currentUser;
    private RecyclerView.Adapter rVAdapter;

    @BindView(R.id.comments_rv)
    public RecyclerView commentsRV;
    @BindView(R.id.pack_name)
    public TextView packName;
    @BindView(R.id.add_comment_section)
    public TextInputEditText add_comment_section;
    @BindView(R.id.comment_options)
    public ConstraintLayout commentOptionsLayout;
    @BindView(R.id.delete_comment_button)
    public Button delete_comment_button;
    @BindView(R.id.booked_pack_details)
    public LinearLayout booked_pack_details;
    private int selectedCommentPosition;
    public IMainActivity parent;
    private Integer packId;

    public fragmentBookedPackDetails() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booked_pack_details, container, false);
        ButterKnife.bind(this, view);

        commentOptionsLayout.setVisibility(View.GONE);
        delete_comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSelectedComment();
            }
        });

        packVM.getSelectedBookedPackId().observe(getViewLifecycleOwner(), id -> packId = id);

        packVM.getRegisterStatus().observe(getViewLifecycleOwner(), status ->{
            if(status == null)
                return;
            if(status != 201 && status != 200){
                Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getResources().getString(R.string.comment_creation_failed), Toast.LENGTH_LONG).show();
            }
            packVM.setRegisterStatus(null);
        });

        packVM.getSelectedBookedPackId().observe(getViewLifecycleOwner(), id -> {
            packDao.loadComments(packVM, id, getContext());
        });

        packVM.getSelectedBookedPackName().observe(getViewLifecycleOwner(), name -> packName.setText(name));
        packVM.getSelectedBookedPackComments().observe(getViewLifecycleOwner(), comments -> {
            this.comments = comments;
            initRecyclerView(comments);
        });
        packVM.getCurrentUser().observe(getViewLifecycleOwner(), user -> currentUser = user);
        add_comment_section.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    String message = Objects.requireNonNull(add_comment_section.getText()).toString();
                    if(message.length() != 0){
                        if(message.equals("roll")){
                            parent.changeFragment(fragmentBookedPackDetails.SECRET_CODE);
                        }
                        else{
                            comments.add(0, new Comment(message, currentUser));
                            if(rVAdapter == null){
                                initRecyclerView(comments);
                            }
                            rVAdapter.notifyItemInserted(0);
                            rVAdapter.notifyItemRangeChanged(0, comments.size());
                            packDao.registerNewComment(new CommentCreationBindingModel(0, message, packId, currentUser.getUser_id()), getContext(), packVM);
                        }
                    }
                    else{
                        Toast.makeText(getContext(), getResources().getString(R.string.comment_is_empty), Toast.LENGTH_SHORT).show();
                    }
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    add_comment_section.getText().clear();
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    public void initRecyclerView(ArrayList<Comment> comments){
        commentsRV.setHasFixedSize(true);
        commentsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        rVAdapter = new CommentsAdapter(comments, getContext(), this);
        commentsRV.setAdapter(rVAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packVM = ViewModelProviders.of(getActivity()).get(PackDetailVM.class);
        packDao = SingletonDao.getPackDao();
    }

    @Override
    public void onCommentClick(String commentOwnerId, int selectedCommentPosition) {
        this.selectedCommentPosition = selectedCommentPosition;
        if(currentUser == null)
            return;
        if(currentUser.getUser_id().equals(commentOwnerId)){
            commentOptionsLayout.setVisibility(View.VISIBLE);
        }
    }

    public void removeSelectedComment(){
        Comment commentToDelete = comments.get(selectedCommentPosition);

        comments.remove(selectedCommentPosition);
        rVAdapter.notifyItemRemoved(selectedCommentPosition);
        rVAdapter.notifyItemRangeChanged(selectedCommentPosition, comments.size());
        commentOptionsLayout.setVisibility(View.GONE);
        packDao.deleteComment(commentToDelete.getId(), getContext(), packVM);
    }

    public boolean backPressed(){
        if(commentOptionsLayout.getVisibility() == View.VISIBLE){
            commentOptionsLayout.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            parent = (IMainActivity)context;
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
