package sam.ensat.authentificationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout name, email, mobile, password, passwordConfirmation;
    Button next, login;
    ImageView img;
    TextView titleText;
    DatabaseReference db;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.input_name);
        email = findViewById(R.id.input_email);
        mobile = findViewById(R.id.input_mobile);
        password = findViewById(R.id.input_password);
        passwordConfirmation = findViewById(R.id.input_passwordConfirmation);
        next = findViewById(R.id.Next_btn);
        login = findViewById(R.id.login_btn);
        titleText = findViewById(R.id.textTiltle);
        img = findViewById(R.id.img);
        db = FirebaseDatabase.getInstance().getReference().child("User");


    }
    //back to login activity when user click on already have an account
    public void SignIn(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

    }


    public void RegisterNext(View view) {
        //validate inputs
        if (!ValidateName() | !ValidateُEmail() | !ValidateُPassword() | !ValidateConfirmation()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), Register2LevelActivity.class);
        //Add Shared Animation
        Pair[] pairs = new Pair[4];
        pairs[1] = new Pair<View, String>(next, "transition_next");
        pairs[2] = new Pair(login, "transition_login");
        pairs[3] = new Pair(titleText, "transition_title");
        pairs[0] = new Pair(img, "transition_img");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
        //register all the fieled in object of type user
        user = new User(name.getEditText().getText().toString(), email.getEditText().getText().toString(),
                mobile.getEditText().getText().toString(), password.getEditText().getText().toString()
        );
        //send user in intent
        intent.putExtra("user", user);
        //open the view associated to Register2LevelActivity
        startActivity(intent, options.toBundle());
        finish();

    }

    //validate name input
    private Boolean ValidateName() {
        String val = name.getEditText().getText().toString();
        if (val.isEmpty()) {
            name.setError("Name is required");
            return false;
        } else {
            name.setError(null);
            name.setErrorEnabled(false);
            return true;
        }
    }
    //validate email input
    private Boolean ValidateُEmail() {
        String val = email.getEditText().getText().toString();
        if (val.isEmpty()) {
            email.setError("Email Address is required");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            email.setError("Invalid Email Address");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
    //validat password input
    private Boolean ValidateُPassword() {
        String val = password.getEditText().getText().toString();
        String pwdPattern = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                ".{8,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            password.setError("Password is required");
            return false;
        } else if (!val.matches(pwdPattern)) {
            password.setError("Invalide Password : provide at least 8 alphanumerics caracteres");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
// validate password confirmation input
    private Boolean ValidateConfirmation() {
        String val = passwordConfirmation.getEditText().getText().toString();
        String val0 = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            passwordConfirmation.setError("Plz confirm your Password ");
            return false;
        } else if (!val.equals(val0)) {
            passwordConfirmation.setError("Password doesn't match");
            return false;
        } else {
            passwordConfirmation.setError(null);
            passwordConfirmation.setErrorEnabled(false);
            return true;
        }
    }
}
