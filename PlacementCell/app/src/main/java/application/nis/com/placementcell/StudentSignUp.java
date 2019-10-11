package application.nis.com.placementcell;

import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;


import java.util.HashMap;


public class StudentSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
     private AutoCompleteTextView name,email,cgpa,rollno,Hs_marks,metric_marks,password;
     private Button signup,login;
     private Spinner branch;
     private String bvalue;
     String[] branches = {"--Select Branch--","CSE","IT","ECE","EE","ME","CE"};
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_student_sign_up);
            name=(AutoCompleteTextView)findViewById(R.id.Name);
            email=(AutoCompleteTextView)findViewById(R.id.Email);
            cgpa=(AutoCompleteTextView)findViewById(R.id.CGPA);
            branch=(Spinner)findViewById(R.id.branch);
            rollno=(AutoCompleteTextView)findViewById(R.id.Rollno);
            Hs_marks=(AutoCompleteTextView)findViewById(R.id.HS_MARKS);
            metric_marks=(AutoCompleteTextView)findViewById(R.id.METRIC_MARKS);
            password=(AutoCompleteTextView)findViewById(R.id.password);
            signup=(Button)findViewById(R.id.SignUpButton);
            login=(Button)findViewById(R.id.login);
            signup.setOnClickListener(new btn_click(this));
            login.setOnClickListener(new btn_click(this));
            branch.setOnItemSelectedListener(this);
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,branches);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            branch.setAdapter(aa);
     }

    class btn_click implements View.OnClickListener {
        Context ctx;

        public btn_click(Context c) {
            ctx = c;
        }

        @Override
        public void onClick(View view) {
         if(view==signup) {
             HashMap<String,String> obj=new HashMap<String, String>();
             obj.put("sname",name.getText().toString());
             obj.put("semail",email.getText().toString());
             obj.put("scgpa",cgpa.getText().toString());
             obj.put("sbranch",bvalue);
             obj.put("srollno",rollno.getText().toString());
             obj.put("spwd",password.getText().toString());
             obj.put("shs_marks",Hs_marks.getText().toString());
             obj.put("smetric_marks",metric_marks.getText().toString());
             Intent intent=new Intent(getApplicationContext(),StudentLoginActivity.class);
             CallHttp ct=new  CallHttp(ctx,"Student_Registration",obj,intent);

         }
         else if(view==login) {
             Intent intent=new Intent(ctx,StudentLoginActivity.class);
             startActivity(intent);
         }
     }
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        bvalue=branches[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
    }

}

