package com.example.bookmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bookmanager.adapter.HoaDonAdapter;
import com.example.bookmanager.dao.HoaDonDao;
import com.example.bookmanager.model.HoaDon;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ListHoaDonActivity extends AppCompatActivity {
    public List<HoaDon> dsHoaDon = new ArrayList<>();
    ListView lvHoaDon;
    HoaDonAdapter adapter = null;
    HoaDonDao hoaDonDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hoa_don);
        setTitle("HOÁ ĐƠN");
        lvHoaDon = (ListView) findViewById(R.id.lvHoaDon);
        hoaDonDAO = new HoaDonDao(ListHoaDonActivity.this);
        try {
            dsHoaDon = hoaDonDAO.getAllHoaDon();
        } catch (Exception e) {
            Log.d("Error: ", e.toString());
        }
        adapter = new HoaDonAdapter(this, dsHoaDon);
        lvHoaDon.setAdapter(adapter);
        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                HoaDon hoaDon = (HoaDon) parent.getItemAtPosition(position);
                Intent intent = new Intent(ListHoaDonActivity.this, ListHoaDonChiTietActivity.class);

                Bundle b = new Bundle();
                b.putString("MAHOADON", hoaDon.getMaHoaDon());
                intent.putExtras( b);
                startActivity(intent);
            }
        });
        // TextFilter
        lvHoaDon.setTextFilterEnabled(true);
        EditText edSeach = (EditText) findViewById(R.id.edSearch);
        edSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
                if (count < before) {
                    adapter.resetData();
                }
                adapter.getFilter().filter(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dsHoaDon.clear();
        try {
            dsHoaDon = hoaDonDAO.getAllHoaDon();
            adapter.changeDataset(hoaDonDAO.getAllHoaDon());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hoadon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(ListHoaDonActivity.this,HoaDonActivity.class);
                startActivity(intent);
                return(true);
            case R.id.exit:
                Intent intent1 = new Intent(ListHoaDonActivity.this, MainActivity.class);
                startActivity(intent1);
                return (true);
        }
        return super.onOptionsItemSelected(item);
    }
}