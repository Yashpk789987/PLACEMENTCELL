package application.nis.com.placementcell;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;



public class HttpGetCall {
    public Context ctx;
    public String callname;

    public HttpGetCall(Context ctx,String callname) {
      this.callname=callname;
      this.ctx=ctx;
    }

    public boolean call(String id){
        if(callname.equalsIgnoreCase("DisplayVisitById")) {
            RequestQueue queue= Volley.newRequestQueue(ctx);
            String url="http://"+R.string.ip+R.string.port+"/Placement_Cell/DisplayVisitById?vid="+id;
            JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {   SharedPreferences SVP=ctx.getSharedPreferences("visitinfo",0);
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
                            editor.commit(); }catch (Exception e)
                    {}
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(request);
        }
        return true;
    }
}
