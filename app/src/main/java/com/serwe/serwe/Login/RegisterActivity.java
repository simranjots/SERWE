package com.serwe.serwe.Login;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.serwe.serwe.R;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity implements IPickResult {

    DatabaseReference reference;
    FirebaseDatabase database;
    ImageView imgt;
    SharedPreferences pref;

    EditText namet, emailt, passt, confrmt, phonet;
    Button btreg;
    String userImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        PickImageDialog.build(new PickSetup()).show(RegisterActivity.this);
        namet = (EditText) findViewById(R.id.name);
        emailt = (EditText) findViewById(R.id.email);
        passt = (EditText) findViewById(R.id.pass);
        confrmt = (EditText) findViewById(R.id.cpass);
        phonet = (EditText) findViewById(R.id.phone);
        btreg = (Button) findViewById(R.id.btnregister);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        imgt = (ImageView) findViewById(R.id.img);


        imgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()).show(RegisterActivity.this);

            }
        });

        btreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, mail, pass, confirm, phone;


                name = namet.getText().toString();
                mail = emailt.getText().toString();
                pass = passt.getText().toString();
                confirm = confrmt.getText().toString();
                phone = phonet.getText().toString();

                if (name.matches("") || mail.matches("") || pass.matches("") || confirm.matches("") || phone.matches("")) {

                    Toast.makeText(RegisterActivity.this, "Please Entered all the fields", Toast.LENGTH_SHORT).show();


                } else {

                    if (pass.equals(confirm)) {

                        String userId = reference.push().getKey();
                        reference.child(userId).setValue(new users(userImage, name, mail, phone, pass, pref.getString("KEY_FCM_TOKEN", "")));


                        Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Retry", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });


    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());

            //If you want the Bitmap.
            imgt.setImageBitmap(r.getBitmap());
            Bitmap bimg = r.getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bimg.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] byteimg = bos.toByteArray();
            userImage = Base64.encodeToString(byteimg, Base64.DEFAULT);

            //Image path
            //r.getPath();
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

