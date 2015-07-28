package edu.uc.bitcoinparkpay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Luai on 6/4/2015.
 */
public class CameraActivity extends MainActivity {
    public static final int CAMERA_REQUEST = 10;
    private ImageView imgPhoto;
/**
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //associate the layout with this activity.
        //Cannot resolve "activity_camera"
        setContentView(R.layout.activity_camera);

        //access image
        //Cannot resolve "imgPhoto"
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
    }
**/
    //This method is called when the "Capture" button is clicked
    public void btnCaptureClicked(View v) {
        Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        super.startActivityForResult(CameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                //We are hitting camera
                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                //image from camera taken
                imgPhoto.setImageBitmap(cameraImage);
            }
        }

    }

}
