package com.smartex.uploadit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    public static final int IMAGE_REQ = 1;

    private Button bChoose, bUpload;
    private ImageView imageView;
    private Uri imageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        bChoose.setOnClickListener(this);
        bUpload.setOnClickListener(this);
    }

    private void initViews() {
        bChoose = (Button) findViewById(R.id.bChoose);
        bUpload = (Button) findViewById(R.id.bUpload);
        imageView = (ImageView) findViewById(R.id.ivSample);
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: item selected " + v.getId());
        switch (v.getId()) {
            case R.id.bChoose:
                Intent intent = new Intent();
                intent.setType("image/*").setAction(Intent.ACTION_GET_CONTENT); //I want all type of Image
                startActivityForResult(Intent.createChooser(intent, "select pic"), IMAGE_REQ);
                Log.d(TAG, "onClick: new Act started");
                break;
            case R.id.bUpload :
                if (imageUri != null){
//                    Upload upload = new Upload(,imageUri);
                    StorageReference fileref = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
                    fileref.putFile(imageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(MainActivity.this, "Image uploaded !!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                                }
                            });

                    break;}
                else Toast.makeText(this, "First Select image", Toast.LENGTH_SHORT).show();
                break;



        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: called");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQ && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: getting image");
            imageUri = (Uri) data.getData();
//            Picasso.load(imageUri).into(imageView);
            imageView.setImageURI(imageUri);
        }

    }

    private String getFileExtension(Uri uri){ //get extensio of file
        //TODO search about it
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }
}
