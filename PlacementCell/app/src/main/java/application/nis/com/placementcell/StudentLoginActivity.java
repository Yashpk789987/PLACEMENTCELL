package application.nis.com.placementcell;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;



import android.widget.Button;
import android.widget.EditText;
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


public class StudentLoginActivity extends AppCompatActivity {

    private EditText rollno,password;
    private Button signin,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        rollno=(EditText) findViewById(R.id.rollno);
        password=(EditText) findViewById(R.id.password);
        signin=(Button)findViewById(R.id.signin);
        signin.setOnClickListener(new btn_click(this));
        signup=(Button)findViewById(R.id.signup);
        signup.setOnClickListener(new btn_click(this));
    }

    class btn_click implements View.OnClickListener {
        public Context ctx;
        public btn_click(Context ctx) {
            this.ctx=ctx;
        }
        public void onClick(View view) {
            if(view==signin) {
                  final HashMap<String, String> obj = new HashMap<String, String>();
                  obj.put("srollno", rollno.getText().toString());
                  obj.put("spwd", password.getText().toString());
                  RequestQueue queue = Volley.newRequestQueue(ctx);
                  final ProgressDialog progress=new ProgressDialog(ctx);
                  progress.setMessage("Loging In");
                  progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                  progress.setIndeterminate(true);
                  progress.setProgress(0);
                  progress.show();
                  String url = "http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/StudentCheckLogin?srollno=" + obj.get("srollno") + "&spwd=" + obj.get("spwd");
                  JsonArrayRequest request = new JsonArrayRequest(url, new com.android.volley.Response.Listener<JSONArray>() {
                      @Override
                      public void onResponse(JSONArray response) {
                          SharedPreferences SP=getSharedPreferences("stsession",0);
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
                              Intent intent = new Intent(ctx, StudentHome.class);
                              progress.dismiss();
                              startActivity(intent);}
                              catch (Exception e) {
                                  progress.dismiss();
                                  e.printStackTrace();
                                  Toast toast=Toast.makeText(ctx,"Wrong User Id/Password",Toast.LENGTH_LONG);
                                  toast.setGravity(17,0,0);
                                  toast.show();
                              }
                      }}, new com.android.volley.Response.ErrorListener() {
                              @Override
                              public void onErrorResponse(VolleyError error) {
                                  progress.dismiss();
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
          /////////////////////////////////////////////////////////////////
          RequestQueue queue2= Volley.newRequestQueue(getApplicationContext());
          String url2="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/DisplayAllUpdatedVisits";
          JsonArrayRequest request2=new JsonArrayRequest(url2, new Response.Listener<JSONArray>() {
              @Override
              public void onResponse(JSONArray response) {

                  Integer counter1 = 0, counter2 = 0;
                  for(int i=0;i<response.length();i++) {
                      try {
                          SharedPreferences SP=getSharedPreferences("stsession",0);
                          JSONObject obj=(JSONObject) response.get(i);
                          if((Float.parseFloat(SP.getString("HS_MARKS",""))>=(float)(obj.getDouble("REQUIRED_HSC_MARKS")))&&(Float.parseFloat(SP.getString("METRIC_MARKS",""))>=(float)(obj.getDouble("REQUIRED_METRIC_MARKS")))&&(Float.parseFloat(SP.getString("CGPA",""))>=(float)(obj.getDouble("REQUIRED_CGPA")))) {
                              counter1++;
                          }
                      }catch (Exception e) {
                          System.out.println(e);
                      }
                  }
                  int flag=0;

                  NotificationCompat.Builder builder =
                          new NotificationCompat.Builder(ctx)
                                  .setContentTitle("New Update For You")
                                  .setSmallIcon(R.drawable.notification)
                                  .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                  .setContentText("A New Company Is coming Soon..");

                  SharedPreferences SP3=getSharedPreferences("previousnoofvisits",0);
                  try{
                      counter2=Integer.parseInt(SP3.getString("pv",""));
                  }catch(Exception e) {
                      System.out.println(e.getMessage());
                  }

                  if(!(counter1==counter2)) {
                      Intent notificationIntent = new Intent(ctx, StudentHome.class);
                      PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, notificationIntent,
                              PendingIntent.FLAG_UPDATE_CURRENT);
                      builder.setContentIntent(contentIntent);
                      NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                      builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
                      builder.setAutoCancel(true);
                      manager.notify(0, builder.build());
                      flag=1;
                  }

                  if(flag==1) {
                      NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                  }

                  SharedPreferences.Editor editor=SP3.edit();
                  editor.putString("pv",counter1.toString());
                  editor.commit();
               System.out.println("Total Visits"+counter1);
              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {

              }
          });
          queue2.add(request2);

      }
      else if(view==signup) {
          Intent intent=new Intent(ctx,StudentSignUp.class);
          startActivity(intent);
      } }
    }
}


