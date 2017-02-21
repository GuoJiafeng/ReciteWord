package com.gjf.reciteword;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by BlackBeard丶 on 2016/11/24.
 */
public class SearchActivity extends AppCompatActivity {
    EditText eden;
    EditText edcn;
    Button   bnen;
    Button   bncn;
    TextView textserch;
   List word;
  ListView listview;
    String personid;
    String chinese;
    String english;
    EditText en ;
    EditText cn ;
    String upenglish;
    String upchinese;

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        eden = (EditText)findViewById(R.id.seen);
        edcn = (EditText)findViewById(R.id.secn);
        bnen = (Button)findViewById(R.id.sen);
        bncn = (Button)findViewById(R.id.scn);
        final Gson gson = new Gson();

        textserch = (TextView)findViewById(R.id.textsearch);


        bnen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // listview.removeAllViews();

               searchen();
                eden.setText("");



            }
        });

        bncn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //  listview.removeAllViews();

                searchcn();
                edcn.setText("");


            }
        });

    }


 public  void   searchen(){
     List <Word> words = (List) DataSupport.where("english like ?",eden.getText().toString()).find(Word.class);
     List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
     for (Word word:words){
         HashMap<String, Object> item = new HashMap<String, Object>();
         item.put("id", word.getId());
         item.put("english", word.getEnglish());
         item.put("chinese", word.getChinese());
         item.put("publishdate", word.getPublishdate());
         data.add(item);
     }

     SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item,
             new String[]{"id", "english", "chinese","publishdate"}, new int[]{R.id.name, R.id.phone, R.id.amount,R.id.date});
     //实现列表的显示
     listview = (ListView)findViewById(R.id.list);
     listview.setAdapter(adapter);
     listview.setOnItemClickListener(new ItemClick());
 }




    public  void   searchcn(){
        List <Word> words = (List) DataSupport.where("chinese like ?",edcn.getText().toString()).find(Word.class);
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        for (Word word:words){
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("id", word.getId());
            item.put("english", word.getEnglish());
            item.put("chinese", word.getChinese());
            item.put("publishdate", word.getPublishdate());
            data.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item,
                new String[]{"id", "english", "chinese","publishdate"}, new int[]{R.id.name, R.id.phone, R.id.amount,R.id.date});
        //实现列表的显示
        listview = (ListView)findViewById(R.id.list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new ItemClick());
    }

    private  final class ItemClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            ListView listView = (ListView)parent;
            HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
            personid = data.get("id").toString();
            english = data.get("english").toString();
            chinese = data .get("chinese").toString();
            //Toast.makeText(getApplicationContext(), personid, 1).show();
            delete();


        }
    }

    private void delete(){
        new AlertDialog.Builder(SearchActivity.this).setMessage("选项")
                .setPositiveButton("编辑", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        LayoutInflater inflater = getLayoutInflater();
                        final View layout = inflater.inflate(R.layout.update, (ViewGroup) findViewById(R.id.uplayout));
                        en = (EditText)layout.findViewById(R.id.upen);
                        cn = (EditText)layout.findViewById(R.id.upcn);
                        cn.setText(chinese);
                        en.setText(english);

                        new AlertDialog.Builder(SearchActivity.this).setMessage("请输入修改内容:").setView(layout).setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //  Toast.makeText(SearchAllActivity.this,  en.getText().toString(),Toast.LENGTH_LONG).show();


                                upenglish = en.getText().toString();
                                upchinese = cn.getText().toString();
                                ContentValues valuesen = new ContentValues();
                                valuesen.put("english",upenglish );
                                DataSupport.update(Word.class, valuesen, Long.parseLong(personid));

                                ContentValues valuescn = new ContentValues();
                                valuescn.put("chinese",upchinese );
                                DataSupport.update(Word.class, valuescn, Long.parseLong(personid));
                                refresh();

                            }
                        }).setNegativeButton("取消",null).show();
                    }
                }).setNegativeButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new AlertDialog.Builder(SearchActivity.this).setMessage("真的要删除吗？").setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.delete(Word.class, Long.parseLong(personid));
                        refresh();
                    }
                }).setPositiveButton("否",null).show();
            }
        }).show();
    }
    private void refresh(){
        finish();
        Intent intent = new Intent(SearchActivity.this,SearchActivity.class);
        startActivity(intent);
    }


}
