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

public class AddNewVisit extends AppCompatActivity {
    private AutoCompleteTextView vname,vrcgpa,vrhsc,vrmetric,vbranch,vaddress;
    private Button addnewvisitbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_visit);
        vname=(AutoCompleteTextView)findViewById(R.id.vname);
        vrcgpa=(AutoCompleteTextView)findViewById(R.id.RequiredCgpa);
        vrhsc=(AutoCompleteTextView)findViewById(R.id.vrhsc);
        vrmetric=(AutoCompleteTextView)findViewById(R.id.vrmetric);
        vbranch=(AutoCompleteTextView)findViewById(R.id.vbranch);
        vaddress=(AutoCompleteTextView)findViewById(R.id.vaddress);
        addnewvisitbtn=(Button)findViewById(R.id.newvisitbtn);
        addnewvisitbtn.setOnClickListener(new btn_click(this));
    }

    class btn_click implements View.OnClickListener {
        public Context ctx;

        public btn_click(Context ctx) {
            this.ctx=ctx;
        }

        public void onClick(View view) {
         SharedPreferences SP=getSharedPreferences("csession",0);
         String cid=SP.getString("COMPANYID","");
         HashMap<String,String> obj=new HashMap<String, String>();
         obj.put("cid",cid);
         obj.put("vplace",vaddress.getText().toString());
         obj.put("vbranch",vbranch.getText().toString());
         obj.put("vname",vname.getText().toString());
         obj.put("vrcgpa",vrcgpa.getText().toString());
         obj.put("vrhsc",vrhsc.getText().toString());
         obj.put("vrmetric",vrmetric.getText().toString());
         Intent intent=new Intent(getApplicationContext(),CompanyHome.class);
         CallHttp CH=new CallHttp(ctx,"AddNewVisit",obj,intent);
       }
    }
}

