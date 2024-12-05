package com.example.myapplication.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class HomeFragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100; // 權限請求碼
    private FusedLocationProviderClient fusedLocationProviderClient; // 用於獲取定位的 API 客戶端
    private TextView textHome; // 用來顯示定位結果的 TextView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 膨脹對應的佈局
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // 獲取 TextView 元件
        textHome = root.findViewById(R.id.text_home);

        // 初始化 FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        // 檢查和請求定位權限
        checkLocationPermission();

        return root;
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            // 權限未授予，請求權限
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        } else {
            // 權限已授予，啟動定位功能
            fetchLocation();
        }
    }

    private void fetchLocation() {
        // 檢查是否具備權限（在執行階段安全檢查）
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return; // 如果無權限，直接返回
        }

        // 獲取最後一次已知位置
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // 顯示定位結果
                            String result = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();
                            textHome.setText(result); // 更新 TextView
                        } else {
                            textHome.setText("Unable to fetch location. Try again.");
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 如果權限被授予
                fetchLocation();
            } else {
                // 如果權限被拒絕
                textHome.setText("Location permission denied.");
            }
        }
    }
}
