package application.nis.com.placementcell;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class CallHttp {
    public Context ctx;
    public String process;
    public HashMap<String,String> obj;
    public Intent targetActivity;

    public CallHttp(Context ctx, String process, HashMap<String,String> obj,Intent targetActivity) {
        this.ctx=ctx;
        this.process=process;
        this.obj=obj;
        this.targetActivity=targetActivity;
        execute();
    }

    public void execute() {
        String url="";
        if(process.equalsIgnoreCase("CompanyRegistration")) {
            url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/CompanyRegistration";
        }

        else if(process.equalsIgnoreCase("Student_Registration")){
            url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/StudentRegistration";
        }

        else if(process.equalsIgnoreCase("CompanyUpdate")) {
            url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/UpdateCompany";
        }
        else if(process.equalsIgnoreCase("AddNewVisit")) {
            url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/AddNewVisit";
        }
        else if(process.equalsIgnoreCase("VISITMODIFY")) {
            url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/ModifyVisit";
        }
        else if(process.equalsIgnoreCase("DELETEVISIT")) {
            url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/DeleteVisitById";
        }
        else if(process.equalsIgnoreCase("LETSVISIT")) {
            url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/UpdateVisits";
        }
        else if(process.equalsIgnoreCase("LETSMODIFYVISIT")) {
            url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/ModifyUpdatedVisit";
        }
        else if(process.equalsIgnoreCase("Completed")) {
            url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/VisitComplete";
        }
        else if(process.equalsIgnoreCase("StudentUpdate")) {
            url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/StudentUpdate";
        }


        ProgressDialog progress2=new ProgressDialog(ctx);
        progress2.setMessage("Please Wait....");
        progress2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress2.setIndeterminate(true);
        progress2.setProgress(0);
        progress2.show();
        RequestQueue queue=Volley.newRequestQueue(ctx);
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response_Listener(ctx, process, progress2,targetActivity),new Response_Error(ctx,progress2)){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return obj;
            }
            };
            queue.add(request);
        }
    }


class Response_Listener implements com.android.volley.Response.Listener{
    Context ctx;
    String process;
    ProgressDialog m;
    Intent target;

    public Response_Listener(Context ctx, String process,ProgressDialog m,Intent target){
        this.ctx=ctx;
        this.process=process;
        this.m=m;
        this.target=target;
    }

    @Override
    public void onResponse(Object response) {
       m.dismiss();
       ctx.startActivity(target);
       Toast.makeText(ctx,response.toString(),Toast.LENGTH_LONG).show();
    }
}

class Response_Error implements com.android.volley.Response.ErrorListener{
    Context ctx;
    ProgressDialog m;

    public Response_Error(Context ctx,ProgressDialog m){
        this.ctx=ctx;
        this.m=m;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        m.dismiss();
        Toast.makeText(ctx,"No Internet Connection ",Toast.LENGTH_LONG).show();
    }
}

