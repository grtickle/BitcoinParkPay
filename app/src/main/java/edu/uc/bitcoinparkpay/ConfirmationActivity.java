package edu.uc.bitcoinparkpay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by Greg on 8/1/2015.
 */
public class ConfirmationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
    }

    public void GoToNotificationOnClicked(View v){
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    public void ReturnToHomeOnClicked(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
