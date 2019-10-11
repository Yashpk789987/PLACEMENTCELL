import android.app.DownloadManager;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SONY on 2/27/2018.
 */

public class CallHttp {
    private Context ctx;
    private String process;
    private HashMap<String,String> obj;
    public CallHttp(Context ctx, String process, HashMap<String,String> obj)
    {  this.ctx=ctx;
       this.process=process;
       this.obj=obj;
       execute();
    }
    public void execute()
    {
        if(process.equalsIgnoreCase("Student_Registration"))
        {   RequestQueue queue=Volley.newRequestQueue(ctx);
            String url="http://192.168.43.222:2128/Placement_Cell/StudentRegistration";
            StringRequest request=new StringRequest(Request.Method.POST,url,new ResponseListener(ctx),new ResponseError(ctx)){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    return obj;
                }
            };
            queue.add(request);}




        }
    }
class ResponseListener implements com.android.volley.Response.Listener{
    Context ctx;
    public ResponseListener(Context ctx){
        this.ctx=ctx;
    }
    @Override
    public void onResponse(Object response) {
        Toast.makeText(ctx,response.toString(),Toast.LENGTH_LONG).show();
        System.out.println("MyResponse"+response);
    }
}
class ResponseError implements com.android.volley.Response.ErrorListener{
    Context ctx;
    public ResponseError(Context ctx){
        this.ctx=ctx;
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("Hello3");
        Toast.makeText(ctx,error.toString(),Toast.LENGTH_LONG).show();
    }
}

