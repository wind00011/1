package com.example.myapplication;
//定義應用程式的主套件名稱。

import android.Manifest;//匯入定位權限相關的常數
import android.content.pm.PackageManager;//匯入檢查權限的類別
import androidx.core.app.ActivityCompat;//匯入權限請求工具
import androidx.core.content.ContextCompat;//匯入檢查權限工具
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
//匯入Android基本功能所需的類別，如Bundle（用於保存狀態）、View和Menu。

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
//匯入Google Material Design的Snackbar（提示訊息）和NavigationView（側邊導航）相關類別。

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
//匯入Android Jetpack導航和UI工具（如導航控制器、應用欄配置、抽屜布局等）。

import com.example.myapplication.databinding.ActivityMainBinding;
//匯入自動生成的`ActivityMainBinding`類別，用於綁定XML佈局檔案。

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    //定義AppBarConfiguration，用於管理應用欄的行為與導航結構。
    private ActivityMainBinding binding;
    //定義binding變數，用於綁定活動的視圖元件。
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;//定義定位權限請求碼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在創建活動時呼叫父類別的onCreate方法。

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //使用ViewBinding來加載對應的佈局檔案。
        setContentView(binding.getRoot());
        //設定當前活動的內容視圖為綁定的根視圖。

        setSupportActionBar(binding.appBarMain.toolbar);
        //設定自定義的工具欄作為支援操作欄。
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //設置浮動按鈕(FAB)的點擊事件。
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //顯示Snackbar訊息，提供即時反饋。
                        .setAction("Action", null)
                        //為Snackbar添加按鈕（目前為空操作）。
                        .setAnchorView(R.id.fab).show();
                        //將Snackbar錨定到FAB（浮動按鈕）。
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        //獲取綁定的抽屜佈局。
        NavigationView navigationView = binding.navView;
        //獲取綁定的導航視圖。
        //設置應用欄配置，將導航項目設置為頂層目標。
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_moitor, R.id.nav_comment, R.id.nav_setting)
                //定義頂層導航目標的ID。
                .setOpenableLayout(drawer)
                //綁定抽屜佈局。
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //獲取導航控制器，管理Fragment的導航。
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //設置操作欄與導航控制器的同步。
        NavigationUI.setupWithNavController(navigationView, navController);
        //設置導航視圖與導航控制器的同步。
        checkLocationPermission();//檢查和請求定位權限
    }

    /**
     * 檢查是否已取得 ACCESS_FINE_LOCATION 權限，如果未取得則請求權限。
     */
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION // 欲檢查的權限
        ) != PackageManager.PERMISSION_GRANTED) {
            // 如果尚未取得權限
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, // 欲請求的權限清單
                    LOCATION_PERMISSION_REQUEST_CODE // 權限請求碼
            );
        } else {
            // 已經取得權限，可以執行定位功能的相關邏輯
            startLocationFunctionality();
        }
    }

    /**
     * 當權限請求完成後的回調處理。
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // 如果是定位權限請求
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 如果使用者授予了權限
                startLocationFunctionality();
            } else {
                // 權限被拒絕時，顯示訊息或採取其他處理
                Snackbar.make(binding.getRoot(), "Location permission is required for this feature", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 啟動定位功能的相關邏輯。
     */
    private void startLocationFunctionality() {
        // 在此處添加與定位功能相關的邏輯，例如初始化定位服務或請求位置更新
        Snackbar.make(binding.getRoot(), "Location permission granted! Starting location functionality.", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //建立選單，將選單項目添加到操作欄（如果存在）。
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //膨脹選單資源檔（`res/menu/main.xml`）並加載到Menu中。
        return true;
        //返回true表示選單已成功加載。
    }

    @Override
    public boolean onSupportNavigateUp() {
        //處理應用欄的向上導航行為。
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //獲取導航控制器。
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                //使用NavigationUI來實現向上導航功能。
                || super.onSupportNavigateUp();
                //如果導航控制器無法處理，則調用父類的處理邏輯。
    }
}