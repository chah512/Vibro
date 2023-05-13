package sam.ensat.authentificationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.invoke.ConstantCallSite;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        System.out.println("instance of database "+FirebaseDatabase.getInstance());
        System.out.println("on crate");
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

    }
    //check if there is any active session of user and redirecting him to his profile
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null){
            startActivity(new Intent (MainActivity.this, ProfileActivity.class));
            finish();
        }
    }

    //action for login button
    public void OpenLoginView(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        // animation
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.login_btn),"transaction_login");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
        //start the activity
        startActivity(intent,options.toBundle());
    }
    //action for register button
    public void OpenRegisterView(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}