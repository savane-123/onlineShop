import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.savane.R;

public class MainActivity extends AppCompatActivity {
    private EditText text1;
    private EditText text2;
    private Button   login;
    private Button   sign_up;
    private Button   back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        login=findViewById(R.id.login);
        sign_up=findViewById(R.id.sign);
        back=findViewById(R.id.back);
        final String t1=text1.getText().toString().trim();
        final String t2=text2.getText().toString().trim();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st1=text1.getText().toString();
                Toast.makeText(getApplicationContext()," Welcom  "+st1,Toast.LENGTH_LONG).show();

            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), com.savane.activities.sign_up.class);
                startActivity(i);
            }
        });
    }
}
