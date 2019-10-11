package application.nis.com.placementcell;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;



public class ProfileFragement extends Fragment {

    public ProfileFragement() {
        // Required empty public constructor
    }

    private TextView name;
    private ListView listView;
    private Button modify,logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fragement, container, false);
        name = (TextView) view.findViewById(R.id.stname);
        listView = (ListView) view.findViewById(R.id.stlistview);
        modify = (Button) view.findViewById(R.id.stedit);
        logout = (Button) view.findViewById(R.id.stlogout);
        SharedPreferences SP = this.getActivity().getSharedPreferences("stsession", 0);


        modify.setOnClickListener(new btn_click(getContext()));
        logout.setOnClickListener(new btn_click(getContext()));

        String stname = SP.getString("NAME", "").toUpperCase();
        name.setText(stname);
        String maintitle[] = {"Name", "RollNo.", "Branch", "Email", "Btech Cgpa", "12th Per.", "10th Per."};
        String subtitle[] = {SP.getString("NAME", ""), SP.getString("ROLLNO", ""), SP.getString("BRANCH", ""), SP.getString("EMAIL", ""), SP.getString("CGPA", ""), SP.getString("HS_MARKS", ""), SP.getString("METRIC_MARKS", "")};
        CustomAdapter1 C1 = new CustomAdapter1(getContext(), maintitle, subtitle, "ForCompanyUpdate");
        listView.setAdapter(C1);
        return view;
    }

    class btn_click implements View.OnClickListener {
        Context ctx;

        public btn_click(Context ctx) {
            this.ctx = ctx;
        }

        public void onClick(View view) {
            if (view == modify) {
                Intent intent=new Intent(ctx,StudentUpdate.class);
                startActivity(intent);
            }else if (view == logout) {
                AlertDialog.Builder ad=new AlertDialog.Builder(ctx);
                ad.setTitle("Logout Confirmation");
                ad.setMessage("Are You Sure To log out");
                ad.setCancelable(true);
                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences SP=ctx.getSharedPreferences("stsession",0);
                        SharedPreferences.Editor editor= SP.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent=new Intent(ctx,StudentLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });

                ad.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }) ;
                ad.show();
            }
        }


    }
}
