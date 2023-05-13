package sam.ensat.authentificationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Register2LevelActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RadioGroup radioGroup;
    private DatePicker dop;
    private RadioButton radioButton;
    User _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2_level);
        //get the data from intent
        _user = (User) getIntent().getSerializableExtra("user");
        //instanciate the FirbaseAuth object
        mAuth = FirebaseAuth.getInstance();

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        dop = (DatePicker) findViewById((R.id.date));


    }
    //back to login activity when user click on already have an account
    public void SignIn(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

    }
    // action of button register : try to register user on database
    public void RegisterFinal(View view) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id and set user'sexe
        radioButton = (RadioButton) findViewById(selectedId);
        _user.setSexe(radioButton.getText().toString());
        //get the date from datepicker and set it in user attribute
        Date date = (Date) new Date
                (dop.getYear(), dop.getMonth(), dop.getDayOfMonth());
        _user.setDop(date);
        //save the user in database
        mAuth.createUserWithEmailAndPassword(_user.getEmail(),_user.getPassword()).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                    setValue(new User(_user.getName(),_user.getMobile(),_user.getSexe(),_user.getDop(),_user.getEmail())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "User has ben registred successfully", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed to register user ! plz Try again...", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Failed to register user ! plz Try again...", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                        }
                    }

                });

    }
}