package com.project.us4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class WhoIsUser extends AppCompatActivity  {
    private RadioGroup radioGroup;
    private RadioButton rButton1,rButton2;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_is_user);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        rButton1=(RadioButton)findViewById(R.id.rButton1);
        rButton2=(RadioButton)findViewById(R.id.rButton2);
        next=(Button)findViewById(R.id.button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rButton1.isChecked()){
                    Intent myintent = new Intent(WhoIsUser.this, Login.class);
                    startActivity(myintent);
                }else if(rButton2.isChecked()){
                    Intent intent = new Intent(WhoIsUser.this, BusinessLogin.class);
                    startActivity(intent);
                }

            }
        });
    }
}