package application.nis.com.placementcell;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.HashMap;

public class CompanyRegistration extends AppCompatActivity {
    private AutoCompleteTextView companyname,companytype,companycontactperson,email,mobile,password;
    private Button csignup,clogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_registration);
        companyname=(AutoCompleteTextView)findViewById(R.id.CompanyName);
        companytype=(AutoCompleteTextView)findViewById(R.id.CompanyType);
        companycontactperson=(AutoCompleteTextView)findViewById(R.id.CContactPerson);
        email=(AutoCompleteTextView)findViewById(R.id.CEmail);
        mobile=(AutoCompleteTextView)findViewById(R.id.CContactNumber);
        password=(AutoCompleteTextView)findViewById(R.id.cpassword);
        csignup=(Button)findViewById(R.id.CSignUpButton);
        clogin=(Button)findViewById(R.id.Clogin);
        csignup.setOnClickListener(new btn_click(this));
        clogin.setOnClickListener(new btn_click(this));
    }

    class btn_click implements View.OnClickListener {
        public Context ctx;
        public btn_click(Context ctx) {
            this.ctx=ctx;
        }

        @Override
        public void onClick(View view) {
            if(view==csignup) {
                HashMap<String, String> obj = new HashMap<String, String>();
                obj.put("ctype", companytype.getText().toString());
                obj.put("cemail", email.getText().toString());
                obj.put("ccontactperson", companycontactperson.getText().toString());
                obj.put("cmobileno", mobile.getText().toString());
                obj.put("cname", companyname.getText().toString());
                obj.put("cpwd", password.getText().toString());
                Intent intent=new Intent(ctx,CompanyLoginActivity.class);
                CallHttp FT = new CallHttp(ctx, "CompanyRegistration", obj,intent);
            }
            else if (view==clogin)
            {
                Intent intent=new Intent(ctx,CompanyLoginActivity.class);
                startActivity(intent);
            }
        }
    }
}


