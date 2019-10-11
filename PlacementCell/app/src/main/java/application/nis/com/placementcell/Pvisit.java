package application.nis.com.placementcell;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Pvisit extends AppCompatActivity {
    private ListView prvlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvisit);
        prvlist=(ListView)findViewById(R.id.prvlist);
        prvlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(getApplicationContext(),EStudents.class);
                intent.putExtra("VID",prvlist.getItemAtPosition(i).toString());
                intent.putExtra("pvintent","Pvisit");
                startActivity(intent);
            }
        });
        final Context ctx=this;
        final ArrayList<String> al=new ArrayList<>();
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/getPreviousVisit";
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> al=new ArrayList<String>();
                ArrayList<String> il=new ArrayList<String>();
                //////////////// DATA FORMAT  ////////////////////
                for(int i=0;i<response.length();i++) {
                    try {
                        JSONObject obj=(JSONObject) response.get(i);
                        al.add(obj.getString("NAME").toString());
                        il.add(obj.getString("VISITID").toString());
                        System.out.println(obj.getString("VISITID").toString());
                    }catch (Exception e)
                    {}
                }
                ///////////// DATA FORMAT ////////////////////////
                String alist[]=al.toArray(new String[al.size()]);
                String ilist[]=il.toArray(new String[il.size()]);
                prvlist.setAdapter(new CustomAdapter2(getApplicationContext(),alist,ilist));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
}
