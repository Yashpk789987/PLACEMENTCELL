package application.nis.com.placementcell;


import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;


public class CompanyHome extends TabActivity {
    private TabHost companytab;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_company_home);
        SharedPreferences SP=getSharedPreferences("csession",0);
        System.out.println(SP.contains("PASSWORD"));
        if(!(SP.contains("PASSWORD")))
        {   Intent intent=new Intent(this,CompanyLoginActivity.class);
            startActivity(intent);
        }
        fab=(FloatingActionButton)findViewById(R.id.fab);
        companytab=(TabHost)findViewById(android.R.id.tabhost);
        companytab.getTabWidget().setDividerDrawable(null);
        TabHost.TabSpec spec;
        Intent intent;
        spec=companytab.newTabSpec("pvisit");
        spec.setIndicator("",getResources().getDrawable(R.drawable.prevous));
        intent=new Intent(this,Pvisit.class);
        spec.setContent(intent);
        companytab.addTab(spec);

        spec=companytab.newTabSpec("cvisit");
        spec.setIndicator("",getResources().getDrawable(R.drawable.currentvisitssss));
        intent=new Intent(this,Cvisit.class);

        spec.setContent(intent);
        companytab.addTab(spec);

        spec=companytab.newTabSpec("cprofile");
        spec.setIndicator("",getResources().getDrawable(R.drawable.cprofile222));
        intent=new Intent(this,CProfile.class);
        spec.setContent(intent);
        companytab.addTab(spec);

        companytab.setCurrentTab(1);

        companytab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {

            }
        });


    }
}

