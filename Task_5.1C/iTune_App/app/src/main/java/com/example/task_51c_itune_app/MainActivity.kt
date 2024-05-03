package com.example.task_51c_itune_app

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

class MainActivity : AppCompatActivity() {
    private lateinit var databse: DatabaseStr
    private lateinit var signupBTN: Button
    private lateinit var loginBTN: Button
    private lateinit var username: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databse = DatabaseStr(this)
        username = findViewById(R.id.username_input)
        password = findViewById(R.id.password_input)
        loginBTN = findViewById(R.id.login_button)
        signupBTN = findViewById(R.id.signup_button)

        loginBTN.setOnClickListener {
            val username = username.text.toString().trim()
            val password = password.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val cursor = databse.getUser(username)
                if (cursor != null && cursor.moveToFirst()) {
                    val storedPassword = cursor.getString(cursor.getColumnIndex(DatabaseStr.COLUMN_PASSWORD))
                    if (password == storedPassword) {
                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Main_Page::class.java)
                        intent.putExtra("username", username)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Invalid User", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Fill in the Fields", Toast.LENGTH_SHORT).show()
            }
        }

        signupBTN.setOnClickListener {
            val intent = Intent(this, SignUpPage::class.java)
            startActivity(intent)
        }
    }
}
