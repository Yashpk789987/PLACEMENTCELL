package application.nis.com.placementcell;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class UV extends AppCompatActivity {
    private EditText date;
    private AutoCompleteTextView info;
    DatePickerDialog datePickerDialog;
    private Button visitsubmit,done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv);
        done=(Button)findViewById(R.id.done);
        date=(EditText)findViewById(R.id.visitdate);
        info=(AutoCompleteTextView)findViewById(R.id.info);
        visitsubmit=(Button)findViewById(R.id.visitsubmit);
        Bundle bundle=getIntent().getExtras();
        final String vid=bundle.getString("VID");
        final Context ctx=this;
        String url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/DisplayUpdatedVisitById?vid="+vid;
        RequestQueue queue= Volley.newRequestQueue(ctx);
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
          //   Toast.makeText(ctx,response.toString(),Toast.LENGTH_LONG).show();
             if(response.toString().equalsIgnoreCase("[]")) {
                 done.setEnabled(false);done.setVisibility(View.GONE);
                 date.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         final Calendar c = Calendar.getInstance();
                         int mYear = c.get(Calendar.YEAR); // current year
                         int mMonth = c.get(Calendar.MONTH); // current month
                         int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                         datePickerDialog = new DatePickerDialog(ctx,
                                 new DatePickerDialog.OnDateSetListener() {
                                     @Override
                                     public void onDateSet(DatePicker view, int year,
                                                           int monthOfYear, int dayOfMonth) {
                                         date.setText(dayOfMonth + "/"
                                                 + (monthOfYear + 1) + "/" + year);
                                     }
                                 }, mYear, mMonth, mDay);
                         datePickerDialog.show();
                     }
                 });

                 visitsubmit.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         info=(AutoCompleteTextView)findViewById(R.id.info);
                         HashMap<String,String>  obj=new HashMap<String, String>();
                         obj.put("visitid",vid);
                         obj.put("visitdate",date.getText().toString());
                         obj.put("visitinfo",info.getText().toString());
                         Intent intent=new Intent(ctx,CompanyHome.class);
                         CallHttp C=new CallHttp(ctx,"LETSVISIT",obj,intent);
                     }
                 });
             }
             else
             { try{ visitsubmit.setText("Modify");
                final JSONObject obj=(JSONObject) response.get((0));
                String str[]=obj.getString("DATE").split("/");
                 int mYear = Integer.parseInt(str[2]); // current year
                 int mMonth = Integer.parseInt(str[1]); // current month
                 int mDay = Integer.parseInt(str[0]);
                 datePickerDialog = new DatePickerDialog(ctx,
                         new DatePickerDialog.OnDateSetListener() {

                             @Override
                             public void onDateSet(DatePicker view, int year,
                                                   int monthOfYear, int dayOfMonth) {
                             try{    // set day of month , month and year value in the edit text
                                 date.setText(obj.getString("DATE"));  }catch(Exception e)
                             {}

                             }
                         }, mYear, mMonth, mDay);
                 datePickerDialog.show();

                info.setText(obj.getString("INFO"));
                 visitsubmit.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         info=(AutoCompleteTextView)findViewById(R.id.info);
                         HashMap<String,String>  obj=new HashMap<String, String>();
                         obj.put("visitid",vid);
                         obj.put("visitdate",date.getText().toString());
                         obj.put("visitinfo",info.getText().toString());
                         Intent intent=new Intent(ctx,CompanyHome.class);
                         CallHttp C=new CallHttp(ctx,"LETSMODIFYVISIT",obj,intent);
                     }
                 });

                 done.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         AlertDialog.Builder ad=new AlertDialog.Builder(ctx);
                         ad.setTitle("Confirmation");
                         ad.setMessage("Are You Sure To Close This Visit ");
                         ad.setCancelable(true);
                         ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 HashMap<String,String>  obj=new HashMap<String, String>();
                                 obj.put("visitid",vid);
                                 Intent intent=new Intent(ctx,CompanyHome.class);
                                 CallHttp C=new CallHttp(ctx,"Completed",obj,intent);
                             }
                         });
                         ad.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {

                             }
                         }) ;
                         ad.show();


                     }
                 });


             }catch (Exception e)
             {}

             }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }


        );
        queue.add(request);


          visitsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info=(AutoCompleteTextView)findViewById(R.id.info);
                HashMap<String,String>  obj=new HashMap<String, String>();
                obj.put("visitid",vid);
                obj.put("visitdate",date.getText().toString());
                obj.put("visitinfo",info.getText().toString());
                Intent intent=new Intent(ctx,CompanyHome.class);
                CallHttp C=new CallHttp(ctx,"LETSVISIT",obj,intent);
            }
        });

    }
}
