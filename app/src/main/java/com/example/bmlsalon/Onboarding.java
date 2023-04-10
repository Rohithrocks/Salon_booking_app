package com.example.bmlsalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Onboarding extends AppCompatActivity implements View.OnClickListener {

    private Button getstarted,signupforgetpass,next,passlogin,signup_login,continue_btn,user_login,forgotpass_link;

    private TextView forgetpasstext,loginsignupcard,back_pass;

    private CardView logincard,forgetpasscard,signupcard,passcard;

    private EditText namesignup,emailsignup,phonenumber,enterpass_signup,re_enterpass_signup,email_login,password_login,forgetpass_email;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDialog;

    VideoView videoView;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        getstarted = findViewById(R.id.getstarted);
        videoView = findViewById(R.id.video_bg);
        logincard = findViewById(R.id.login_card);
        forgetpasstext = findViewById(R.id.forget_password_login_text);
        forgetpasscard = findViewById(R.id.forget_password_card);
        signupforgetpass=findViewById(R.id.signUp_btn_forgotPassword);
        signupcard = findViewById(R.id.signup_card);
        loginsignupcard=findViewById(R.id.logIn_btn_signUp);
        namesignup=findViewById(R.id.signup_name);
        emailsignup=findViewById(R.id.signup_email);
        phonenumber=findViewById(R.id.signup_phone_number);
        next=findViewById(R.id.signup_next_btn);
        passcard=findViewById(R.id.set_pass_card);
        enterpass_signup=findViewById(R.id.enter_pass_re_edit);
        re_enterpass_signup=findViewById(R.id.re_enter_pass_ree_edit);
        back_pass=findViewById(R.id.go_back);
        passlogin=findViewById(R.id.pass_login);
        signup_login=findViewById(R.id.signUp_btn_logInCard);
        email_login=findViewById(R.id.email_address);
        password_login=findViewById(R.id.password);
        continue_btn= findViewById(R.id.continue_btn);
        user_login=findViewById(R.id.login_button);
        forgetpass_email=findViewById(R.id.forgot_pass_email);
        forgotpass_link=findViewById(R.id.send_link_btn);

        getstarted.setOnClickListener(this);
        logincard.setOnClickListener(this);
        forgetpasstext.setOnClickListener(this);
        forgetpasscard.setOnClickListener(this);
        signupforgetpass.setOnClickListener(this);
        signupcard.setOnClickListener(this);
        loginsignupcard.setOnClickListener(this);
        namesignup.setOnClickListener(this);
        emailsignup.setOnClickListener(this);
        phonenumber.setOnClickListener(this);
        next.setOnClickListener(this);
        passcard.setOnClickListener(this);
        enterpass_signup.setOnClickListener(this);
        re_enterpass_signup.setOnClickListener(this);
        back_pass.setOnClickListener(this);
        passlogin.setOnClickListener(this);
        signup_login.setOnClickListener(this);
        email_login.setOnClickListener(this);
        password_login.setOnClickListener(this);
        continue_btn.setOnClickListener(this);
        user_login.setOnClickListener(this);
        forgetpass_email.setOnClickListener(this);
        forgotpass_link.setOnClickListener(this);

        progressDialog=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video2);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }




    @Override
    protected void onPostResume() {
        videoView.resume();
        super.onPostResume();
    }

    @Override
    protected void onRestart() {
        videoView.start();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        videoView.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.getstarted):
                getstarted.setVisibility(View.GONE);
                logincard.setVisibility(View.VISIBLE);
                break;
            case(R.id.forget_password_login_text):
                logincard.setVisibility(View.GONE);
                forgetpasscard.setVisibility(View.VISIBLE);
                break;
            case(R.id.signUp_btn_forgotPassword):
                forgetpasscard.setVisibility(View.GONE);
                signupcard.setVisibility(View.VISIBLE);
                break;
            case(R.id.logIn_btn_signUp):
                signupcard.setVisibility(View.GONE);
                logincard.setVisibility(View.VISIBLE);
                break;
            case(R.id.go_back):
                passcard.setVisibility(View.GONE);
                signupcard.setVisibility(View.VISIBLE);
                break;
            case(R.id.pass_login):
                passcard.setVisibility(View.GONE);
                logincard.setVisibility(View.VISIBLE);
                break;
            case(R.id.signUp_btn_logInCard):
                logincard.setVisibility(View.GONE);
                signupcard.setVisibility(View.VISIBLE);
                break;
            case(R.id.signup_next_btn):
                BeforeAuth();
                break;
            case(R.id.continue_btn):
                PerformAuth();
                break;
            case(R.id.login_button):
                PerformLogin();
                break;
            case(R.id.send_link_btn):
                ValidateData();
                break;
        }

    }

    private void ValidateData() {
        String ForgotMail = forgetpass_email.getText().toString();
        if(ForgotMail.isEmpty()){
            forgetpass_email.setError("Please Enter an Email");
            Toast.makeText(Onboarding.this, "Please Enter an Email", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.sendPasswordResetEmail(ForgotMail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Onboarding.this, "Check your mail", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(Onboarding.this, "Error: "+task.getException(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    private void PerformLogin() {
        String LoginEmail = email_login.getText().toString();
        String LoginPass = password_login.getText().toString();

        if(!LoginEmail.matches(emailPattern)){
            email_login.setError("Enter Correct Email");
            Toast.makeText(Onboarding.this,"Enter correct Email",Toast.LENGTH_SHORT).show();
        }
        else if(LoginPass.isEmpty() || LoginPass.length()<6){
            password_login.setError("Enter Proper Password");
            Toast.makeText(Onboarding.this, "Enter Proper Password", Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog.setMessage("Logging in...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(LoginEmail,LoginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(Onboarding.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(Onboarding.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void PerformAuth() {
        Toast.makeText(Onboarding.this,"PerformAuth",Toast.LENGTH_SHORT).show();

        String Email = emailsignup.getText().toString();
        String password = enterpass_signup.getText().toString();
        String confirmPass = re_enterpass_signup.getText().toString();

        if(password.isEmpty() || password.length()<6){
            enterpass_signup.setError("Enter a Password minimum of length 6 digits");
            Toast.makeText(Onboarding.this,"Enter a Password minimum of length 6 digits",Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirmPass)){
            re_enterpass_signup.setError("Re-Entered Password doesn't matches the entered password");
            Toast.makeText(Onboarding.this,"Re-Entered Password doesn't matches the entered password",Toast.LENGTH_SHORT).show();
        }

        else{
            progressDialog.setMessage("Please wait while we register you...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(Onboarding.this, "Registration is Successful", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(Onboarding.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(Onboarding.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void BeforeAuth() {
            String Name = namesignup.getText().toString();
            String Email = emailsignup.getText().toString();
            String Number= phonenumber.getText().toString();

            if(Name.isEmpty()){
                namesignup.setError("Enter a Name");
                Toast.makeText(Onboarding.this,"Enter a Name",Toast.LENGTH_SHORT).show();
            }
            else if(!Email.matches(emailPattern)){
                emailsignup.setError("Enter Correct Email");
                Toast.makeText(Onboarding.this,"Enter Correct Email",Toast.LENGTH_SHORT).show();
            }
            else if(Number.isEmpty() || Number.length()<10){
                phonenumber.setError("Enter a correct mobile number");
                Toast.makeText(Onboarding.this,"Enter a correct mobile number",Toast.LENGTH_SHORT).show();
            }
            else{
                signupcard.setVisibility(View.GONE);
                passcard.setVisibility(View.VISIBLE);
            }
    }
}