package com.example.myapplication.ui.comment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText editTextComment;
    private Button buttonSend;

    private CommentAdapter commentAdapter;
    private List<Comment> commentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 載入佈局
        View root = inflater.inflate(R.layout.nav_comment, container, false);

        // 初始化 UI 元件
        recyclerView = root.findViewById(R.id.recycler_view_comments);
        editTextComment = root.findViewById(R.id.edit_text_comment);
        buttonSend = root.findViewById(R.id.button_send);

        // 初始化留言列表和適配器
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);

        // 設置 RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(commentAdapter);

        // 設置發送按鈕點擊事件
        buttonSend.setOnClickListener(v -> {
            String commentContent = editTextComment.getText().toString().trim();
            if (!TextUtils.isEmpty(commentContent)) {
                addComment(commentContent);
                editTextComment.setText(""); // 清空輸入框
            } else {
                Toast.makeText(getContext(), "請輸入留言", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    // 新增留言
    private void addComment(String content) {
        // 獲取當前時間
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date());

        // 創建新留言
        Comment comment = new Comment(content, timestamp);

        // 添加到列表並更新視圖
        commentList.add(comment);
        commentAdapter.notifyItemInserted(commentList.size() - 1);
        recyclerView.scrollToPosition(commentList.size() - 1); // 滾動到最新留言
    }
}
