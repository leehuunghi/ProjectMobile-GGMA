package com.example.admin.foodn_test;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

public class UpdateStore extends AppCompatActivity {

    EditText txtTenCuaHangUpdate, txtDiaChiCuaHangUpdate, txtSDTCuaHangUpdate, txtMoCuaUpdate, txtDongCuaUpdate;
    Button btnUpdateStore;
    ImageView imgImageStoreUpdate;
    ImageButton btnEditStoreUpdate;

    private static int LOAD_IMAGE_RESULTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_store);

        addControls();
        addEvents();

        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Chỉnh sửa quán");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEvents() {

//        imgLocationCuaHangUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intMap=new Intent(UpdateStore.this,HomeActivity.class);
//                startActivity(intMap);
//            }
//        });

        btnEditStoreUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the Intent for Image Gallery.
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // i.setType("image/*");
                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
                startActivityForResult(i, LOAD_IMAGE_RESULTS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent intent) {
        super.onActivityResult(requestCode, resultcode, intent);
        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultcode == RESULT_OK && intent != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = intent.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Now we need to set the GUI ImageView data with data read from the picked file.
            imgImageStoreUpdate.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

    private void addControls() {
        txtTenCuaHangUpdate = findViewById(R.id.txtTenCuaHangUpdate);
        txtDiaChiCuaHangUpdate=findViewById(R.id.txtDiaChiCuaHangUpdate);
        txtSDTCuaHangUpdate=findViewById(R.id.txtSDTCuaHangUpdate);
        txtMoCuaUpdate=findViewById(R.id.txtMoCuaUpdate);
        txtDongCuaUpdate=findViewById(R.id.txtDongCuaUpdate);
        btnUpdateStore=findViewById(R.id.btnUpdateStore);
        imgImageStoreUpdate=findViewById(R.id.imgImageStoreUpdate);
        btnEditStoreUpdate=findViewById(R.id.btnEditStoreUpdate);
    }
}
