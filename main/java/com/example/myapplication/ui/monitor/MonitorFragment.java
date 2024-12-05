package com.example.myapplication.ui.monitor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class MonitorFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 為 Fragment 設定對應的佈局
        View root = inflater.inflate(R.layout.nav_monitor, container, false);

        // 取得按鈕的引用
        Button button = root.findViewById(R.id.button_open_web);

        // 為按鈕設定點擊事件，打開指定網頁
        button.setOnClickListener(v -> {
            // 指定目標網址
            String url = "https://tw.live/";
            // 使用 Intent 打開瀏覽器
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        return root;
    }
}
