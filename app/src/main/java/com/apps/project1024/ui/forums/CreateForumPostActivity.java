package com.apps.project1024.ui.forums;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.apps.project1024.R;
import com.apps.project1024.databinding.ActivityCreateForumPostBinding;
import com.apps.project1024.models.ForumPost;
import com.apps.project1024.models.User;
import com.apps.project1024.ui.articles.CreateArticleActivity;
import com.apps.project1024.ui.home.HomeActivity;
import com.apps.project1024.viewmodels.ForumsViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.os.Build.VERSION_CODES.M;
import static com.apps.project1024.utils.Utils.displayErrorMessage;
import static com.apps.project1024.utils.Utils.displaySuccessMessage;

public class CreateForumPostActivity extends AppCompatActivity {

    private ActivityCreateForumPostBinding mBinding;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int OPEN_IMAGE = 200;
    private String[] storagePermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Uri selectedPhotoUri = null;
    private String selectedForum = "";
    private ProgressDialog mProgress;
    private ForumsViewModel mViewModel;
    private FirebaseUser mCurrentUser;

    @RequiresApi(api = M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCreateForumPostBinding.inflate(getLayoutInflater());

        setContentView(mBinding.getRoot());
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Post to a forum");
            ab.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        if (intent.hasExtra("bundle")) {
            Bundle bundle = intent.getBundleExtra("bundle");
            User user = bundle.getParcelable("profile");
            if (user != null) {
                mBinding.tvName.setText(user.getName());

            }
        }
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mProgress = new ProgressDialog(this);
        mViewModel = new ViewModelProvider(this).get(ForumsViewModel.class);
        if (!areStoragePermissionsGranted()) {
            requestStoragePermissions();
        }

        Glide.with(this).load(R.drawable.person).apply(RequestOptions.circleCropTransform())
                .into(mBinding.profilePhoto);

        mBinding.spinnerForum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    selectedForum = "growth";

                } else if (i==2) {
                    selectedForum = "elevate";
                } else if (i==3) {
                    selectedForum = "empower";

                } else if (i==4) {
                    selectedForum = "konnect";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedForum = "";
            }
        });

        mBinding.btnUploadImage.setOnClickListener(view -> {
            openImageChooser();
        });
        mBinding.btnCreatePost.setOnClickListener(view -> {
            if (validateInput()) {
                ForumPost post = new ForumPost(
                        "",
                        selectedForum,
                        selectedForum + " Forum Post",
                        mBinding.txtPostContent.getText().toString().trim(),
                        "",
                        mCurrentUser.getUid(),
                        new Date().getTime()

                );
                mViewModel.createPost(post, selectedPhotoUri);
            }
        });

        mViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                showProgress("Creating post please wait...");
            } else {
                hideProgress();
            }
        });

        mViewModel.getSuccessMsg().observe(this, s -> {
            if (!s.isEmpty()) {
                displaySuccessMessage(getApplicationContext(), s);
                mViewModel.resetValues();
                onBackPressed();
            }
        });

        mViewModel.getErrorMsg().observe(this, s -> {
            if (!s.isEmpty()) {
                displayErrorMessage(getApplicationContext(), s);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showProgress(String message) {
        mProgress.setMessage(message);
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();
    }

    private void hideProgress() {
        if (mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }

    @RequiresApi(api = M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(permissions[i])) {
                        requestStoragePermissions();
                    }
                    return;
                } else {
                    openImageChooser();
                }
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            selectedPhotoUri = uri;
            assert uri != null;

            Glide.with(CreateForumPostActivity.this)
                    .load(uri)
                    .into(mBinding.selectedPhoto);
            mBinding.selectedPhoto.setVisibility(View.VISIBLE);

        } else {
            mBinding.selectedPhoto.setVisibility(View.GONE);
        }
    }


    @RequiresApi(api = M)
    private void requestStoragePermissions() {
        List<String> remainingPermissions = new ArrayList<>();
        for (String permission : storagePermissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                remainingPermissions.add(permission);
            }
        }
        requestPermissions(remainingPermissions.toArray(new String[remainingPermissions.size()]), PERMISSION_REQUEST_CODE);
    }

    @RequiresApi(api = M)
    private boolean areStoragePermissionsGranted() {
        for (String permission : storagePermissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }


    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select A Photo"), OPEN_IMAGE);
    }

    private boolean validateInput() {
        boolean isValid = true;
        if (selectedForum.isEmpty()) {
            isValid = false;
            Toasty.info(getApplicationContext(), "Select a forum to continue", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(mBinding.txtPostContent.getText().toString().trim())) {
            mBinding.txtPostContent.setError("Required");
            isValid = false;
        } else {
            mBinding.txtPostContent.setError(null);
        }

        return isValid;
    }
}