package application.nis.com.placementcell;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class CompanyLoginActivity extends AppCompatActivity {
    private Button clogin,csignup;
    private AutoCompleteTextView cemail,cpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);
        cemail=(AutoCompleteTextView)findViewById(R.id.cemail);
        cpassword=(AutoCompleteTextView)findViewById(R.id.cpassword);
        clogin=(Button)findViewById(R.id.Cloginbtn);
        csignup=(Button)findViewById(R.id.csignupbutton);
        clogin.setOnClickListener(new btn_click(this));
        csignup.setOnClickListener(new btn_click(this));
    }

    class btn_click implements View.OnClickListener {
      public Context ctx;

      public btn_click(Context ctx) {
          this.ctx=ctx;
      }

      public void onClick(View view) {
          if(view==csignup) {
              Intent intent=new Intent(ctx,CompanyRegistration.class);
              startActivity(intent);
          } else if(view==clogin) {
              final ProgressDialog progress=new ProgressDialog(ctx);
              progress.setMessage("Loging In");
              progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
              progress.setIndeterminate(true);
              progress.setProgress(0);
              progress.show();
              final HashMap<String,String> obj=new HashMap<String, String>();
              obj.put("cemail",cemail.getText().toString());
              obj.put("cpwd",cpassword.getText().toString());
              String url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/CompanyCheckLogin?cemail="+obj.get("cemail")+"&cpwd="+obj.get("cpwd");
              RequestQueue queue= Volley.newRequestQueue(ctx);
              JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                      @Override
                      public void onResponse(JSONArray response) {
                          SharedPreferences SP=getSharedPreferences("csession",0);
                          SharedPreferences.Editor editor=SP.edit();
                          editor.clear();
                          editor.commit();
                        try{
                            JSONObject object=(JSONObject) response.get(0);
                            Iterator<String> itr=object.keys();
                            LinkedList<String> k=new LinkedList<String>();
                            int i=0;
                            while (itr.hasNext()) {
                                k.add(itr.next());
                            }

                            for(int j=0;j<object.length();j++) {
                              editor.putString(k.get(j),object.get(k.get(j)).toString());
                            }

                            editor.commit();
                            Intent intent=new Intent(ctx,CompanyHome.class);
                            progress.dismiss();
                            startActivity(intent);
                        }catch (Exception e) {
                            e.printStackTrace();
                            Toast toast=Toast.makeText(ctx,"Wrong User Id/Password",Toast.LENGTH_LONG);
                            toast.setGravity(17,0,0);
                            toast.show();
                        }
                      }
                    }, new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         progress.dismiss();
                         error.printStackTrace();
                         Toast toast=Toast.makeText(ctx,"No Internet Connection ",Toast.LENGTH_LONG);
                                  toast.setGravity(17,0,0);
                                  toast.show();
                     }
              })
              {
                  @Override
                  protected Map<String, String> getParams() throws AuthFailureError {
                      return obj;
                  }
              };
              queue.add(request);
          }
      }
    }
}
