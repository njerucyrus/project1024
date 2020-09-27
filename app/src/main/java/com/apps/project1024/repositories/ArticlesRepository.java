package com.apps.project1024.repositories;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apps.project1024.models.Article;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.apps.project1024.utils.Utils.getBitmapFromUri;
import static com.apps.project1024.utils.Utils.getFileNameFromUri;

public class ArticlesRepository {
    private static final String TAG = "ArticlesRepository";
    private static ArticlesRepository INSTANCE;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<String> successMsg = new MutableLiveData<>();
    private MutableLiveData<String> errorMsg = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private Application application;

    public static ArticlesRepository getInstance(Application application) {
      if (INSTANCE == null) {
          INSTANCE = new ArticlesRepository();
          INSTANCE.application= application;

      }
      return INSTANCE;
    }


    public void createArticle(Article article, Uri imageUrl) {
        successMsg.postValue("");
        errorMsg.postValue("");
        isLoading.postValue(true);
        final DocumentReference docRef = db.collection("articles").document();
        article.setDocKey(docRef.getId());
        if (imageUrl != null) {

            Bitmap bitmapImage = getBitmapFromUri(application.getApplicationContext(), imageUrl);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            assert bitmapImage != null;
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            final byte[] thumbnailBytes = baos.toByteArray();
            String fileName = String.format("%s_%s", article.getDocKey(), getFileNameFromUri(application.getApplicationContext(), imageUrl));

            UploadTask uploadTask = mStorageRef.child("articles")
                    .child(fileName)
                    .putBytes(thumbnailBytes);

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("articles/" + fileName);
                mStorageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadUrl = uri.toString();
                    article.setImageUrl(downloadUrl);
                    docRef.set(article)
                            .addOnSuccessListener(aVoid -> {
                                isLoading.postValue(false);
                                successMsg.postValue("Post Created successfully");
                            }).addOnFailureListener(e -> {
                                isLoading.postValue(false);
                        errorMsg.postValue("Error occurred while creating this post. please try again later.");
                        Log.e(TAG, "createArticle: ", e);
                    });
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "createArticle: ", e);
                    isLoading.postValue(false);
                    errorMsg.postValue("Unable to created download link for article photo");
                });
            }).addOnFailureListener(e -> {
                isLoading.postValue(false);
                errorMsg.postValue("Error occurred while uploading your profile photo.");
            });
        } else {
            docRef.set(article)
                    .addOnSuccessListener(aVoid -> {
                        isLoading.postValue(false);
                        successMsg.postValue("Post Created successfully");
                    }).addOnFailureListener(e -> {
                errorMsg.postValue("Error occurred while creating this post. please try again later.");
                Log.e(TAG, "createArticle: ", e);
            });

        }
    }

    public LiveData<List<Article>> fetchArticles() {
        successMsg.postValue("");
        errorMsg.postValue("");
        isLoading.postValue(true);

        MutableLiveData<List<Article>> articlesLiveData = new MutableLiveData<>();

        CollectionReference colRef = db.collection("articles");
        colRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Article> articles = new ArrayList<>();
            for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                Article article = snapshot.toObject(Article.class);
                if (article != null) {
                    articles.add(article);
                }

            }

            articlesLiveData.postValue(articles);
            isLoading.postValue(false);

        }).addOnFailureListener(e -> {
            isLoading.postValue(false);
            errorMsg.postValue("Error occurred while posting ");
            Log.e(TAG, "fetchArticles: ", e);
        });
        return articlesLiveData;
    }

    public MutableLiveData<String> getSuccessMsg() {
        return successMsg;
    }

    public MutableLiveData<String> getErrorMsg() {
        return errorMsg;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }




    public void resetValues() {
        errorMsg.postValue("");
        successMsg.postValue("");
        isLoading.postValue(false);

    }


}
