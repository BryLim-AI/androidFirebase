package com.example.androidfirebase

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidfirebase.ui.theme.AndroidFirebaseTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class viewActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidFirebaseTheme {
             Surface(
                 modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background)
             {
                 var employeeList = mutableStateListOf<employed?>()
                 var db: FirebaseFirestore = FirebaseFirestore.getInstance()

                 db.collection("employee").get()
                     .addOnSuccessListener {
                         queryDocumentSnapshots ->
                         if(!queryDocumentSnapshots.isEmpty){
                             val list = queryDocumentSnapshots.documents

                             for(d in list){
                                 val e: employed? = d.toObject(employed::class.java)
                                 employeeList.add(e)
                             }
                         }else{
                             Toast.makeText(
                                 this@viewActivity,
                                 "No data found in Database",
                                 Toast.LENGTH_SHORT
                             ).show()
                         }
                     }
                     .addOnFailureListener {
                         Toast.makeText(
                             this@viewActivity,
                             "Fail to get the data.",
                             Toast.LENGTH_SHORT
                         ).show()
                     }
                 //show list
                 listUI(LocalContext.current, employeeList)
             }
            }
        }
    }
}

@Composable
fun listUI(context: Context, employList: SnapshotStateList<employed?>) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White),
        // on below line adding vertical and
        // horizontal alignment for column.
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn{
            itemsIndexed(employList){
                index, item ->
                Card(
                    modifier = Modifier.padding(8.dp),
                ){
                    Column(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()) {

                        Spacer(modifier = Modifier.width(5.dp))
                        employList[index]?.fullName?.let {
                            Text(text = it,
                                modifier = Modifier.padding(4.dp),

                                // on below line we are adding
                                // color for our text
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 20.sp, fontWeight = FontWeight.Bold

                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        employList[index]?.emailAddress?.let {
                            Text(text = it,
                                modifier = Modifier.padding(4.dp),

                                // on below line we are adding
                                // color for our text
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 20.sp, fontWeight = FontWeight.Bold

                                )
                            )
                        }


                    }
                }
            }
        }
    }
}
