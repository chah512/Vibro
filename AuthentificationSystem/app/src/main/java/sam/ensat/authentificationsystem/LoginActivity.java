package sam.ensat.authentificationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout passwordInput, emailInput;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
    }
    //action of login button : verify if user have an account
    public void Login(View view) {
        if (!ValidateُEmail() | !ValidateُPassword()) {
            return;
        }
        String email = emailInput.getEditText().getText().toString().trim();
        String pdw = passwordInput.getEditText().getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,pdw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Failed to Login ! PLZ Check your Crudentiel",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Check if the email input not empty and match email pattern
    private Boolean ValidateُEmail() {
        String val = emailInput.getEditText().getText().toString();
        if (val.isEmpty()) {
            emailInput.setError("Email Address is required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            emailInput.setError("Invalid Email Address");
            return false;
        } else {
            emailInput.setError(null);
            emailInput.setErrorEnabled(false);
            return true;
        }
    }

    //Check if password input not empty
    private Boolean ValidateُPassword() {
        String val = passwordInput.getEditText().getText().toString();
        if (val.isEmpty()) {
            passwordInput.setError("Password is required");
            return false;
        } else {
            passwordInput.setError(null);
            passwordInput.setErrorEnabled(false);
            return true;
        }
    }
    //action of create account if user doent have an account
    public void Register(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
}