package com.example.citispotter.sqlLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.citispotter.R;

public class check_database extends AppCompatActivity {
    private EditText title,desc;
    private Button button;
//    HistoryDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_database);
        title=(EditText)findViewById(R.id.db_title);
        desc=(EditText)findViewById(R.id.db_desc);
        button=(Button)findViewById(R.id.db_button);
//        db=new HistoryDatabaseHelper(this);

    }

    /*public void doSomething(View view) {
        boolean insert=db.Insert(title.getText().toString(),desc.getText().toString());
        if (insert)
            Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Not inserted", Toast.LENGTH_SHORT).show();
    }*/
}
