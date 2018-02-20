package com.example.edisonoffice.bargraph;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public  class CustomAdapter extends BaseAdapter {

    Context context;
    public static LayoutInflater inflater;
    static Holder holder;


   // String type1;
  static Object date_time,devicename,switchnumberr,devicetype,modetype;
    ArrayList datetim,device_name,switch_number,device_type,modetye;
      ArrayList dats_li;
    static String roomnumber;
    private String sunday,monday,tuesday,wednes,Thursday,Friday,Saturday;
    List days_array = new ArrayList();
    public CustomAdapter(MainActivity mainActivity, ArrayList datetim, ArrayList device, ArrayList number, ArrayList devtype, ArrayList modtype) {
        context=  mainActivity;



        this.datetim=datetim;
        this.device_name=device;
        this.switch_number=number;
        this.device_type=devtype;
        this.modetye=modtype;

       // devicename=devicename;
        //inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflater = LayoutInflater.from(context);
         inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }




    @Override
    public int getCount() {

        return datetim.size();

    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }




    public static class Holder
    {
        TextView time;
        TextView devicename,switchh,device_typ,mode_typ;
        ImageButton edit,delete;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
         holder=new Holder();
        final View rowView;

        rowView = inflater.inflate(R.layout.recy_item, null);
       holder.time=(TextView) rowView.findViewById(R.id.date_time);
        holder.devicename=(TextView) rowView.findViewById(R.id.dev_name);
        holder.switchh=(TextView) rowView.findViewById(R.id.swt_num);
        holder.device_typ=(TextView) rowView.findViewById(R.id.dev_type);
        holder.mode_typ=(TextView) rowView.findViewById(R.id.mode_type);

        holder.time.setText((CharSequence) datetim.get(position));
        holder.devicename.setText((CharSequence) device_name.get(position));
        holder.switchh.setText((CharSequence) switch_number.get(position));
     // holder.type.setText((CharSequence) days.get(position));
        holder.device_typ.setText((CharSequence) device_type.get(position));
        holder.mode_typ.setText((CharSequence) modetye.get(position));



        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, "You Clicked "+ result.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}