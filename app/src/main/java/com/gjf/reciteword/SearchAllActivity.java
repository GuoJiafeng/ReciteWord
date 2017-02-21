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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by BlackBeard丶 on 2016/11/24.
 */
public class SearchAllActivity extends AppCompatActivity {
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
        setContentView(R.layout.serchall);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<Word> words = Word.findAll(Word.class);
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        for (Word word:words){
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("id", word.getId());
            item.put("english", word.getEnglish());
            item.put("chinese", word.getChinese());
            item.put("publishdate", word.getPublishdate());
            data.add(item);
        }

        //创建SimpleAdapter适配器将数据绑定到item显示控件上
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item,
                new String[]{"id", "english", "chinese","publishdate"}, new int[]{R.id.name, R.id.phone, R.id.amount,R.id.date });
        //实现列表的显示
      ListView listView = (ListView)findViewById(R.id.listall);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ItemClick());





    }
     //AlertDialog.Builder builder = new AlertDialog.Builder(this);
    private  final class ItemClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            ListView listView = (ListView)parent;
            HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
           personid = data.get("id").toString();
            english = data.get("english").toString();
            chinese = data .get("chinese").toString();
            delete();


        }
    }

    private void delete(){
        new AlertDialog.Builder(SearchAllActivity.this).setMessage("选项")
                .setPositiveButton("编辑", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        LayoutInflater inflater = getLayoutInflater();
                        final View layout = inflater.inflate(R.layout.update, (ViewGroup) findViewById(R.id.uplayout));
                        en = (EditText)layout.findViewById(R.id.upen);
                        cn = (EditText)layout.findViewById(R.id.upcn);
                        cn.setText(chinese);
                        en.setText(english);

                         new AlertDialog.Builder(SearchAllActivity.this).setMessage("请输入修改内容:").setView(layout).setPositiveButton("保存", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {




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
                     new AlertDialog.Builder(SearchAllActivity.this).setMessage("真的要删除吗？").setNegativeButton("是", new DialogInterface.OnClickListener() {
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
        Intent intent = new Intent(SearchAllActivity.this,SearchAllActivity.class);
        startActivity(intent);
    }
}
