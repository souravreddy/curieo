package com.example.myapplication;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.Fragment; import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AboutFragment extends Fragment {

    private static String mFileName = null;
    private MediaPlayer mPlayer;
    private Button b,b1,b2;
    List<File> spinnerArray =  new ArrayList<File>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){


        return inflater.inflate(R.layout.aboutlayout, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {


        System.out.println();
       //b=(Button) view.findViewById(R.id.button);
        b1=(Button) view.findViewById(R.id.button3);
        b2=(Button) view.findViewById(R.id.button4);
        final Spinner sItems = (Spinner) view.findViewById(R.id.spinner);
        b.setVisibility(view.INVISIBLE);
        sItems.setVisibility(view.INVISIBLE);
        b2.setVisibility(view.INVISIBLE);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setVisibility(v.VISIBLE);
                sItems.setVisibility(v.VISIBLE);
                b2.setVisibility(v.VISIBLE);
                b1.setVisibility(v.INVISIBLE);
                mFileName = Environment.getExternalStorageDirectory().getAbsolutePath()+"/trying";
                Log.d("Files", "Path: " + mFileName);
                File directory = new File(mFileName);
                File[] files = directory.listFiles();
                if(files!=null) {
                    Log.d("Files", "Size: " + files.length);
                    for (int i = 0; i < files.length; i++) {
                        spinnerArray.add(files[i]);
                        System.out.println(files[i]);
                        Log.d("Files", "FileName:" + files[i].getName());
                    }
                }

                ArrayAdapter<File> adapter = new ArrayAdapter<File>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                sItems.setAdapter(adapter);

            }
        });
        System.out.println("dfg");
        Toast.makeText(getActivity().getApplicationContext(),"dssfd",Toast.LENGTH_SHORT);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.release();
                mPlayer = null;
            }
        });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = sItems.getSelectedItem().toString();
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(text);
                    mPlayer.prepare();
                    mPlayer.start();
                    Toast.makeText(getActivity().getApplicationContext(), "Recording Started Playing", Toast.LENGTH_LONG).show();
                } catch (IOException e) {

                }

            }
        });
    }
}