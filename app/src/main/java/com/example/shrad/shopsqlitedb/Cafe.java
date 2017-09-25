package com.example.shrad.shopsqlitedb;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by shrad on 22/09/2017.
 */

public class Cafe extends Fragment {

    private TextView name;
    private TextView address;
    private TextView phonenumber;
    private TextView netlink;
    private String n[]= new String[100];
    private String a[]= new String[100];
    private String p[]= new String[100];
    private String l[]= new String[100];
    private int count;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(int i=0;i<99;i++)
        {
            n[i]="name"+i;
            a[i]="address"+i;
            p[i]="phonenumber"+i;
            l[i]="Netlink"+i;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_search_cafe, container, false);
        name = rootView.findViewById(R.id.name1);
        name.setText("Name: "+n[0+count]);
        address = rootView.findViewById(R.id.address1);
        address.setText("Address: "+a[0+count]);
        phonenumber = rootView.findViewById(R.id.phone1);
        phonenumber.setText("Phonenumber: "+p[0+count]);
        netlink = rootView.findViewById(R.id.netlink1);
        netlink.setText("NetLink: "+l[0+count]);

        name = rootView.findViewById(R.id.name2);
        name.setText("Name: "+n[1+count]);
        address = rootView.findViewById(R.id.address2);
        address.setText("Address: "+a[1+count]);
        phonenumber = rootView.findViewById(R.id.phone2);
        phonenumber.setText("Phonenumber: "+p[1+count]);
        netlink = rootView.findViewById(R.id.netlink2);
        netlink.setText("NetLink: "+l[1+count]);

        name = rootView.findViewById(R.id.name3);
        name.setText("Name: "+n[2+count]);
        address = rootView.findViewById(R.id.address3);
        address.setText("Address: "+a[2+count]);
        phonenumber = rootView.findViewById(R.id.phone3);
        phonenumber.setText("Phonenumber: "+p[2+count]);
        netlink = rootView.findViewById(R.id.netlink3);
        netlink.setText("NetLink: "+l[2+count]);

        name = rootView.findViewById(R.id.name4);
        name.setText("Name: "+n[3+count]);
        address = rootView.findViewById(R.id.address4);
        address.setText("Address: "+a[3+count]);
        phonenumber = rootView.findViewById(R.id.phone4);
        phonenumber.setText("Phonenumber: "+p[3+count]);
        netlink = rootView.findViewById(R.id.netlink4);
        netlink.setText("NetLink: "+l[3+count]);

        rootView.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
            }
        });
        rootView.findViewById(R.id.choose1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SearchTwo.class);
                i.putExtra("information", new Information(n[0+count],a[0+count],p[0+count]));
                getActivity().startActivity(i);
            }
        });
        rootView.findViewById(R.id.choose2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SearchTwo.class);
                i.putExtra("information", new Information(n[1+count],a[1+count],p[1+count]));
                getActivity().startActivity(i);
            }
        });
        rootView.findViewById(R.id.choose3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SearchTwo.class);
                i.putExtra("information", new Information(n[2+count],a[2+count],p[2+count]));
                getActivity().startActivity(i);
            }
        });
        rootView.findViewById(R.id.choose4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SearchTwo.class);
                i.putExtra("information", new Information(n[3+count],a[3+count],p[3+count]));
                getActivity().startActivity(i);
            }
        });

        rootView.findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count>3){
                    count=count-4;
                    getFragmentManager().beginTransaction().attach(new Cafe()).commit();
                }
            }
        });

        rootView.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=count+4;
                getFragmentManager().beginTransaction().attach(new Cafe()).commit();
            }
        });

        return rootView;
    }

}
