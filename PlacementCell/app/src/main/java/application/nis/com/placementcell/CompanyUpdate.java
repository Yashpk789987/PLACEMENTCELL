package application.nis.com.placementcell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.HashMap;

public class CompanyUpdate extends AppCompatActivity {
    private AutoCompleteTextView companyname,companytype,companycontactperson,email,mobile,password;
    private Button updatebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_update);
        SharedPreferences SP=getSharedPreferences("csession",0);
        companyname=(AutoCompleteTextView)findViewById(R.id.CompanyName);
        companytype=(AutoCompleteTextView)findViewById(R.id.CompanyType);
        companycontactperson=(AutoCompleteTextView)findViewById(R.id.CContactPerson);
        email=(AutoCompleteTextView)findViewById(R.id.CEmail);
        mobile=(AutoCompleteTextView)findViewById(R.id.CContactNumber);
        password=(AutoCompleteTextView)findViewById(R.id.cpassword);
        updatebtn=(Button)findViewById(R.id.updatebtn);
        companyname.setText(SP.getString("NAME",""));
        companytype.setText(SP.getString("COMPANYTYPE",""));
        companycontactperson.setText(SP.getString("CONTACTPERSON",""));
        email.setText(SP.getString("CONTACTEMAIL",""));
        mobile.setText(SP.getString("MOBILENO",""));
        password.setText(SP.getString("PASSWORD",""));
        updatebtn.setOnClickListener(new btn_click(this));
    }

    class btn_click implements View.OnClickListener {
        public Context ctx;

        public btn_click(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        public void onClick(View view) {
            SharedPreferences SP= getSharedPreferences("csession",0);
            SharedPreferences.Editor editor=SP.edit();
            editor.putString("NAME",companyname.getText().toString());
            editor.putString("COMPANYTYPE",companytype.getText().toString());
            editor.putString("CONTACTPERSON",companycontactperson.getText().toString());
            editor.putString("CONTACTEMAIL",email.getText().toString());
            editor.putString("MOBILENO",mobile.getText().toString());
            editor.putString("PASSWORD",password.getText().toString());
            editor.commit();
            HashMap<String, String> obj = new HashMap<String, String>();
            obj.put("ctype", companytype.getText().toString());
            obj.put("cemail", email.getText().toString());
            obj.put("ccontactperson", companycontactperson.getText().toString());
            obj.put("cmobileno", mobile.getText().toString());
            obj.put("cname", companyname.getText().toString());
            obj.put("cpwd", password.getText().toString());
            Intent intent = new Intent(ctx, CompanyHome.class);
            CallHttp FT = new CallHttp(ctx, "CompanyUpdate", obj,intent);
        }
    }
}

