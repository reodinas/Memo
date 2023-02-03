package com.reodinas2.memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.reodinas2.memo.adapter.MemoAdapter;
import com.reodinas2.memo.data.DatabaseHandler;
import com.reodinas2.memo.model.Memo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editSearch;
    ImageView imgSearch;
    ImageView imgDelete;
    Button btnAdd;

    RecyclerView recyclerView;
    MemoAdapter adapter;
    ArrayList<Memo> memoList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch = findViewById(R.id.editSearch);
        imgSearch = findViewById(R.id.imgSearch);
        imgDelete = findViewById(R.id.imgDelete);
        btnAdd = findViewById(R.id.btnAdd);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 메모 생성 액티비티 실행
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });

        // 버튼을 눌러서 검색
//        imgSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String keyword = editSearch.getText().toString().trim();
//
//                if (keyword.isEmpty()){
//                    editSearch.setText("");
//                    return;
//                }
//                // DB에서 검색한다 => 메모 리스트를 가져온다.
//                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
//                ArrayList<Memo> searchList = db.searchMemo(keyword);
//
//
//                adapter = new MemoAdapter(MainActivity.this, searchList);
//                recyclerView.setAdapter(adapter);
//            }
//        });

        // 실시간 검색
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 1. 유저가 입력한 키워드 뽑는다
                String keyword = editSearch.getText().toString().trim();


                // 두글자 이상 입력 했을 때만 검색이 되도록 한다.
                if (keyword.length() < 2 && keyword.length() > 0){
                    return;
                }

                // 2. DB에서 검색한다 => 메모 리스트를 가져온다.
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                ArrayList<Memo> searchList = db.searchMemo(keyword);

                // 3. 화면에 보여준다.
                adapter = new MemoAdapter(MainActivity.this, searchList);
                recyclerView.setAdapter(adapter);
            }
        });


        // 검색어 삭제
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSearch.setText("");

                adapter = new MemoAdapter(MainActivity.this, memoList);
                recyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 앱 실행시 저장된 데이터를 화면에 보여준다.
        // DB에 저장된 데이터를 가져오자.
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        memoList = db.getAllMemo();

        adapter = new MemoAdapter(MainActivity.this, memoList);
        recyclerView.setAdapter(adapter);

    }
}