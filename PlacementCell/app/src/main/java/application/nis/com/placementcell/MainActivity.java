package application.nis.com.placementcell;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button student,company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        student=(Button)findViewById(R.id.student);
        company=(Button)findViewById(R.id.company);
        student.setOnClickListener(new btn_click(this));
        company.setOnClickListener(new btn_click(this));
    }

    class btn_click implements View.OnClickListener{
        public Context ctx;

        public btn_click(Context ctx) {
            this.ctx=ctx;
        }

        public void onClick(View view) {
            if(view == student){
//                Intent intent=new Intent(ctx,StudentLoginActivity.class);
                Intent intent = new Intent(ctx, StudentHome.class);
                startActivity(intent);
            }
            else {
                Intent intent=new Intent(ctx,CompanyLoginActivity.class);
                startActivity(intent);
            }
        }
    }
}
