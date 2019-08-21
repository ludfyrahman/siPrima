package com.android.primaitech.siprima.Follow_Up;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Config.VolleyMultipartRequest;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Pembeli.Pembeli;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Form_Follow_Up extends AppCompatActivity {
    ImageView btn_calendar;
    ProgressDialog pd;
    Calendar myCalendar;
    Button simpan;
    TextView nama_pembeli, pembahasan, kendala, tanggal_selesai;
    private LocationManager locationManager;
    private LocationListener listener;
    private static final String IMAGE_DIRECTORY = "/PrimaGroup";
    private int GALLERY = 1, CAMERA = 2;
    private ImageView gambar;
    DatePickerDialog.OnDateSetListener date;
    private Uri contentURI;
    Button prospek_rendah, prospek_sedang, prospek_tinggi;
     double longitude = 0, latitude = 0;
     int prospek = 0;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_follow_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        Intent data = getIntent();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Form_Follow_Up.this.onBackPressed();
            }
        });
        prospek_rendah = (Button)findViewById(R.id.prospek_rendah);
        prospek_sedang = (Button)findViewById(R.id.prospek_sedang);
        prospek_tinggi = (Button)findViewById(R.id.prospek_tinggi);
        prospek_rendah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prospek = 1;
                setprospek(1);
            }
        });
        prospek_sedang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prospek = 2;
                setprospek(2);
            }
        });
        prospek_tinggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prospek = 3;
                setprospek(3);
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Pesan", "\n " + location.getLongitude() + " " + location.getLatitude());
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        simpan = (Button)findViewById(R.id.simpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
        nama_pembeli = (TextView) findViewById(R.id.nama_pembeli);
        nama_pembeli.setText(data.getStringExtra("nama_pembeli"));
//        prospek = (TextView)findViewById(R.id.prospek);
        pembahasan = (TextView)findViewById(R.id.pembahasan);
        kendala = (TextView)findViewById(R.id.kendala);
        tanggal_selesai = (TextView)findViewById(R.id.tanggal_selesai);
        btn_calendar = (ImageView)findViewById(R.id.btn_calendar);
        gambar = (ImageView)findViewById(R.id.gambar);
        myCalendar = Calendar.getInstance();
        pd = new ProgressDialog(Form_Follow_Up.this);
        tanggal_selesai.setText(DateFormat.getDateTimeInstance().format(new Date()));
        Button pilih_gambar = (Button)findViewById(R.id.pilih_gambar);
        pilih_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        requestMultiplePermissions();
        configure_button();
    }
    private void setprospek(int val){
        if(val == 1){
            prospek_rendah.setBackgroundResource(R.drawable.box_shadow);
            prospek_sedang.setBackgroundResource(R.drawable.border_green);
            prospek_tinggi.setBackgroundResource(R.drawable.border_green);
        }else if(val == 2){
            prospek_sedang.setBackgroundResource(R.drawable.box_shadow);
            prospek_rendah.setBackgroundResource(R.drawable.border_green);
            prospek_tinggi.setBackgroundResource(R.drawable.border_green);
        }else if(val == 3){
            prospek_tinggi.setBackgroundResource(R.drawable.box_shadow);
            prospek_rendah.setBackgroundResource(R.drawable.border_green);
            prospek_sedang.setBackgroundResource(R.drawable.border_green);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.

                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);

    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }
    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void simpan(){
        Intent data = getIntent();
        final String kode_kunjungan_final= data.getStringExtra("kode_kunjungan");
//        final String lat = Integer.toString(latitude);
        final String kendala_text = kendala.getText().toString().trim();
        final String bahasan = pembahasan.getText().toString().trim();
        if(prospek == 0){
            Toast.makeText(this, "Prospek masih kosong", Toast.LENGTH_SHORT).show();
        }else if(bahasan.equals("")){
            Toast.makeText(this, "Pembahasan masih kosong", Toast.LENGTH_SHORT).show();
            kendala.setFocusable(true);
        }else{
            pd.show();
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(
                    Request.Method.POST,
                    ServerAccess.URL_KUNJUNGAN+"updatekunjungan",
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(new String(response.data));
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {
                                    Toast.makeText(
                                            Form_Follow_Up.this,
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    startActivity(new Intent(Form_Follow_Up.this, Pembeli.class));

                                } else {
                                    Toast.makeText(
                                            Form_Follow_Up.this,
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } catch (JSONException e) {

                                Toast.makeText(
                                        Form_Follow_Up.this,
                                        e.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            Toast.makeText(
                                    Form_Follow_Up.this,
                                    "error",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("kode", AuthData.getInstance(Form_Follow_Up.this).getAuthKey());
                    params.put("kode_kunjungan", kode_kunjungan_final);
                    params.put("modified_by", AuthData.getInstance(getBaseContext()).getAksesData());
                    params.put("pembahasan", bahasan);
                    params.put("latitude", String.valueOf(latitude));
                    params.put("longitude", String.valueOf(longitude));
                    params.put("prospek", String.valueOf(prospek));
                    params.put("kendala", kendala_text);
                    return params;
                }

                /*
                 * Here we are passing image by renaming it with a unique name
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();
                    params.put("foto_kunjungan", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    return params;
                }
            };

            //adding the request to volley
            Volley.newRequestQueue(this).add(volleyMultipartRequest);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                contentURI = data.getData();
                try {
                     bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(Form_Follow_Up.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    gambar.setImageBitmap(bitmap);
//                    base64Stringfoto = ImageUtil.convert(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Form_Follow_Up.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
             bitmap = (Bitmap) data.getExtras().get("data");
            gambar.setImageBitmap(bitmap);
//            base64Stringfoto = ImageUtil.convert(thumbnail);
//            saveImage(thumbnail);
            Toast.makeText(Form_Follow_Up.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }
    public String saveImage(Bitmap myBitmap) {//save image to handphone storage
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
//                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

}
