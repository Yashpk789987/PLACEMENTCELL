package application.nis.com.placementcell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CustomAdapter1 extends ArrayAdapter<String> {
    public Context ctx;
    public String maintitle[];
    public String subtitle[];
    public String forwhat;
    public CustomAdapter1(Context ctx,String maintitle[],String subtitle[],String forwhat) {
      super(ctx,R.layout.forcompanylist,maintitle);
      this.ctx=ctx;
      this.maintitle=maintitle;
      this.subtitle=subtitle;
      this.forwhat=forwhat;
    }

    public View getView(int pos, View view, ViewGroup parent) {
        int layout=0;
        if(forwhat.equalsIgnoreCase("ForCompanyUpdate")) {
            layout=R.layout.forcompanylist;
        }
        else if(forwhat.equalsIgnoreCase("ForVisitEdit")) {
            layout=R.layout.visitinfolist;
        }

        else if(forwhat.equalsIgnoreCase("forcompanyinfo")) {
            layout=R.layout.companyinformation;
        }

        LayoutInflater inflater=(LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view1=inflater.inflate(layout,null,true);
        TextView titleText=(TextView)view1.findViewById(R.id.title);
        TextView subtitleText=(TextView)view1.findViewById(R.id.subtitle);
        System.out.println(maintitle[pos]+"   ------   "+subtitle[pos]);
        titleText.setText(maintitle[pos]);
        subtitleText.setText(subtitle[pos]);
        return view1;
    }

}
