package com.example.task_51c_itune_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpPage : AppCompatActivity() {

    private lateinit var database: DatabaseStr
    private lateinit var mFullnameEditext: EditText
    private lateinit var mUsernameEditText: EditText
    private lateinit var mPasswordEditText: EditText
    private lateinit var mConfirmPassword: EditText
    private lateinit var mSignupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        database = DatabaseStr(this)
        mFullnameEditext = findViewById(R.id.Fullname)
        mUsernameEditText = findViewById(R.id.Username)
        mPasswordEditText = findViewById(R.id.password)
        mConfirmPassword = findViewById(R.id.confirmpassword)
        mSignupButton = findViewById(R.id.buttonsignup)

        mSignupButton.setOnClickListener {
            val fullname = mFullnameEditext.text.toString().trim()
            val username = mUsernameEditText.text.toString().trim()
            val password = mPasswordEditText.text.toString().trim()
            val confirmPassword = mConfirmPassword.text.toString().trim()

            if (password == confirmPassword) {
                // Check if username already exists
                if (database.getUser(username)?.moveToFirst() == true) {
                    // User exists
                    Toast.makeText(this@SignUpPage, "User already exists", Toast.LENGTH_SHORT).show()
                } else {
                    // Insert new user
                    val result = database.insertUser(fullname, username, password)
                    if (result != -1L) {
                        // User inserted successfully
                        Toast.makeText(this@SignUpPage, "Successfully Created account", Toast.LENGTH_SHORT).show()
                        // Proceed to login activity or perform desired action
                    } else {
                        // Error inserting user
                        Toast.makeText(this@SignUpPage, "Invalid", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this@SignUpPage, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
