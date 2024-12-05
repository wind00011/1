package com.example.myapplication.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class UserActivity extends AppCompatActivity {

    private EditText editTextNickname;
    private ImageView imageViewProfile;
    private Button buttonChangeAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // 初始化視圖元件
        editTextNickname = findViewById(R.id.editTextNickname);
        imageViewProfile = findViewById(R.id.imageViewProfile);
        buttonChangeAvatar = findViewById(R.id.button_change_avatar);

        // 取得傳遞過來的使用者名稱
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if (username != null) {
            // 顯示用戶名稱
            editTextNickname.setText(username);
        }

        // 顯示頭貼
        imageViewProfile.setImageResource(R.drawable.ic_default_avatar);

        // 更換頭貼按鈕點擊事件
        buttonChangeAvatar.setOnClickListener(view -> {
            // 這裡您可以開啟圖片選擇器或相機
            // 例如開啟相機或相簿來選擇新頭貼
            // 目前只是簡單顯示一個提示
            imageViewProfile.setImageResource(R.drawable.ic_default_avatar); // 假設您選擇了一個新頭貼
        });
    }
}
