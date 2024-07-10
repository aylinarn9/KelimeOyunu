package com.aylinexample.yazlabproje

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class Direk6Oyun : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    private lateinit var db: FirebaseFirestore
    private lateinit var girilenKelime: String
    private lateinit var girilenKelime2: String
    private var kelimeGirildi = false
    private var kelimeGirildi2 = false
    private lateinit var timeLeftFormatted: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_direk6_oyun)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        auth = FirebaseAuth.getInstance()
        var currentUser=auth.currentUser
        database= FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("bilgi")
        val textViewCountdown = findViewById<TextView>(R.id.textgerisayim)
        var zamanlayici: Zamanlayici? = null

        // 60 saniyelik zamanlayıcıyı başlat
        zamanlayici = Zamanlayici(60000, textViewCountdown)
        zamanlayici?.start()

        val harf1  = findViewById<EditText>(R.id.editText1)
        val harf2  = findViewById<EditText>(R.id.editText2)
        val harf3  = findViewById<EditText>(R.id.editText3)
        val harf4  = findViewById<EditText>(R.id.editText4)
        val harf5  = findViewById<EditText>(R.id.editText5)
        val harf6  = findViewById<EditText>(R.id.editText6)

        val btnonayla = findViewById<Button>(R.id.button2)



        val kelimelist = mutableListOf<String>()

        val kelimelist2 = mutableListOf<String>()
   btnonayla.setOnClickListener {
       var userReference = databaseReference?.child(currentUser?.uid!!)


       var degerdeneme: String? =null
       userReference?.addValueEventListener(object :ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
               degerdeneme = snapshot.child("kullaniciad").value.toString()
               println(degerdeneme)

               val requestsRef = FirebaseFirestore.getInstance().collection("requests")
               requestsRef.whereEqualTo("receiverId", degerdeneme)
                   .addSnapshotListener { snapshot, e ->
                       if (e != null) {
                           Log.e("Firestore", "Error listening to incoming requests: $e")
                           return@addSnapshotListener
                       }

                       // Gelen isteklerin işlenmesi
                       if (snapshot != null && !snapshot.isEmpty) {
                           for (doc in snapshot.documents) {
                               val girilenHarf1 = harf1.text.toString()
                               val girilenHarf2 = harf2.text.toString()
                               val girilenHarf3 = harf3.text.toString()
                               val girilenHarf4 = harf4.text.toString()
                               val girilenHarf5 = harf5.text.toString()
                               val girilenHarf6 = harf6.text.toString()
                                girilenKelime = StringBuilder()
                                   .append(girilenHarf1)
                                   .append(girilenHarf2)
                                   .append(girilenHarf3)
                                   .append(girilenHarf4)
                                   .append(girilenHarf5)
                                   .append(girilenHarf6)
                                   .toString()


                               if (girilenKelime.isNotBlank()) {
                                 //  println(girilenKelime)
                                   kelimelist.add(girilenKelime)
                                   kelimeGirildi = true



                                   Toast.makeText(textViewCountdown.context, "Oyun devam ediyor!", Toast.LENGTH_SHORT).show()
                                   val intent = Intent(textViewCountdown.context, Direk6KelimeTahmin::class.java)

                                   intent.putExtra("girilenkelime",girilenKelime)
                                   intent.putExtra("kalansure", timeLeftFormatted)
                                   startActivity(intent)

                               } else {

                                 //  Toast.makeText(textViewCountdown.context, "Oyunu kaybettiniz!", Toast.LENGTH_SHORT).show()
                               }
                           }
                       }
                       else {


                           Toast.makeText(textViewCountdown.context, "Bu işlemi gerçekleştirmek için yetkiniz bulunmamaktadır.", Toast.LENGTH_SHORT).show()
                       }
                   }
        //////////////////////////////////////////
               requestsRef.whereEqualTo("senderId", degerdeneme)
                   .addSnapshotListener { snapshot, e ->
                       if (e != null) {
                           Log.e("Firestore", "Error listening to incoming requests: $e")
                           return@addSnapshotListener
                       }

                       // Gelen isteklerin işlenmesi
                       if (snapshot != null && !snapshot.isEmpty) {
                           for (doc in snapshot.documents) {
                               val girilenHarf1 = harf1.text.toString()
                               val girilenHarf2 = harf2.text.toString()
                               val girilenHarf3 = harf3.text.toString()
                               val girilenHarf4 = harf4.text.toString()
                               val girilenHarf5 = harf5.text.toString()
                               val girilenHarf6 = harf6.text.toString()
                                girilenKelime2 = StringBuilder()
                                   .append(girilenHarf1)
                                   .append(girilenHarf2)
                                   .append(girilenHarf3)
                                   .append(girilenHarf4)
                                   .append(girilenHarf5)
                                   .append(girilenHarf6)
                                   .toString()


                               if (girilenKelime2.isNotBlank()) {

                                   kelimelist2.add(girilenKelime2)
                                   kelimeGirildi2 = true

                                   Toast.makeText(textViewCountdown.context, "Oyun devam ediyor!", Toast.LENGTH_SHORT).show()
                                   val intent = Intent(textViewCountdown.context, Direk6KelimeTahmin::class.java)

                                   intent.putExtra("girilenkelime2",girilenKelime2)
                                   intent.putExtra("kalansure2", timeLeftFormatted)
                                   startActivity(intent)
                               } else {
                                   // Kelime girilmedi veya zaman aşımına uğradı
                                  // Toast.makeText(textViewCountdown.context, "Oyunu kaybettiniz!", Toast.LENGTH_SHORT).show()
                               }
                           }
                       }
                       else {


                           Toast.makeText(textViewCountdown.context, "Bu işlemi gerçekleştirmek için yetkiniz bulunmamaktadır.", Toast.LENGTH_SHORT).show()
                       }



                   }

           }

           override fun onCancelled(error: DatabaseError) {

           }

       })


}



    }

  inner class Zamanlayici(private val totalTime: Long, private val textViewCountdown: TextView) : CountDownTimer(totalTime, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            // Her saniye geri sayımı güncelle
            val secondsRemaining = millisUntilFinished / 1000
         //  timeLeftFormatted = String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60)
           timeLeftFormatted = String.format("%d", secondsRemaining)
            textViewCountdown.text = timeLeftFormatted

        }

        override fun onFinish() {



            if (!kelimeGirildi && !kelimeGirildi2) {
                var zamanlayicii: Zamanlayici? = null

                zamanlayicii = Zamanlayici(60000, textViewCountdown)
                zamanlayicii?.start()
            }
            /// receiverId kelime girdi senderId girmedi
            else if(kelimeGirildi && !kelimeGirildi2){
                Toast.makeText(textViewCountdown.context, "Oyunu kaybettinizZZZ!", Toast.LENGTH_SHORT).show()
            }
            /// senderId kelime girdi receiverId kelime girmedi
            else if(!kelimeGirildi && kelimeGirildi2){
                Toast.makeText(textViewCountdown.context, "Oyunu kaybettinizZZZ ikinci durum!", Toast.LENGTH_SHORT).show()
            }

        }
    }



}