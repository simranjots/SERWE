package com.serwe.serwe.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.serwe.serwe.Home.MainActivity;
import com.serwe.serwe.R;


public class LoginActivity extends AppCompatActivity {


    DatabaseReference reference;
    FirebaseDatabase database;
    EditText txt1, txt2;
    TextView edt1, edt2;
    Button btn1, btnreg;
    String name, pass;
    SharedPreferences pref;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        txt1 = (EditText) findViewById(R.id.editTextEmail);
        txt2 = (EditText) findViewById(R.id.editTextPassword);
        edt1 = (TextView) findViewById(R.id.edt1);
        edt2 = (TextView) findViewById(R.id.edt2);
        btn1 = (Button) findViewById(R.id.btn1);
        btnreg = (Button) findViewById(R.id.btnreg);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(LoginActivity.this);
                progress.setTitle("Please Wait!!");
                progress.setMessage("Wait!!");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();

                name = txt1.getText().toString();
                pass = txt2.getText().toString();

                Query q = reference.orderByChild("email").equalTo(name);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long count = dataSnapshot.getChildrenCount();
                        String spass = "", username = "", userpic = "", userEmail = "";
                        if (count > 0) {

                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                users user = data.getValue(users.class);
                                spass = user.getPassword();
                                username = user.getName();
                                userpic = user.getImage();
                                userEmail = user.getPhoneno();

                            }
                            if (spass.equals(pass)) {
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("KEY_NAME", username);
                                editor.putString("KEY_PIC", userpic);
                                editor.putString("KEY_Email", name);
                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "Login Success!!!!", Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Fail!!!!", Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "invalid credential", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }


                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);


            }
        });


    }


}
