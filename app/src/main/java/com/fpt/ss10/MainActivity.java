package com.fpt.ss10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fpt.ss10.database.AppDatabase;
import com.fpt.ss10.database.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edName,edDes;
    Spinner spinner;
    CheckBox checkBox;
    Button btRegister;
    String gender = "Male";
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AppDatabase.getAppDatabase(this);
        edName = findViewById(R.id.edUser);
        spinner = findViewById(R.id.spinner);
        checkBox = findViewById(R.id.ck);
        btRegister = findViewById(R.id.btRegister);
        edDes = findViewById(R.id.edDes);

        String[] listgenser = {"Male","Female","Unknow]"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listgenser
        );

        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gender = listgenser[i];
            }
        });
    }

    private void onRegister(){
        if (!validate()){
            return;
        }
        User user = new User();
        user.username = edName.getText().toString();
        user.des = edDes.getText().toString();
        user.gender = gender;
        long id = database.userDao().insertUser(user);
        goList();
    }

    private void goList() {
        Intent intent = new Intent(this,ListUserActivity.class);
        startActivity(intent);
    }

    private boolean validate() {
        String mes = null;
        if (edName.getText().toString().trim().isEmpty()){
            mes = "Chưa nhập username";
        }else if (edDes.getText().toString().trim().isEmpty()){
            mes = "Chưa nhập giới thiệu";
        }else if (!checkBox.isChecked()){
            mes = "Bạn phải đồng ý điều khoản sử dụng";
        }
        if (mes != null){
            Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btRegister:
                onRegister();
                break;
            default:
                break;
        }
    }
}