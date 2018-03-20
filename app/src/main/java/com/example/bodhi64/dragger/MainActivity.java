package com.example.bodhi64.dragger;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageView background, dragging;
    private ViewGroup rootLayout;
    private int xDelta;
    private int yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout= (ViewGroup)findViewById(R.id.root_layout);
        background=(ImageView)findViewById(R.id.img_background);
        dragging=(ImageView)findViewById(R.id.img_drag);

        RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(150,150);
        dragging.setLayoutParams(layoutParams);
        dragging.setOnTouchListener(new ChoiceTouchListener());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBackgroundImage();
            }
        });
    }

    void selectBackgroundImage(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.img_background);

            Uri selectedImageUri = data.getData();
            String s = getRealPathFromURI(selectedImageUri);

            Picasso.get()
                    .load(new File(s)) 
                    .config(Bitmap.Config.RGB_565)
                    .fit().centerCrop()
                    .into(imageView);

            Intent i = new Intent(this, SelectImageActivity.class);
            startActivityForResult(i, 2);

        }

        if (requestCode ==2) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                if(result.equals("thug")){
                    dragging.setImageResource(R.drawable.thug);
                }
                else if(result.equals("ears")){
                    dragging.setImageResource(R.drawable.ears);

                }
                else if(result.equals("cap")){
                    dragging.setImageResource(R.drawable.cap);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private class ChoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            final int X=(int)motionEvent.getRawX();
            final int Y=(int)motionEvent.getRawY();
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams IParams=(RelativeLayout.LayoutParams)view.getLayoutParams();
                    xDelta=X-IParams.leftMargin;
                    yDelta=X-IParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;

                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = X - xDelta;
                    layoutParams.topMargin = Y - yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }



}
