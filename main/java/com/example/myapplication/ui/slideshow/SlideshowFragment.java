package com.example.myapplication.ui.slideshow;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class SlideshowFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;

    private ImageView imageView;
    private TextView textViewLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private Location lastKnownLocation;
    private Bitmap capturedImage;  // 用於儲存拍攝的圖片

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        // 初始化元件
        imageView = root.findViewById(R.id.imageView);
        textViewLocation = root.findViewById(R.id.textView_location);
        Button buttonTakePhoto = root.findViewById(R.id.button_take_photo);

        // 初始化 FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        // 檢查並請求相機和位置權限
        checkPermissions();

        // 拍照按鈕點擊事件
        buttonTakePhoto.setOnClickListener(v -> openCamera());

        return root;
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "相機權限已授予", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "未授予相機權限", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "位置權限已授予", Toast.LENGTH_SHORT).show();
                fetchLocation();  // 如果授予了位置權限，立刻獲取位置
            } else {
                Toast.makeText(getActivity(), "未授予位置權限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                // 從拍照 Intent 獲取圖片的 Bitmap
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                capturedImage = bitmap; // 儲存圖片
                imageView.setImageBitmap(capturedImage); // 顯示圖片

                // 獲取位置資訊
                fetchLocation();
            }
        }
    }

    private void fetchLocation() {
        // 檢查是否有位置權限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // 創建一個 LocationRequest 來設置位置更新頻率
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(10000); // 每10秒更新一次位置
            locationRequest.setFastestInterval(5000); // 最快5秒更新一次
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            // 創建 LocationCallback 來接收位置信息
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(com.google.android.gms.location.LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    if (locationResult != null && locationResult.getLocations() != null) {
                        for (Location location : locationResult.getLocations()) {
                            lastKnownLocation = location;
                            String locationText = "緯度: " + location.getLatitude() + ", 經度: " + location.getLongitude();
                            textViewLocation.setText("位置資訊：" + locationText);
                        }
                    }
                }
            };

            // 請求位置更新
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            textViewLocation.setText("無權限獲取位置資訊");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // 停止位置更新以節省電池
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }
}
