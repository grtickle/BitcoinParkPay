package edu.uc.bitcoinparkpay;

import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.AddressBook.MESSAGE";
    private ListView obj;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mydb = new DBHelper(MainActivity.this);
//
//        ArrayList array_list = mydb.getAllContacts();
//        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
//
//        obj = (ListView)findViewById(R.id.listView1);
//        obj.setAdapter(arrayAdapter);
//        obj.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                //TODO Auto-generated method stub
//               int id_To_Search = arg2 + 1;
//
//                Bundle dataBundle = new Bundle();
//                dataBundle.putInt("id", id_To_Search);
//
//                Intent intent = new Intent(getApplicationContext(), PACKAGE_NAME.DisplayContact.class);
//
//                intent.putExtras(dataBundle);
//                startActivity(intent);
//           }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

//        switch(item.getItemId())
//        {
//            case R.id.action_settings:Bundle dataBundle = new Bundle();
//                dataBundle.putInt("id", 0);
//
//                Intent intent = new Intent(getApplicationContext(),com.example.sairamkrishna.myapplication.DisplayContact.class);
//                intent.putExtras(dataBundle);
//
//                startActivity(intent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }

        return super.onOptionsItemSelected(item);
    }

//    public boolean onKeyDown(int keycode, KeyEvent event) {
//        if (keycode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(true);
//        }
//        return super.onKeyDown(keycode, event);
//    }
}
