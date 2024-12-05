package com.example.myapplication.ui.gallery;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1; // 定義圖片選擇的請求代碼
    private FragmentGalleryBinding binding;
    private Uri selectedImageUri; // 用於存儲選擇的圖片 URI

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText editTextUsername = root.findViewById(R.id.editTextUsername);
        final EditText editTextPassword = root.findViewById(R.id.editTextPassword);
        Button buttonLogin = root.findViewById(R.id.button_login);
        Button buttonRegister = root.findViewById(R.id.button_register);
        TextView textTitle = root.findViewById(R.id.text_title); // 登入註冊標題

        // 頭貼和暱稱相關視圖
        final ImageView imageViewProfile = root.findViewById(R.id.imageViewProfile);
        final EditText editTextNickname = root.findViewById(R.id.editTextNickname);
        final Button buttonChangeAvatar = root.findViewById(R.id.button_change_avatar);

        // 初始隱藏頭貼和暱稱欄位
        imageViewProfile.setVisibility(View.GONE);
        editTextNickname.setVisibility(View.GONE);
        buttonChangeAvatar.setVisibility(View.GONE);

        // 登入按鈕點擊事件
        buttonLogin.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // 基本驗證：檢查使用者名稱與密碼是否為空
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getActivity(), "請輸入使用者名稱與密碼", Toast.LENGTH_SHORT).show();
            } else {
                // 顯示用戶頭貼、暱稱欄位與更換頭貼按鈕
                imageViewProfile.setVisibility(View.VISIBLE);
                editTextNickname.setVisibility(View.VISIBLE);
                buttonChangeAvatar.setVisibility(View.VISIBLE);

                // 顯示頭貼（這裡使用默認頭貼，您可以根據需要更換）
                imageViewProfile.setImageResource(R.drawable.ic_default_avatar);  // 預設頭貼

                // 顯示已輸入的使用者名稱，並將其作為暱稱
                editTextNickname.setText(username);

                // 隱藏登入註冊相關的視圖
                editTextUsername.setVisibility(View.GONE);
                editTextPassword.setVisibility(View.GONE);
                buttonLogin.setVisibility(View.GONE);
                buttonRegister.setVisibility(View.GONE);
                textTitle.setVisibility(View.GONE);
            }
        });

        // 註冊按鈕點擊事件
        buttonRegister.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // 基本驗證：檢查使用者名稱與密碼是否為空
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getActivity(), "請輸入使用者名稱與密碼", Toast.LENGTH_SHORT).show();
            } else {
                // 顯示註冊成功的提示
                Toast.makeText(getActivity(), "註冊成功！", Toast.LENGTH_SHORT).show();
            }
        });

        // 更換頭貼按鈕點擊事件
        buttonChangeAvatar.setOnClickListener(view -> {
            // 啟動圖片選擇器，讓用戶選擇圖片
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*"); // 限制為選擇圖片
            startActivityForResult(intent, PICK_IMAGE_REQUEST); // 開始選擇圖片
        });

        return root;
    }

    // 當圖片選擇結果回傳時，處理圖片
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData(); // 獲取圖片 URI
            ImageView imageViewProfile = binding.imageViewProfile; // 獲取頭貼 ImageView
            imageViewProfile.setImageURI(selectedImageUri); // 設置圖片為選擇的圖片
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
