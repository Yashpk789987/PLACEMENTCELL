package application.nis.com.placementcell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


public class CustomAdapter2 extends ArrayAdapter<String> {
    public Context ctx;
    public String VName[];
    public String Vid[];

    public CustomAdapter2(Context ctx,String VName[],String Vid[]) {
        super(ctx,R.layout.listview2,Vid);
        this.ctx=ctx;
        this.VName=VName;
        this.Vid=Vid;
    }

    public View getView(int pos, View view, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view1=inflater.inflate(R.layout.listview2,null,true);
        TextView vname=(TextView)view1.findViewById(R.id.vname);
        vname.setText(VName[pos]);
        return view1;
    }

}
