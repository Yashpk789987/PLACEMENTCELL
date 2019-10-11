package application.nis.com.placementcell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;

public class StudentUpdate extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {
    private AutoCompleteTextView name,email,studentid,cgpa,rollno,Hs_marks,metric_marks,password;
    private Button stupdate;
    private Spinner branch;
    private String bvalue;
    private String bvaluestored;
    private int pos;
    String[] branches={"Select Your  Branch","CSE","IT","ECE","EE","ME","CE"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_update);
        SharedPreferences SP=getSharedPreferences("stsession",0);
        bvaluestored=SP.getString("BRANCH","");
        for(int j=0;j<branches.length;j++)
        { if(branches[j].equalsIgnoreCase(bvaluestored))
          {   pos=j;
              break;
          }
        }
        name=(AutoCompleteTextView)findViewById(R.id.Name);

        email=(AutoCompleteTextView)findViewById(R.id.Email);
        cgpa=(AutoCompleteTextView)findViewById(R.id.CGPA);
        branch=(Spinner)findViewById(R.id.Branch);
        rollno=(AutoCompleteTextView)findViewById(R.id.Rollno);
        Hs_marks=(AutoCompleteTextView)findViewById(R.id.HS_MARKS);
        metric_marks=(AutoCompleteTextView)findViewById(R.id.METRIC_MARKS);
        password=(AutoCompleteTextView)findViewById(R.id.password);
        name.setText(SP.getString("NAME",""));
        email.setText(SP.getString("EMAIL",""));
        cgpa.setText(SP.getString("CGPA",""));

        rollno.setText(SP.getString("ROLLNO",""));
        Hs_marks.setText(SP.getString("HS_MARKS",""));
        metric_marks.setText(SP.getString("METRIC_MARKS",""));
        password.setText(SP.getString("PASSWORD",""));
        stupdate=(Button)findViewById(R.id.stupdate);
        stupdate.setOnClickListener(new btn_click(this));
        branch.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,branches);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        branch.setSelection(pos);
        branch.setAdapter(aa);
        branch.setSelection(pos);
    }

    class btn_click implements View.OnClickListener {
        Context ctx;

        public btn_click(Context c) {
            ctx = c;
        }

        @Override
        public void onClick(View view) {
                SharedPreferences SP=getSharedPreferences("stsession",0);
                SharedPreferences.Editor editor=SP.edit();
                editor.putString("NAME",name.getText().toString());
                editor.putString("EMAIL",email.getText().toString());
                editor.putString("CGPA",cgpa.getText().toString());
                editor.putString("BRANCH",bvalue);
                editor.putString("ROLLNO",rollno.getText().toString());
                editor.putString("HS_MARKS",Hs_marks.getText().toString());
                editor.putString("METRIC_MARKS",metric_marks.getText().toString());
                editor.putString("PASSWORD",password.getText().toString());
                editor.commit();
                HashMap<String,String> obj=new HashMap<String, String>();
                obj.put("sid",SP.getString("STUDENTID",""));
                obj.put("sname",name.getText().toString());
                obj.put("semail",email.getText().toString());
                obj.put("scgpa",cgpa.getText().toString());
                obj.put("sbranch",bvalue);
                obj.put("srollno",rollno.getText().toString());
                obj.put("spwd",password.getText().toString());
                obj.put("shs_marks",Hs_marks.getText().toString());
                obj.put("smetric_marks",metric_marks.getText().toString());
                Intent intent=new Intent(ctx,StudentHome.class);
                CallHttp ct=new  CallHttp(ctx,"StudentUpdate",obj,intent);
        }
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        bvalue=branches[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
     // TODO Auto-generated method stub

    }
}
