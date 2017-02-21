package com.gjf.reciteword;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.tablemanager.Connector;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText edtexten;
    EditText edtextcn;
    Button bnenter;
    Button   bncnsel;
    Button   bnserch;
    String cn;
    String en;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SQLiteDatabase db = Connector.getDatabase();
        edtexten = (EditText)findViewById(R.id.edtexten);
        edtextcn = (EditText)findViewById(R.id.edtextcn);
        bnenter  = (Button)findViewById(R.id.bnenter);
        bncnsel   = (Button)findViewById(R.id.bncancel);

        en = edtexten.getText().toString();
        cn = edtextcn.getText().toString();
        final Word word = new Word();
        bnenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtexten.getText().toString().equals("")&&!edtextcn.getText().toString().equals("")) {
                    word.setEnglish(edtexten.getText().toString());
                    word.setChinese(edtextcn.getText().toString());
                    word.setPublishdate(new Date());
                    word.save();

                    if (word.save()) {
                        Toast.makeText(getApplicationContext(), "存储成功", Toast.LENGTH_SHORT).show();
                    } else {   Toast.makeText(getApplicationContext(), "存储失败", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"你倒是输入内容啊！",Toast.LENGTH_LONG).show();
                }


            }
        });
        bncnsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtexten.setText("");
                edtextcn.setText("");
                refresh();
            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            Intent intent = new Intent(MainActivity.this,SearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
         Intent intent = new Intent(MainActivity.this,SearchAllActivity.class);
            startActivity(intent);

        }  else if (id == R.id.nav_share) {
              Intent intent  = new Intent(MainActivity.this,LoginActivity.class);
             startActivity(intent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void refresh(){
        finish();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
