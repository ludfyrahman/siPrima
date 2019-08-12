package com.android.primaitech.siprima.Kegiatan;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Config.VolleyMultipartRequest;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ubah_Kegiatan extends AppCompatActivity {
    EditText kendala,solusi;
    Button pilih_gambar, simpan;
    ImageView gambar;
    private static final String IMAGE_DIRECTORY = "/PrimaGroup";
    private int GALLERY = 1, CAMERA = 2;
    Bitmap bitmap;
    ProgressDialog pd;
    private Uri contentURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_kegiatan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ubah_Kegiatan.this.onBackPressed();
            }
        });
        pd = new ProgressDialog(Ubah_Kegiatan.this);
        gambar = (ImageView) findViewById(R.id.gambar);
        kendala = (EditText)findViewById(R.id.kendala);
        solusi = (EditText)findViewById(R.id.solusi);
        simpan = (Button) findViewById(R.id.simpan);
        pilih_gambar = (Button)findViewById(R.id.pilih_gambar);
        pilih_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        requestMultiplePermissions();
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
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
        final String kode_kegiatan = data.getStringExtra("kode");
        if(kendala.getText().toString().equals("")){
            Toast.makeText(getBaseContext(), "Kendala masih kosong", Toast.LENGTH_SHORT).show();
        }else if(solusi.getText().toString().equals("")){
            Toast.makeText(getBaseContext(), "Solusi masih kosong", Toast.LENGTH_SHORT).show();
        }else{
            pd.show();
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(
                    Request.Method.POST,
                    ServerAccess.URL_KEGIATAN+"updatekegiatan",
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(new String(response.data));
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {

                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
//                                    ((Detail_Kegiatan)getBaseContext()).reload();
//                                    Ubah_Kegiatan.this.onBackPressed();
                                    startActivity(new Intent(getBaseContext(), Kegiatan.class));

                                } else {
                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } catch (JSONException e) {

                                Toast.makeText(
                                        getBaseContext(),
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
                                    getBaseContext(),
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
                    params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
                    params.put("kode_kegiatan", kode_kegiatan);
                    params.put("kode_karyawan", AuthData.getInstance(getBaseContext()).getAksesData());
                    params.put("kendala", kendala.getText().toString());
                    params.put("solusi", solusi.getText().toString());
                    return params;
                }

                /*
                 * Here we are passing image by renaming it with a unique name
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();
                    params.put("foto_kegiatan", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    return params;
                }
            };

            //adding the request to volley
            Volley.newRequestQueue(getBaseContext()).add(volleyMultipartRequest);
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
                    Toast.makeText(getBaseContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    gambar.setImageBitmap(bitmap);
//                    base64Stringfoto = ImageUtil.convert(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            gambar.setImageBitmap(bitmap);
//            base64Stringfoto = ImageUtil.convert(thumbnail);
//            saveImage(thumbnail);
            Toast.makeText(getBaseContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
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
            MediaScannerConnection.scanFile(getBaseContext(),
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
                        Toast.makeText(getBaseContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
}
