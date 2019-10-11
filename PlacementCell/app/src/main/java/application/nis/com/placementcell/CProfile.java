package application.nis.com.placementcell;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CProfile extends AppCompatActivity {
    private TextView name;
    private ListView listView;
    private Button modify,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cprofile);
        SharedPreferences SP=getSharedPreferences("csession",0);

        if(!(SP.contains("PASSWORD"))) {
            Intent intent=new Intent(this,CompanyLoginActivity.class);
            startActivity(intent);
        }

        name=(TextView)findViewById(R.id.textView3);
        listView=(ListView)findViewById(R.id.clistview);
        modify=(Button)findViewById(R.id.cedit);
        logout=(Button)findViewById(R.id.clogout);
        modify.setOnClickListener(new btn_click(this));
        logout.setOnClickListener(new btn_click(this));
        SP=getSharedPreferences("csession",0);
        String cname=SP.getString("NAME","").toUpperCase();
        name.setText(cname);
        String maintitle[]={"Name","Type","Contact No","Email","Contact Person",};
        String subtitle[]={SP.getString("NAME",""),SP.getString("COMPANYTYPE",""),SP.getString("MOBILENO",""),SP.getString("CONTACTEMAIL",""),SP.getString("CONTACTPERSON","")};
        CustomAdapter1 C1=new CustomAdapter1(this,maintitle,subtitle,"ForCompanyUpdate");
        listView.setAdapter(C1);

    }

    class btn_click implements View.OnClickListener {
       public Context ctx;
       public btn_click(Context ctx) {
           this.ctx=ctx;
       }

      public void onClick(View view) {
          if(view==modify) {
            Intent intent=new Intent(ctx,CompanyUpdate.class);
            startActivity(intent);
          }

          else if(view==logout) {
              AlertDialog.Builder ad=new AlertDialog.Builder(ctx);
              ad.setTitle("Logout Confirmation");
              ad.setMessage("Are You Sure To log out");
              ad.setCancelable(true);
              ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                      SharedPreferences SP=getSharedPreferences("csession",0);
                      SharedPreferences.Editor editor= SP.edit();
                      editor.clear();
                      editor.commit();
                      Intent intent=new Intent(ctx,CompanyLoginActivity.class);
                      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                      startActivity(intent);

                  }
              });

             ad.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) { }
             }) ;
             ad.show();
          }
      }
    }
}
