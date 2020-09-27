package com.apps.project1024.repositories;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apps.project1024.models.ForumPost;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
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

public class ForumPostRepository {
    private static final String TAG = "ForumPostRepository";
    private static ForumPostRepository INSTANCE;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<String> successMsg = new MutableLiveData<>();
    private MutableLiveData<String> errorMsg = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private Application application;
    private MutableLiveData<FirebaseUser> mCurrentUser = new MutableLiveData<>();

    public static ForumPostRepository getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new ForumPostRepository();
            INSTANCE.application = application;
        }
        return INSTANCE;
    }

    public void createForumPost(ForumPost post, Uri mediaUri) {
        isLoading.postValue(true);
        successMsg.postValue("");
        errorMsg.postValue("");


        String docKey = db.collection("forum_posts").document().getId();
        post.setDocKey(docKey);
        final DocumentReference docRef = db.collection("forum_posts").document(docKey);

        if (mediaUri != null) {

            Bitmap bitmapImage = getBitmapFromUri(application.getApplicationContext(), mediaUri);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            assert bitmapImage != null;
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            final byte[] thumbnailBytes = baos.toByteArray();
            String fileName = String.format("%s_%s", post.getDocKey(), getFileNameFromUri(application.getApplicationContext(), mediaUri));

            UploadTask uploadTask = mStorageRef.child("forum_posts")
                    .child(fileName)
                    .putBytes(thumbnailBytes);

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("forum_posts/" + fileName);
                mStorageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadUrl = uri.toString();
                    post.setPhotoUrl(downloadUrl);
                    docRef.set(post)
                            .addOnSuccessListener(aVoid -> {
                                isLoading.postValue(false);
                                successMsg.postValue("Post Created successfully");
                            }).addOnFailureListener(e -> {
                        isLoading.postValue(false);
                        errorMsg.postValue("Error occurred while creating this post. please try again later.");
                        Log.e(TAG, "createForumPost: ", e);
                    });
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "createForumPost: ", e);
                    isLoading.postValue(false);
                    errorMsg.postValue("Unable to create download link for the uploaded  photo");
                });
            }).addOnFailureListener(e -> {
                isLoading.postValue(false);
                errorMsg.postValue("Error occurred while uploading your post photo.");
            });
        } else {
            docRef.set(post).addOnSuccessListener(aVoid -> {
                isLoading.postValue(false);

                successMsg.postValue("Post created successfully.");
            }).addOnFailureListener(e -> {
                isLoading.postValue(false);
                Log.e(TAG, "createForumPost: ", e);
                errorMsg.postValue("Error occurred while creating your post.Try again later");
            });

        }


    }


    public MutableLiveData<List<ForumPost>> getAllPost() {
        MutableLiveData<List<ForumPost>> posts = new MutableLiveData<>();
        List<ForumPost> postList = new ArrayList<>();
        db.collection("forum_posts").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    ForumPost post = snapshot.toObject(ForumPost.class);
                    if (post != null) {
                        postList.add(post);
                    }
                }
                isLoading.postValue(false);
                posts.postValue(postList);
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "onFailure: ", e);
            isLoading.postValue(false);
            errorMsg.postValue("Error occurred while fetching posts");
        });
        return posts;
    }


    public MutableLiveData<List<ForumPost>> getGrowthPosts() {
        MutableLiveData<List<ForumPost>> posts = new MutableLiveData<>();
        List<ForumPost> postList = new ArrayList<>();
        db.collection("forum_posts").whereEqualTo("forum", "growth").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    ForumPost post = snapshot.toObject(ForumPost.class);
                    if (post != null) {
                        postList.add(post);
                    }
                }
                isLoading.postValue(false);
                posts.postValue(postList);
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "onFailure: ", e);
            isLoading.postValue(false);
            errorMsg.postValue("Error occurred while fetching posts");
        });
        return posts;
    }

    public MutableLiveData<List<ForumPost>> getElevatePosts() {
        MutableLiveData<List<ForumPost>> posts = new MutableLiveData<>();
        List<ForumPost> postList = new ArrayList<>();
        db.collection("forum_posts").whereEqualTo("forum", "elevate").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    ForumPost post = snapshot.toObject(ForumPost.class);
                    if (post != null) {
                        postList.add(post);
                    }
                }
                isLoading.postValue(false);
                posts.postValue(postList);
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "onFailure: ", e);
            isLoading.postValue(false);
            errorMsg.postValue("Error occurred while fetching posts");
        });
        return posts;
    }


    public MutableLiveData<List<ForumPost>> getEmpowerPosts() {
        MutableLiveData<List<ForumPost>> posts = new MutableLiveData<>();
        List<ForumPost> postList = new ArrayList<>();
        db.collection("forum_posts").whereEqualTo("forum", "empower").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    ForumPost post = snapshot.toObject(ForumPost.class);
                    if (post != null) {
                        postList.add(post);
                    }
                }
                isLoading.postValue(false);
                posts.postValue(postList);
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "onFailure: ", e);
            isLoading.postValue(false);
            errorMsg.postValue("Error occurred while fetching posts");
        });
        return posts;
    }

    public MutableLiveData<List<ForumPost>> getKonnectPosts() {
        MutableLiveData<List<ForumPost>> posts = new MutableLiveData<>();
        List<ForumPost> postList = new ArrayList<>();
        db.collection("forum_posts").whereEqualTo("forum", "konnect").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                ForumPost post = snapshot.toObject(ForumPost.class);
                if (post != null) {
                    postList.add(post);
                }
            }
            isLoading.postValue(false);
            posts.postValue(postList);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "onFailure: ", e);
            isLoading.postValue(false);
            errorMsg.postValue("Error occurred while fetching posts");
        });
        return posts;
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


    public LiveData<FirebaseUser> getCurrentUser() {
        return mCurrentUser;
    }

    public void resetValues() {
        errorMsg.postValue("");
        successMsg.postValue("");
        isLoading.postValue(false);
    }
}
