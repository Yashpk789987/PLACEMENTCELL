package application.nis.com.placementcell;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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



public class RecentVisitFragement extends Fragment {

    private ListView vlist;

    public RecentVisitFragement() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recentvisit,container, false);
        final Context ctx=getContext();
        vlist=(ListView)view.findViewById(R.id.vlist);
        vlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getContext(),VisitInformation.class);
                intent.putExtra("VISITID",vlist.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });
        String url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/DisplayAllUpdatedVisits";
        RequestQueue queue= Volley.newRequestQueue(getContext());
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> al=new ArrayList<String>();
                ArrayList<String> il=new ArrayList<String>();
                for(int i=0;i<response.length();i++) {
                    try{
                        SharedPreferences SP=getContext().getSharedPreferences("stsession",0);
                        JSONObject obj=(JSONObject) response.get(i);
                        if((Float.parseFloat(SP.getString("HS_MARKS",""))>=(float)(obj.getDouble("REQUIRED_HSC_MARKS")))&&(Float.parseFloat(SP.getString("METRIC_MARKS",""))>=(float)(obj.getDouble("REQUIRED_METRIC_MARKS")))&&(Float.parseFloat(SP.getString("CGPA",""))>=(float)(obj.getDouble("REQUIRED_CGPA")))) {
                            al.add(obj.getString("NAME").toString());
                            il.add(obj.getString("VISITID").toString());
                        }
                    }catch (Exception e)
                    {}
                }
                String alist[]=al.toArray(new String[al.size()]);
                String ilist[]=il.toArray(new String[il.size()]);
                vlist.setAdapter(new CustomAdapter2(getContext(),alist,ilist));
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
        return view;
    }
}
