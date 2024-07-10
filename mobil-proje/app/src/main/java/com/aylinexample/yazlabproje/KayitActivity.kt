package com.aylinexample.yazlabproje

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class KayitActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kayit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firestore=FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("bilgi")

        val btnKayitol = findViewById<Button>(R.id.kbuttonkayit)
        val kullaniciAd  = findViewById<EditText>(R.id.kayitkullaniciad)
        val sifree = findViewById<EditText>(R.id.kayitsifre)

        //  kullaniciadd=findViewById(R.id.kayitkullaniciad)
        //  sifree= findViewById(R.id.kayitsifre)

        // val kullaniciad=kullaniciadd.text.toString()
        // val sifre=sifree.text.toString()

        //var datab=FirebaseDatabase.getInstance().reference

        btnKayitol.setOnClickListener {
            auth.createUserWithEmailAndPassword(kullaniciAd.text.toString(),sifree.text.toString()).addOnCompleteListener(this){
                    task ->
                if(task.isSuccessful){

                    //suanki id al
                    var currentuser=  auth.currentUser
                    //kullanici id sini alıp kulluaniciad ve sifre kaydet
                    var currentUserDb=currentuser?.let { it1 -> databaseReference?.child(it1.uid) }
                    currentUserDb?.child("kullaniciad")?.setValue(kullaniciAd.text.toString())
///firestore kullanımı//
                    /*
                    currentuser?.let {
                        val user=it.email

                        val datamap = HashMap<String, Any>()
                        datamap.put("kullaniciad", user!!)
                        firestore.collection("oyuncubilgi").add(datamap).addOnSuccessListener {

                        }.addOnFailureListener {
                            Toast.makeText(this, "hata", Toast.LENGTH_SHORT).show()
                        }
                        firestore.collection("oyuncubilgi").addSnapshotListener { value, error ->

                            if (error != null) {

                            } else {
                                if (value != null) {
                                    if (value.isEmpty) {
                                        Toast.makeText(this, "mesaj yok", Toast.LENGTH_SHORT).show()
                                    } else {
                                        val documents = value.documents
                                        for (doc in documents) {
                                            val kullaniciad = doc.get("kullaniciad") as String
                                            print(kullaniciad)
                                        }
                                    }

                                }
                            }
                        }
                    }

///////
*/

                    Toast.makeText(this, "Kullanıcı kaydı başarıyla oluşturuldu", Toast.LENGTH_SHORT).show()
                }else{
                    val exception = task.exception
                    Toast.makeText(this, "Hata: ${exception?.message}", Toast.LENGTH_SHORT).show()
                    //Toast.makeText(this, "hatali", Toast.LENGTH_SHORT).show()
                }
            }


        }

        val btngirisedon = findViewById<Button>(R.id.kbuttongiris)
        btngirisedon.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}