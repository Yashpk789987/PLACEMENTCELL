package application.nis.com.placementcell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.HashMap;

public class VisitModify extends AppCompatActivity {
    private AutoCompleteTextView name,branches,becgpa,twelthper,tenthper,place;
    private Button vupdatebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_modify);
        SharedPreferences SVP=getSharedPreferences("visitinfo",0);
        String visitid=SVP.getString("VISITID","");
        name=(AutoCompleteTextView)findViewById(R.id.VisitName);
        branches=(AutoCompleteTextView)findViewById(R.id.branches);
        becgpa=(AutoCompleteTextView)findViewById(R.id.becgpa);
        twelthper=(AutoCompleteTextView)findViewById(R.id.twelth);
        tenthper=(AutoCompleteTextView)findViewById(R.id.tenth);
        place=(AutoCompleteTextView)findViewById(R.id.vplace);
        vupdatebtn=(Button)findViewById(R.id.vupdatebtn);
        name.setText(SVP.getString("NAME",""));
        branches.setText(SVP.getString("BRANCH",""));
        becgpa.setText(SVP.getString("REQUIRED_CGPA",""));
        twelthper.setText(SVP.getString("REQUIRED_HSC_MARKS",""));
        tenthper.setText(SVP.getString("REQUIRED_METRIC_MARKS",""));
        place.setText(SVP.getString("VISITPLACE",""));
        vupdatebtn.setOnClickListener(new btn_click(this));
    }

    class btn_click implements View.OnClickListener {
        public Context ctx;
        public btn_click(Context ctx) {
            this.ctx=ctx;
        }

        public void onClick(View view) {
            SharedPreferences SVP=getSharedPreferences("visitinfo",0);
            String visitid=SVP.getString("VISITID","");
            SharedPreferences.Editor editor=SVP.edit();
            editor.putString("NAME",name.getText().toString());
            editor.putString("BRANCH",branches.getText().toString());
            editor.putString("REQUIRED_CGPA",becgpa.getText().toString());
            editor.putString("REQUIRED_HSC_MARKS",twelthper.getText().toString());
            editor.putString("REQUIRED_METRIC_MARKS",tenthper.getText().toString());
            editor.putString("VISITPLACE",place.getText().toString());
            editor.commit();
            HashMap<String,String>  obj=new HashMap<String, String>();
            obj.put("visitid",visitid);
            obj.put("vbranch",branches.getText().toString());
            obj.put("vname",name.getText().toString());
            obj.put("vrcgpa",becgpa.getText().toString());
            obj.put("vrhsc",twelthper.getText().toString());
            obj.put("vrmetric",tenthper.getText().toString());
            obj.put("vplace",place.getText().toString());
            Intent intent=new Intent(ctx,VInfo.class);
            intent.putExtra("VID",visitid);
            CallHttp C=new CallHttp(ctx,"VISITMODIFY",obj,intent);
        }
    }
}
