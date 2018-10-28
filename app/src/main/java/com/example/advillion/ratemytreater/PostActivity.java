package com.example.advillion.ratemytreater;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class PostActivity extends LoginActivity {

    public ImageView newPostImage;
    private EditText newPostDesc;
    private Button newPostBtn;
    public Uri postImageUri = null;
    private String desc;
    private ProgressBar newPostProgress;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private String currentId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentId = username;


        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent postintent = new Intent(PostActivity.this, HouseActivity.class);
                startActivity(postintent);

            }
        });

        newPostImage = findViewById(R.id.imageView);
        newPostDesc = findViewById(R.id.editText);
        newPostBtn = findViewById(R.id.postbutton);
        newPostProgress = findViewById(R.id.progressBar);
        newPostImage.bringToFront();

        newPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {

                        Toast.makeText(PostActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(PostActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {

                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(PostActivity.this);
                    }

                }else{

                    Toast.makeText(PostActivity.this, "There is something wrong with this app", Toast.LENGTH_LONG).show();
                }
            }

        });

        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                desc = newPostDesc.getText().toString();

                if(!TextUtils.isEmpty(desc) && postImageUri != null){

                    newPostProgress.setVisibility(View.VISIBLE);
                    final String random = FieldValue.serverTimestamp().toString();
                    final StorageReference filePath = storageReference.child("images").child(random + ".jpg");
                    filePath.putFile(postImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {




                            String downloadUri = filePath.getDownloadUrl().toString();

                            Map<String, Object> postMap = new HashMap<>();
                            postMap.put("imageUrl", downloadUri);
                            postMap.put("Rating", desc);
                            postMap.put("user", currentId);
                            postMap.put("time", FieldValue.serverTimestamp());

                            //Toast.makeText(PostActivity.this, "Post will never been added", Toast.LENGTH_LONG).show();
                            firebaseFirestore.collection(Adapter.address).document(random).set(postMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(PostActivity.this, "Post has been added", Toast.LENGTH_LONG).show();
                                    Intent blogIntent = new Intent (PostActivity.this, HouseActivity.class);
                                    startActivity(blogIntent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(PostActivity.this, "Post will never been added", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    });
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                postImageUri = result.getUri();
                Log.w("myApp", String.valueOf(postImageUri));

                newPostImage.setImageURI(postImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();


            }
        }
    }



}
