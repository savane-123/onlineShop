package com.savane.activities.user;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.savane.R;
import com.savane.activities.map.PermissionsActivity;
import com.savane.api.RetrofitClient;
import com.savane.data.model.DefaultResponse;
import com.savane.share.datePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sign_up  extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
        EditText firstName, lastName, email, phone, Password;
       TextView DoB;
        RadioGroup gender;
    RadioButton genderRadioButton;
    Button buttonSigup;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.fName);
        lastName = findViewById(R.id.lName);
        email = findViewById(R.id.Email);
        phone = findViewById(R.id.Phone);
        DoB = findViewById(R.id.DoB);
        Password = findViewById(R.id.Password);
        gender = findViewById(R.id.genderGroup);
        //genderRadioButton = findViewById(gender.getCheckedRadioButtonId());
        buttonSigup = findViewById(R.id.sign_up);
        login = findViewById(R.id.login);

        buttonSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), Login.class);
               startActivity(i);
            }
        });
        DoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepiker= new datePickerFragment();
                datepiker.show(getSupportFragmentManager(),"Date picker");
            }
        });
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c= Calendar.getInstance();
         c.set(Calendar.YEAR,year);
         c.set(Calendar.MONTH,month);
         c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
         String DateFormateString=new SimpleDateFormat("yyyy/MM/dd").format(c.getTime());
         //String DateFormateString= DateFormat.getDateInstance(DateFormat.Fu).format(c.getTime());
         TextView textView= findViewById(R.id.DoB);
         textView.setText(DateFormateString);
    }

    private void signup(){
        String fname=firstName.getText().toString().trim();
        String lname=lastName.getText().toString().trim();
        String mail=email.getText().toString().trim();
        String uphone=phone.getText().toString().trim();
        String uDob=DoB.getText().toString().trim();
        String upassword=Password.getText().toString().trim();
        //String ugender=genderRadioButton.getText().toString().trim();
        //String utype=Utype.getText().toString().trim();
        int selectid= gender.getCheckedRadioButtonId();
        genderRadioButton=findViewById(selectid);
        String gen=genderRadioButton.getText().toString();
        //Toast.makeText(getApplicationContext(),gen,Toast.LENGTH_LONG).show();
        if (fname.isEmpty()){
          firstName.setError(" first Name is required");
          firstName.requestFocus();
          return;
      }
      if (lname.isEmpty()){
          lastName.setError("last name is required");
          lastName.requestFocus();
          return;
      }
      if (mail.isEmpty()){
          email.setError("Email is required");
          email.requestFocus();
          return;
      }
      if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
          email.setError("Enter Valid email");
          email.requestFocus();
          return;
      }
      if (uphone.isEmpty()){
          phone.setError("phone number is required");
          phone.requestFocus();
          return;
      }
      if (uDob.isEmpty()){
          DoB.setError("Date of birth is required");
          DoB.requestFocus();
          return;
      }
      if (upassword.isEmpty()){
          Password.setError("Age is required");
          Password.requestFocus();
          return;
      }
      if (upassword.length()<4){
          Password.setError("the length of the password should be more than 4 character");
          Password.requestFocus();
          return;
      }
      Call<DefaultResponse> call= RetrofitClient
              .getInstance()
              .getApi()
              .createUser(fname,lname,mail,uphone,
                      uDob,upassword,gen);
      call.enqueue(new Callback<DefaultResponse>() {
          @Override
          public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
               if(response.code()==201 ){
                  DefaultResponse dr=response.body();
                  Toast.makeText(sign_up.this,dr.getMsg(),Toast.LENGTH_LONG).show();
                   Intent i= new Intent(getApplicationContext(), PermissionsActivity.class);
                   i.putExtra("email",mail);
                   startActivity(i);
              }else {
                  Toast.makeText(sign_up.this,"Http code "+response.code()+"message "+response.message(),Toast.LENGTH_LONG).show();
              }

          }

          @Override
          public void onFailure(Call<DefaultResponse> call, Throwable t) {
              Toast.makeText(sign_up.this,t.getMessage()+"unable to create user",Toast.LENGTH_LONG).show();

          }
      });
  }
}
