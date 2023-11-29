package com.example.aplikacja3

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.database.database
import androidx.compose.runtime.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import android.content.ContentValues.TAG
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.*
import com.google.firebase.database.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val database = Firebase.database
    val myRef = database.getReference("message")


    var zapis:String? by remember {
        mutableStateOf("")
    }
    myRef.addValueEventListener(object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val value = snapshot.getValue<String>()
            zapis = value
          //  Log.d(TAG, "Value is: " + value)

        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value", error.toException())
        }
    })
    var text by remember {
        mutableStateOf("")
    }

    Column( modifier = Modifier.padding(20.dp)) {
        TextField(
            value = text,
            onValueChange = {newtext -> text = newtext},
            label = { Text(text = "podaj imię")}
        )
        Button(
            onClick = { myRef.setValue(text)},
            modifier = Modifier.padding(20.dp)
        ) {
            Text(text = "Zapisz")
        }
        Spacer(modifier = Modifier.padding(30.dp))
        Text(text = "$zapis")
    }
}

