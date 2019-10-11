package application.nis.com.placementcell;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static application.nis.com.placementcell.R.id.label;


public class RecruiterFragement extends Fragment {
    private ListView companylist;

    public RecruiterFragement() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.recruiter, container, false);
        final Context ctx=getContext();
        String url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/DisplayAllCompanies";
        RequestQueue queue= Volley.newRequestQueue(getContext());
        companylist=(ListView)view.findViewById(R.id.companylist);

        companylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getContext(),CompanyInformation.class);
                intent.putExtra("COMPANYID",companylist.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> al=new ArrayList<String>();
                ArrayList<String> il=new ArrayList<String>();
                for(int i=0;i<response.length();i++)
                {   System.out.println("Cvisit2");
                    try{

                        JSONObject obj=(JSONObject) response.get(i);
                        al.add(obj.getString("NAME").toString());
                        il.add(obj.getString("COMPANYID").toString());

                    }catch (Exception e)
                    {}
                }
                String alist[]=al.toArray(new String[al.size()]);
                String ilist[]=il.toArray(new String[il.size()]);
                companylist.setAdapter(new CustomAdapter2(getContext(),alist,ilist));
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
        return view;
    }
}
