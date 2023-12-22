package com.example.androidfirebase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.androidfirebase.ui.theme.AndroidFirebaseTheme

class MainActivity : ComponentActivity() {

    private var fullName by mutableStateOf(" ")
    private var emailAddress by mutableStateOf(" ")
    private lateinit var db: FirebaseFirestore

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()


        setContent {
            AndroidFirebaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                    ) {
                        TextField(value = fullName, onValueChange = {
                            newFullName -> fullName = newFullName
                        },
                        label = { Text(text = "Full Name:")},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                            )

                        TextField(value = emailAddress, onValueChange = {
                                newEmail -> emailAddress = newEmail
                        },
                            label = { Text(text = "Email Address:")},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )

                        Button(onClick = {
                            onSubmit(fullName, emailAddress)
                        },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                                Text("Submit");
                        }
                        firebaseUI(LocalContext.current)
                    }
                }
            }
        }
    }

    @Composable
    fun firebaseUI(context: Context){
        Button(
            onClick = {
                // on below line opening course details activity.
                context.startActivity(Intent(context, viewActivity::class.java))
            },
            // on below line we are
            // adding modifier to our button.
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // on below line we are adding text for our button
            Text(text = "View Employees", modifier = Modifier.padding(8.dp))
        }
    }

    private fun onSubmit(fullName: String, emailAddress: String) {
        val dbEmployees = db.collection("employee")

        val employeeData = hashMapOf(
            "fullName" to fullName,
            "emailAddress" to emailAddress
        )

        dbEmployees
            .add(employeeData)
            .addOnSuccessListener {
              showToast("Add Successful.");
            }
            .addOnFailureListener {
                showToast("Add Failed.");
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

