package application.nis.com.placementcell;

import android.app.TabActivity;
import android.content.Intent;

import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;



public class UpdateVisitHomeTab extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_visit_home_tab);
        /////////////////////////  Tab Host Starts    ////////////////////////////
        TabHost updatevisithometab=(TabHost)findViewById(android.R.id.tabhost);
        updatevisithometab.getTabWidget().setDividerDrawable(null);
        Bundle bundle=getIntent().getExtras();
        String vid=bundle.getString("VID");
        TabHost.TabSpec spec;
        Intent intent;

        spec=updatevisithometab.newTabSpec("visitinfo");
        spec.setIndicator("",getResources().getDrawable(R.drawable.information));
        intent=new Intent(this,VInfo.class);
        intent.putExtra("VID",vid);
        spec.setContent(intent);
        updatevisithometab.addTab(spec);

        spec=updatevisithometab.newTabSpec("estudent");
        spec.setIndicator("",getResources().getDrawable(R.drawable.student));
        intent=new Intent(this,EStudents.class);
        intent.putExtra("VID",vid);
        intent.putExtra("pvintent","Cvisit");
        spec.setContent(intent);
        updatevisithometab.addTab(spec);

        spec=updatevisithometab.newTabSpec("updatevisit");
        spec.setIndicator("",getResources().getDrawable(R.drawable.loop));
        intent=new Intent(this,UV.class);
        intent.putExtra("VID",vid);
        spec.setContent(intent);
        updatevisithometab.addTab(spec);

        updatevisithometab.setCurrentTab(0);

        updatevisithometab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        });
        ////////////////////////////////////  Tab Host Ends    //////////////////////////
    }
}

