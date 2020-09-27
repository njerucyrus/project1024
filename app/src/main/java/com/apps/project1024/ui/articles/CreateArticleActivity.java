package com.apps.project1024.ui.articles;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.apps.project1024.R;
import com.apps.project1024.databinding.ActivityCreateArticleBinding;
import com.apps.project1024.models.Article;
import com.apps.project1024.ui.home.HomeActivity;
import com.apps.project1024.viewmodels.ArticlesViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.M;
import static com.apps.project1024.utils.Utils.displayErrorMessage;
import static com.apps.project1024.utils.Utils.displaySuccessMessage;

public class CreateArticleActivity extends AppCompatActivity {

    private ActivityCreateArticleBinding mBinding;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int OPEN_IMAGE = 200;
    private String[] storagePermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Uri selectedPhotoUri = null;
    private ArticlesViewModel mViewModel;
    private String selectedCategory;
    private ProgressDialog mProgress;

    @RequiresApi(api = M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityCreateArticleBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mProgress = new ProgressDialog(this);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("New Article");
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mViewModel = new ViewModelProvider(this).get(ArticlesViewModel.class);

        mBinding.btnUploadImage.setOnClickListener(view -> {
            if (areStoragePermissionsGranted()) {
                openImageChooser();
            } else {
                requestStoragePermissions();
            }
        });

        mBinding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    selectedCategory = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                    selectedCategory = "";
            }
        });

        mViewModel.getIsLoading().observe(this, isLoading->{
            if (isLoading) {
                showProgress("Submitting please wait...");
            }
            else {
                hideProgress();
            }
        });

        mViewModel.getSuccessMsg().observe(this, s->{
            if (!s.isEmpty()) {
                displaySuccessMessage(getApplicationContext(), s);
                mViewModel.resetValues();
                startActivity(new Intent(CreateArticleActivity.this, HomeActivity.class));
                finish();
            }
        });

        mViewModel.getErrorMsg().observe(this, s->{
            if (!s.isEmpty()) {
                displayErrorMessage(getApplicationContext(), s);
            }
        });

    }


    private void showProgress(String message){
        mProgress.setMessage(message);
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();
    }

    private void hideProgress() {
        if (mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();


        if (id == R.id.action_create) {
            if (validateInputs()) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Article article = new Article(
                        "", mBinding.txtTitle.getText().toString().trim(),
                        mBinding.txtBody.getText().toString().trim(),
                        selectedCategory,
                        "",
                        mBinding.txtVideoUrl.getText().toString().trim(),
                        mBinding.txtAuthor.getText().toString().trim(),
                        true,
                        df.format(new Date())
                );
                mViewModel.createArticle(article, selectedPhotoUri);
            }
        }

        return super.onOptionsItemSelected(item);
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
            Glide.with(CreateArticleActivity.this)
                    .load(uri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mBinding.btnUploadImage);

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

    private boolean validateInputs() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mBinding.txtTitle.getText().toString().trim())) {
            isValid = false;
            mBinding.txtTitle.setError("Required");
        } else {
            mBinding.txtTitle.setError(null);

        }

        if (TextUtils.isEmpty(mBinding.txtBody.getText().toString().trim())) {
            isValid = false;
            mBinding.txtBody.setError("Required");
        } else {
            mBinding.txtBody.setError(null);

        }



        if (TextUtils.isEmpty(mBinding.txtAuthor.getText().toString().trim())) {
            isValid = false;
            mBinding.txtAuthor.setError("Required");
        } else {
            mBinding.txtAuthor.setError(null);

        }

        if (TextUtils.isEmpty(selectedCategory)) {
            isValid = false;
            displayErrorMessage(getApplicationContext(), "Select category.");
        }

        return isValid;
    }

}