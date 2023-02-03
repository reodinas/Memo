package com.reodinas2.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.reodinas2.memo.data.DatabaseHandler;
import com.reodinas2.memo.model.Memo;

public class AddActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString().trim();
                String content = editContent.getText().toString().trim();

                // 제목과 내용이 있는지 확인
                if (title.isEmpty()){
                    Toast.makeText(AddActivity.this, "제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (content.isEmpty()){
                    Toast.makeText(AddActivity.this, "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 메모를 저장한다.
                // 묶어서 처리할 Memo 객체를 하나 만든다.
                Memo memo = new Memo(title, content);

                // 메모를 DB에 저장한다.
                DatabaseHandler db = new DatabaseHandler(AddActivity.this);
                db.addMemo(memo);

                // 유저한테 잘 저장되었다고, 알려준다.
                Toast.makeText(AddActivity.this, "잘 저장되었습니다", Toast.LENGTH_SHORT).show();

                // 다 완료되면 메모 생성 화면은 필요가 없다.
                // 따라서 이  액티비티는 종료한다.
                finish();
            }
        });
    }
}