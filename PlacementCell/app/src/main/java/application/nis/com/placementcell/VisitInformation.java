package application.nis.com.placementcell;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class VisitInformation extends AppCompatActivity {
    private ListView clistview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_information);
        clistview=(ListView)findViewById(R.id.clistview);
        Bundle bundle=getIntent().getExtras();
        String vid=bundle.getString("VISITID","");
        final Context ctx=this;
        String url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/DisplayUpdatedVisitById?vid="+vid;

        RequestQueue queue= Volley.newRequestQueue(this);
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try{
                    JSONObject object=(JSONObject) response.get(0);
                    System.out.println(object.toString());
                    String maintitle[] = {"Visit Name","Company ","For Branches","Info","Visit Date","Visit Place"};
                    String subtitle[]={object.getString("NAME"),object.getString("COMPANYNAME"),object.getString("BRANCH"),object.getString("INFO"),object.getString("DATE"),object.getString("VISITPLACE")};

                    CustomAdapter1 C1 = new CustomAdapter1(ctx, maintitle, subtitle, "forcompanyinfo");
                    clistview.setAdapter(C1);


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
}
