package com.example.myapplication;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeFragment extends Fragment {
    private Button startbtn, stopbtn, playbtn, stopplay;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null,mFileName1=null;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        return inflater.inflate(R.layout.homelayou, viewGroup, false);

    }
    public void onViewCreated(View view, Bundle savedInstanceState) {

        System.out.print("hkhf");
        startbtn = (Button) view.findViewById(R.id.btnRecord);
        stopbtn = (Button) view.findViewById(R.id.btnStop);
        playbtn = (Button) view.findViewById(R.id.btnPlay);
        stopplay = (Button) view.findViewById(R.id.btnStopPlay);
        stopbtn.setEnabled(false);
        playbtn.setEnabled(false);
        stopplay.setEnabled(false);
        mFileName1 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/trying";
        mFileName += "/AudioRecording.3gp";
        System.out.println(mFileName);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("hkjghf");
                if (CheckPermissions()) {
                    stopbtn.setEnabled(true);
                    startbtn.setEnabled(false);
                    playbtn.setEnabled(false);
                    stopplay.setEnabled(false);
                    mRecorder = new MediaRecorder();
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mFileName =mFileName1+ "/AudioRecording"+System.nanoTime()+".3gp";
                    mRecorder.setOutputFile(mFileName);
                    try {
                        mRecorder.prepare();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "prepare() failed");
                    }
                    mRecorder.start();
                    Toast.makeText(getActivity().getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();
                } else {
                    RequestPermissions();
                }
            }
        });
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopbtn.setEnabled(false);
                startbtn.setEnabled(true);
                playbtn.setEnabled(true);
                stopplay.setEnabled(true);
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                Toast.makeText(getActivity().getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();
            }
        });
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopbtn.setEnabled(false);
                startbtn.setEnabled(true);
                playbtn.setEnabled(false);
                stopplay.setEnabled(true);
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(mFileName);
                    mPlayer.prepare();
                    mPlayer.start();
                    Toast.makeText(getActivity().getApplicationContext(), "Recording Started Playing", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "prepare() failed");
                }
                Log.d("Files", "Path: " + mFileName1);
                File directory = new File(mFileName1);
                File[] files = directory.listFiles();
                Log.d("Files", "Size: "+ files.length);
                for (int i = 0; i < files.length; i++)
                {
                    System.out.println(files[i]);
                    Log.d("Files", "FileName:" + files[i].getName());
                }
            }
        });
        stopplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.release();
                mPlayer = null;
                stopbtn.setEnabled(false);
                startbtn.setEnabled(true);
                playbtn.setEnabled(true);
                stopplay.setEnabled(false);
                Toast.makeText(getActivity().getApplicationContext(),"Playing Audio Stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }

        //startbtn.setOnClickListener(this);
        //stopbtn.setOnClickListener(this);
        //playbtn.setOnClickListener(this);
        //stopplay.setOnClickListener(this);



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length> 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] ==  PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getActivity().getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    public boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }
}