package application.nis.com.placementcell;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class StudentInformation extends AppCompatActivity {
     private  TextView name,branch,email,_12th,be,_10th;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_information);
        name=(TextView)findViewById(R.id.Namev);
        branch=(TextView)findViewById(R.id.Branchv);
        email=(TextView)findViewById(R.id.emailv);
        _12th=(TextView)findViewById(R.id._12thmarksv);
        _10th=(TextView)findViewById(R.id._10thmarksv);
        be=(TextView)findViewById(R.id.Cgpav);
        Bundle bundle=getIntent().getExtras();
        String rollno=bundle.getString("ROLLNO");
        final Context ctx=this;
        String url="http://"+ctx.getString(R.string.ip)+ctx.getString(R.string.port)+"/Placement_Cell/StudentDisplayByRollno?rollno="+rollno;
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
           try {
                JSONObject obj=(JSONObject) response.get(0);
                name.setText(obj.getString("NAME"));
                branch.setText(obj.getString("BRANCH"));
                email.setText(obj.getString("EMAIL"));
                be.setText(obj.getString("CGPA"));
                _10th.setText(obj.getString("METRIC_MARKS"));
                _12th.setText(obj.getString("HS_MARKS"));
           }catch (Exception e) {
               System.out.println(e);
           }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
}
