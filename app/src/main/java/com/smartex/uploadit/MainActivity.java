package com.smartex.uploadit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    public static final int IMAGE_REQ = 1;

    private Button bChoose, bUpload;
    private ImageView imageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        bChoose = (Button) findViewById(R.id.bChoose);
        imageView = (ImageView) findViewById(R.id.ivSample);

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: item selected " + v.getId());
        switch (v.getId()) {
            case R.id.bChoose:
                Intent intent = new Intent();
                intent.setType("image/*").setAction(Intent.ACTION_GET_CONTENT); //I want all type of Image
                startActivityForResult(Intent.createChooser(intent, "select pic"), IMAGE_REQ);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQ && resultCode != RESULT_OK && data != null && data.getData() != null) {
            imageUri = (Uri) data.getData();
//            Picasso.load(imageUri).into(imageView);
            imageView.setImageURI(imageUri);
        }

    }
}
