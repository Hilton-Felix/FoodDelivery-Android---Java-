package com.project.us4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import Models.User;

public class AdminAddBusiness extends AppCompatActivity {

    EditText fnameBEDT, BusinessnameBEDT, emailBEDT, phoneBEDT, passwordBEDT, confirmPasswordBEDT;
    Button regBTN;
    TextView signInTXT;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        signInTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddBusiness.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        regBTN = findViewById(R.id.reg_button);
        regBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = fnameBEDT.getText().toString().trim();
                String Bname = BusinessnameBEDT.getText().toString().trim();
                String phone = phoneBEDT.getText().toString().trim();
                String email = emailBEDT.getText().toString().trim();
                String password = passwordBEDT.getText().toString().trim();
                String confirmpassword = confirmPasswordBEDT.getText().toString().trim();

                boolean isValid = validateUserInputs(fname, Bname, phone, email, password, confirmpassword);
                if (isValid) {
                    registerUser(fname, Bname, phone, email, password);
                } else
                    return;
            }
        });
    }

    private void initViews() {

        fnameBEDT = findViewById(R.id.editText_reg_fname);
        BusinessnameBEDT = findViewById(R.id.edit_text_reg_lname);
        emailBEDT = findViewById(R.id.edit_text_reg_email);
        phoneBEDT = findViewById(R.id.editText_reg_phone);
        passwordBEDT = findViewById(R.id.editText_reg_password);
        confirmPasswordBEDT = findViewById(R.id.edit_text_reg_confirm_password);
        signInTXT = findViewById(R.id.text_view_already_have_account);
    }

    //Validating user inputs
    private boolean validateUserInputs(String fname, String lname, String phone, String email, String password, String confirmpassword) {
        if (email.isEmpty()) {
            emailBEDT.setError("required");
            emailBEDT.requestFocus();
            return false;
        }
        if (fname.isEmpty()) {
            fnameBEDT.setError("required");
            fnameBEDT.requestFocus();
            return false;
        }
        if (lname.isEmpty()) {
            BusinessnameBEDT.setError("required");
            BusinessnameBEDT.requestFocus();
            return false;
        }
        if (phone.isEmpty()) {
            phoneBEDT.setError("Phone number required");
            phoneBEDT.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            passwordBEDT.setError("The password cannot be empty");
            passwordBEDT.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailBEDT.setError("Please provide a valid email");
            emailBEDT.requestFocus();
            return false;
        }
        if (password.length() < 8) {
            passwordBEDT.setError("Min password length is 8");
            passwordBEDT.requestFocus();
            return false;
        }
        if (confirmpassword.length() < 8) {
            confirmPasswordBEDT.setError("Min password length is 8");
            confirmPasswordBEDT.requestFocus();
            return false;
        }
        if (!TextUtils.equals(password, confirmpassword)) {
            Toast.makeText(this, "Passwords don't match.Try again", Toast.LENGTH_LONG).show();
            return false;
        } else
            return true;
    }

    //Registering user
    private void registerUser(String fname, String Bname, String phone, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                FirebaseUser rUser = mAuth.getCurrentUser();
                assert rUser != null;
                String userId = rUser.getUid();
                User userObj = new User(fname, Bname, phone, email);

                //Calling the saveUserFunction that saves the information in the firestore
                saveUserInfo(userId, userObj);


            } else {
                String errMSG = task.getException().getMessage();
                Toast.makeText(this, errMSG, Toast.LENGTH_LONG).show();
            }
        });

    }

    //This method Saves the User information in the realtime database
    private void saveUserInfo(String userId, User user) {
        //Setting up the Progress dialog
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        //end
        System.out.println("THE USER'S ID: " + userId);

        firestore.collection("US4U").document("USERS").collection(userId).add(user)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        progressDialog.dismiss();
                        clearUserInputs();
                        if (task.isSuccessful()) {
                            //end
                            Toast.makeText(AdminAddBusiness.this, "Registration is successfull", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AdminAddBusiness.this, Login.class));
                            finish();
                        } else {
                            Toast.makeText(AdminAddBusiness.this, "Failed to Register", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //Clearing the Edit text inputs
    private void clearUserInputs() {

        emailBEDT.setText("");
        fnameBEDT.setText("");
        BusinessnameBEDT.setText("");
        phoneBEDT.setText("");
        emailBEDT.setText("");
        passwordBEDT.setText("");
        confirmPasswordBEDT.setText("");
    }
}


