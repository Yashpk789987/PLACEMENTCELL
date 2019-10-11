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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class VInfo extends AppCompatActivity {
    private Button vedit,vdelete;
    private TextView visitname;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinfo);
        Bundle bundle=getIntent().getExtras();
        String vid=bundle.getString("VID");
        System.out.println("VInfo"+vid);
        SharedPreferences SVP=getSharedPreferences("visitinfo",0);
        SharedPreferences.Editor editor=SVP.edit();
        final Context ctx1=this;
       /* HttpGetCall  C=new HttpGetCall(getApplicationContext(),"DisplayVisitById");
        boolean t=C.call(vid);
        System.out.println(t);  */
        final Context ctx=this;
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/DisplayVisitById?vid="+vid;
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    SharedPreferences SVP=getSharedPreferences("visitinfo",0);
                    SharedPreferences.Editor editor=SVP.edit();
                    JSONObject obj = (JSONObject) response.get(0);
                    editor.putString("NAME",obj.getString("NAME"));
                    editor.putString("BRANCH",obj.getString("BRANCH"));
                    editor.putString("VISITID",obj.getString("VISITID"));
                    editor.putString("VISITPLACE",obj.getString("VISITPLACE"));
                    editor.putString("REQUIRED_METRIC_MARKS",obj.getString("REQUIRED_METRIC_MARKS"));
                    editor.putString("REQUIRED_HSC_MARKS",obj.getString("REQUIRED_HSC_MARKS"));
                    editor.putString("REQUIRED_CGPA",obj.getString("REQUIRED_CGPA"));
                    editor.putString("COMPANYID",obj.getString("COMPANYID"));
                    editor.commit();
                    vedit=(Button)findViewById(R.id.vedit);
                    vdelete=(Button)findViewById(R.id.vdelete);
                    visitname=(TextView)findViewById(R.id.visitname);

                    lv=(ListView)findViewById(R.id.vlistview);

                    vedit.setOnClickListener(new btn_click(ctx1));
                    vdelete.setOnClickListener(new btn_click(ctx1));
                    SharedPreferences SP=getSharedPreferences("visitinfo",0);
                    String vname=SP.getString("NAME","").toUpperCase();
                    visitname.setText(vname);
                    String maintitle[]={"Name","Branches","B.E. Req.","12th Req.","10th Req.","Place"};
                    String subtitle[]={SP.getString("NAME",""),SP.getString("BRANCH",""),SP.getString("REQUIRED_CGPA",""),SP.getString("REQUIRED_HSC_MARKS",""),SP.getString("REQUIRED_METRIC_MARKS",""),SP.getString("VISITPLACE","")};
                    CustomAdapter1 C1=new CustomAdapter1(getApplicationContext(),maintitle,subtitle,"ForVisitEdit");
                    System.out.println("I am Invoked...  Custom adapter");
                    lv.setAdapter(C1);
                    System.out.println("  Task Completed ");
                }catch (Exception e)
                {}
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }


        );
        queue.add(request);


    }
    class btn_click implements View.OnClickListener {
        public Context ctx;

        public btn_click(Context ctx) {
            this.ctx=ctx;
        }

        public void onClick(View view) {
            if(view==vedit) {
                Intent intent=new Intent(ctx,VisitModify.class);
                startActivity(intent);
            }

            else if(view==vdelete) {
                AlertDialog.Builder ad=new AlertDialog.Builder(ctx);
                ad.setTitle("Confirmation");
                ad.setMessage("Are You Sure To Want To Delete This Visit ");
                ad.setCancelable(true);
                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences SP=getSharedPreferences("visitinfo",0);
                        HashMap<String,String> obj=new HashMap<String, String>();
                        obj.put("vid",SP.getString("VISITID",""));
                        Intent intent=new Intent(ctx,CompanyHome.class);
                        CallHttp C=new CallHttp(ctx,"DELETEVISIT",obj,intent);
                        finish();
                    }
                });

                ad.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }) ;
                ad.show();
            }

        }
    }
}

