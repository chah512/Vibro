package sam.ensat.authentificationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends AppCompatActivity {
    FirebaseUser user;
    DatabaseReference ref;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);

        LineChart chart = findViewById(R.id.chart);


        chart.getDescription().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // Get your X axis


        YAxis yAxis = chart.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setGranularity(1);

        Legend legend = chart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);


        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        //get user information from database and store them in the variables


        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("UsersData").child("MvD6IBGKOCTGFZk9DkGu35injVV2").child("readings");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Entry> entries = new ArrayList<>();
                int index = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String timeStr = snapshot.child("time").getValue(String.class);
                    long time = Long.parseLong(timeStr);
                    String vibroStr = snapshot.child("vibro").getValue(String.class);
                    float vibro = Float.parseFloat(vibroStr);
                    entries.add(new Entry(time, vibro));
                }
                LineDataSet dataSet = new LineDataSet(entries, "Vibration vs Time"); // add entries to dataset
                dataSet.setColor(Color.BLUE);
                dataSet.setValueTextColor(Color.BLACK);

                LineData lineData = new LineData(dataSet);
                chart.setData(lineData);
                chart.getXAxis().setValueFormatter(new DateAxisValueFormatter());
                chart.invalidate(); // refresh chart

                // Check if the latest value added is greater than 50000
                DataSnapshot latestSnapshot = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (latestSnapshot == null || Long.parseLong(snapshot.child("time").getValue(String.class)) > Long.parseLong(latestSnapshot.child("time").getValue(String.class))) {
                        latestSnapshot = snapshot;
                    }
                }
                if (latestSnapshot != null) {
                    String latestValueStr = latestSnapshot.child("vibro").getValue(String.class);
                    float latestValue = Float.parseFloat(latestValueStr);
                    if (latestValue > 50000) {
                        // Show dialog notification
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                        builder.setMessage("Latest vibration is greater than 50000 !")
                                .setTitle("Alert");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
    //action of button logout
    public void LogOut(View view) {
        //destroy current user session
        FirebaseAuth.getInstance().signOut();
        //then lanch main activity
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
        finish();
    }
}