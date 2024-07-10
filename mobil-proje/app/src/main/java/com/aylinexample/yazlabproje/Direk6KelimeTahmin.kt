package com.aylinexample.yazlabproje

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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

class Direk6KelimeTahmin : AppCompatActivity() {
    private var countDownTimer: CountDownTimer? = null
    private var shortCountDownTimer: CountDownTimer? = null
    private var isButtonClicked = false

    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    private lateinit var db: FirebaseFirestore

    var tahminkontrol = false
    var tahminkontrol2 = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_direk6_kelime_tahmin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        auth = FirebaseAuth.getInstance()
        var currentUser=auth.currentUser
        database= FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("bilgi")

        val girilenkelime= intent.getStringExtra("girilenkelime").toString()
        println(girilenkelime)
        val kalansuree=intent.getStringExtra("kalansure").toString()
        var oyunskor=0

        ////
        val girilenkelime2= intent.getStringExtra("girilenkelime2").toString()
        println(girilenkelime2)
        val kalansuree2=intent.getStringExtra("kalansure2").toString()
        var oyunskor2=0


        val textViewCountdown = findViewById<TextView>(R.id.textgerisayim)

        val harf1  = findViewById<EditText>(R.id.editText1)
        val harf2  = findViewById<EditText>(R.id.editText2)
        val harf3  = findViewById<EditText>(R.id.editText3)
        val harf4  = findViewById<EditText>(R.id.editText4)
        val harf5  = findViewById<EditText>(R.id.editText5)
        val harf6  = findViewById<EditText>(R.id.editText6)

        val harf7  = findViewById<EditText>(R.id.editText7)
        val harf8  = findViewById<EditText>(R.id.editText8)
        val harf9  = findViewById<EditText>(R.id.editText9)
        val harf10 = findViewById<EditText>(R.id.editText10)
        val harf11  = findViewById<EditText>(R.id.editText11)
        val harf12  = findViewById<EditText>(R.id.editText12)

        val harf13  = findViewById<EditText>(R.id.editText13)
        val harf14  = findViewById<EditText>(R.id.editText14)
        val harf15  = findViewById<EditText>(R.id.editText15)
        val harf16 = findViewById<EditText>(R.id.editText16)
        val harf17  = findViewById<EditText>(R.id.editText17)
        val harf18  = findViewById<EditText>(R.id.editText18)

        val harf19 = findViewById<EditText>(R.id.editText19)
        val harf20  = findViewById<EditText>(R.id.editText20)
        val harf21  = findViewById<EditText>(R.id.editText21)
        val harf22 = findViewById<EditText>(R.id.editText22)
        val harf23  = findViewById<EditText>(R.id.editText23)
        val harf24  = findViewById<EditText>(R.id.editText24)

        val harf25 = findViewById<EditText>(R.id.editText25)
        val harf26  = findViewById<EditText>(R.id.editText26)
        val harf27  = findViewById<EditText>(R.id.editText27)
        val harf28 = findViewById<EditText>(R.id.editText28)
        val harf29  = findViewById<EditText>(R.id.editText29)
        val harf30  = findViewById<EditText>(R.id.editText30)

        val harf31 = findViewById<EditText>(R.id.editText31)
        val harf32  = findViewById<EditText>(R.id.editText32)
        val harf33 = findViewById<EditText>(R.id.editText33)
        val harf34 = findViewById<EditText>(R.id.editText34)
        val harf35  = findViewById<EditText>(R.id.editText35)
        val harf36  = findViewById<EditText>(R.id.editText36)

        val btntahmin = findViewById<Button>(R.id.btntahmin)

        val btntoyundancık = findViewById<Button>(R.id.btnoyundancık)
        startCountdown(textViewCountdown)
        btntoyundancık.setOnClickListener {
             mesajDialog()
          }
        var tahminhakki=6
        var tahminhakki2=6

        var userReference = databaseReference?.child(currentUser?.uid!!)
        var degerdeneme: String? =null
        userReference?.addValueEventListener(object : ValueEventListener {
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
                                    btntahmin.setOnClickListener {
                                        isButtonClicked = true
                                        //countDownTimer?.cancel()
                                        shortCountDownTimer?.cancel()
                                        textViewCountdown.visibility = View.GONE


                                        val harfler =
                                            arrayOf(harf1, harf2, harf3, harf4, harf5, harf6)
//birinci satır için islemler
                                        val girilenHarf1 = harf1.text.toString()
                                        val girilenHarf2 = harf2.text.toString()
                                        val girilenHarf3 = harf3.text.toString()
                                        val girilenHarf4 = harf4.text.toString()
                                        val girilenHarf5 = harf5.text.toString()
                                        val girilenHarf6 = harf6.text.toString()
                                        val tahminedilenkelime2 = StringBuilder()
                                            .append(girilenHarf1)
                                            .append(girilenHarf2)
                                            .append(girilenHarf3)
                                            .append(girilenHarf4)
                                            .append(girilenHarf5)
                                            .append(girilenHarf6)
                                            .toString()

                                        if (tahminedilenkelime2 == girilenkelime) {

                                            println("tahminedilen kelime dogru")
                                            tahminkontrol2 = true
                                            Toast.makeText(
                                                textViewCountdown.context,
                                                "Tebrikler, doğru kelimeyi buldunuz!",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()

                                        } else {


                                            tahminhakki2 -= 1
                                            // Her bir harfi kontrol et ve rengini ayarla
                                            for ((index, harf) in tahminedilenkelime2.withIndex()) {

                                                if (girilenkelime.contains(harf)) {
                                                    if (girilenkelime[index] == harf) {

                                                        harfler[index].background.setTint(Color.GREEN)
                                                    } else {

                                                        harfler[index].background.setTint(Color.YELLOW)
                                                    }
                                                } else {

                                                    harfler[index].background.setTint(Color.GRAY)
                                                }
                                            }
                                        }

                                        val harflerikincisatır =
                                            arrayOf(harf7, harf8, harf9, harf10, harf11, harf12)
                                        //ikinci satır için işlemler
                                        val girilenHarf7 = harf7.text.toString()
                                        val girilenHarf8 = harf8.text.toString()
                                        val girilenHarf9 = harf9.text.toString()
                                        val girilenHarf10 = harf10.text.toString()
                                        val girilenHarf11 = harf11.text.toString()
                                        val girilenHarf12 = harf12.text.toString()
                                        val tahminedilenkelimeikincisatir = StringBuilder()
                                            .append(girilenHarf7)
                                            .append(girilenHarf8)
                                            .append(girilenHarf9)
                                            .append(girilenHarf10)
                                            .append(girilenHarf11)
                                            .append(girilenHarf12)
                                            .toString()

                                        if (tahminedilenkelimeikincisatir == girilenkelime) {

                                            println("tahminedilen kelime dogru")
                                            tahminkontrol2 = true
                                            Toast.makeText(
                                                textViewCountdown.context,
                                                "Tebrikler, doğru kelimeyi buldunuz!",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()

                                        } else {


                                            tahminhakki2 -= 1

                                            for ((index, harf) in tahminedilenkelimeikincisatir.withIndex()) {

                                                if (girilenkelime.contains(harf)) {
                                                    if (girilenkelime[index] == harf) {

                                                        harflerikincisatır[index].background.setTint(
                                                            Color.GREEN
                                                        )
                                                    } else {

                                                        harflerikincisatır[index].background.setTint(
                                                            Color.YELLOW
                                                        )
                                                    }
                                                } else {

                                                    harflerikincisatır[index].background.setTint(
                                                        Color.GRAY
                                                    )
                                                }
                                            }
                                        }

                                        val harflerucuncusatır =
                                            arrayOf(harf13, harf14, harf15, harf16, harf17, harf18)
                                        //3.satır icin islemler
                                        val girilenHarf13 = harf13.text.toString()
                                        val girilenHarf14 = harf14.text.toString()
                                        val girilenHarf15 = harf15.text.toString()
                                        val girilenHarf16 = harf16.text.toString()
                                        val girilenHarf17 = harf17.text.toString()
                                        val girilenHarf18 = harf18.text.toString()
                                        val tahminedilenkelimeucuncusatir = StringBuilder()
                                            .append(girilenHarf13)
                                            .append(girilenHarf14)
                                            .append(girilenHarf15)
                                            .append(girilenHarf16)
                                            .append(girilenHarf17)
                                            .append(girilenHarf18)
                                            .toString()

                                        if (tahminedilenkelimeikincisatir == girilenkelime) {

                                            println("tahminedilen kelime dogru")
                                            tahminkontrol2 = true
                                            Toast.makeText(
                                                textViewCountdown.context,
                                                "Tebrikler, doğru kelimeyi buldunuz!",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()

                                        } else {

                                            tahminhakki2 -= 1

                                            for ((index, harf) in tahminedilenkelimeucuncusatir.withIndex()) {

                                                if (girilenkelime.contains(harf)) {
                                                    if (girilenkelime[index] == harf) {

                                                        harflerucuncusatır[index].background.setTint(
                                                            Color.GREEN
                                                        )
                                                    } else {

                                                        harflerucuncusatır[index].background.setTint(
                                                            Color.YELLOW
                                                        )
                                                    }
                                                } else {

                                                    harflerucuncusatır[index].background.setTint(
                                                        Color.GRAY
                                                    )
                                                }
                                            }
                                        }

                                        val harflerdorduncusatır =
                                            arrayOf(harf19, harf20, harf21, harf22, harf23, harf24)
                                        //4.satır icin islemler
                                        val girilenHarf19 = harf19.text.toString()
                                        val girilenHarf20 = harf20.text.toString()
                                        val girilenHarf21 = harf21.text.toString()
                                        val girilenHarf22 = harf22.text.toString()
                                        val girilenHarf23 = harf23.text.toString()
                                        val girilenHarf24 = harf24.text.toString()
                                        val tahminedilenkelimedorduncusatir = StringBuilder()
                                            .append(girilenHarf19)
                                            .append(girilenHarf20)
                                            .append(girilenHarf21)
                                            .append(girilenHarf22)
                                            .append(girilenHarf23)
                                            .append(girilenHarf24)
                                            .toString()

                                        if (tahminedilenkelimedorduncusatir == girilenkelime) {

                                            println("tahminedilen kelime dogru")
                                            tahminkontrol2 = true
                                            Toast.makeText(
                                                textViewCountdown.context,
                                                "Tebrikler, doğru kelimeyi buldunuz!",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()

                                        } else {

                                            tahminhakki2 -= 1

                                            for ((index, harf) in tahminedilenkelimedorduncusatir.withIndex()) {

                                                if (girilenkelime.contains(harf)) {
                                                    if (girilenkelime[index] == harf) {

                                                        harflerdorduncusatır[index].background.setTint(
                                                            Color.GREEN
                                                        )
                                                    } else {

                                                        harflerdorduncusatır[index].background.setTint(
                                                            Color.YELLOW
                                                        )
                                                    }
                                                } else {

                                                    harflerdorduncusatır[index].background.setTint(
                                                        Color.GRAY
                                                    )
                                                }
                                            }
                                        }

                                        val harflerbesincisatır =
                                            arrayOf(harf25, harf26, harf27, harf28, harf29, harf30)
                                        //5.satır icin islemler
                                        val girilenHarf25 = harf25.text.toString()
                                        val girilenHarf26 = harf26.text.toString()
                                        val girilenHarf27 = harf27.text.toString()
                                        val girilenHarf28 = harf28.text.toString()
                                        val girilenHarf29 = harf29.text.toString()
                                        val girilenHarf30 = harf30.text.toString()
                                        val tahminedilenkelimebesincisatir = StringBuilder()
                                            .append(girilenHarf25)
                                            .append(girilenHarf26)
                                            .append(girilenHarf27)
                                            .append(girilenHarf28)
                                            .append(girilenHarf29)
                                            .append(girilenHarf30)
                                            .toString()

                                        if (tahminedilenkelimebesincisatir == girilenkelime) {

                                            println("tahminedilen kelime dogru")
                                            tahminkontrol2 = true
                                            Toast.makeText(
                                                textViewCountdown.context,
                                                "Tebrikler, doğru kelimeyi buldunuz!",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()

                                        } else {

                                            tahminhakki2 -= 1

                                            for ((index, harf) in tahminedilenkelimebesincisatir.withIndex()) {

                                                if (girilenkelime.contains(harf)) {
                                                    if (girilenkelime[index] == harf) {

                                                        harflerbesincisatır[index].background.setTint(
                                                            Color.GREEN
                                                        )
                                                    } else {

                                                        harflerbesincisatır[index].background.setTint(
                                                            Color.YELLOW
                                                        )
                                                    }
                                                } else {

                                                    harflerbesincisatır[index].background.setTint(
                                                        Color.GRAY
                                                    )
                                                }
                                            }
                                        }

                                                                            val harfleraltincisatır=arrayOf(harf31, harf32, harf33, harf34, harf35, harf36)
                                                                             //6.satır icin islemler
                                                                             val girilenHarf31 = harf31.text.toString()
                                                                             val girilenHarf32 = harf32.text.toString()
                                                                             val girilenHarf33= harf33.text.toString()
                                                                             val girilenHarf34 = harf34.text.toString()
                                                                             val girilenHarf35 = harf35.text.toString()
                                                                             val girilenHarf36= harf36.text.toString()
                                                                             val tahminedilenkelimealtincisatir = StringBuilder()
                                                                                 .append(girilenHarf31)
                                                                                 .append(girilenHarf32)
                                                                                 .append(girilenHarf33)
                                                                                 .append(girilenHarf34)
                                                                                 .append(girilenHarf35)
                                                                                 .append(girilenHarf36)
                                                                                 .toString()

                                                                             if (tahminedilenkelimealtincisatir == girilenkelime) {

                                                                                 println("tahminedilen kelime dogru")
                                                                                 tahminkontrol2=true
                                                                                 Toast.makeText(textViewCountdown.context, "Tebrikler, doğru kelimeyi buldunuz!", Toast.LENGTH_SHORT)
                                                                                     .show()

                                                                             } else {


                                                                                 tahminhakki2-=1

                                                                                 for ((index, harf) in tahminedilenkelimealtincisatir.withIndex()) {

                                                                                     if (girilenkelime.contains(harf)) {
                                                                                         if (girilenkelime[index] == harf) {

                                                                                             harfleraltincisatır[index].background.setTint(Color.GREEN)
                                                                                             oyunskor+=10
                                                                                         } else {

                                                                                             harfleraltincisatır[index].background.setTint(Color.YELLOW)
                                                                                             oyunskor+=5
                                                                                         }
                                                                                     } else {

                                                                                         harfleraltincisatır[index].background.setTint(Color.GRAY)
                                                                                     }
                                                                                 }
                                                                                 oyunskor=oyunskor+kalansuree.toInt()
                                                                                 println("Toplam Puan receiver: $oyunskor")
                                                                                 Toast.makeText(textViewCountdown.context, " OYUN PUANI:$oyunskor", Toast.LENGTH_SHORT)
                                                                                     .show()
                                                                             }
                                                                             //tahmin hakki bitmisse ve dogru tahmin edememisse diger oyuncuya kalan her tahmin hakki icin 10sn daha ver
                                                                     /*    if(tahminhakki2==0 && !tahminkontrol2){

                                                                         }*/
                                                                             // eger iki oyuncuda tahmin edemediyse kazanan belirleme

                                                                            if(!tahminkontrol && !tahminkontrol2){
                                                                                 if(oyunskor>oyunskor2){
                                                                                     Toast.makeText(textViewCountdown.context, "senderId oyuncusu oyunu kazandı!", Toast.LENGTH_SHORT)
                                                                                         .show()
                                                                                 }
                                                                                 else if (oyunskor2>oyunskor){
                                                                                     Toast.makeText(textViewCountdown.context, "receiverId oyuncusu oyunu kazandı!", Toast.LENGTH_SHORT)
                                                                                         .show()
                                                                                 }
                                                                                 else if(oyunskor==oyunskor2){
                                                                                     Toast.makeText(textViewCountdown.context, "oyun berabere bitti!", Toast.LENGTH_SHORT)
                                                                                         .show()
                                                                                 }
                                                                             }


                                    }
                                }
                            } /*else {


                                Toast.makeText(
                                    textViewCountdown.context,
                                    "Bu işlemi gerçekleştirmek için yetkiniz bulunmamaktadır receiver.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }*/
                        }


                    ////////////////////sendertahminediyo//////////////////////
                    requestsRef.whereEqualTo("senderId", degerdeneme)
                        .addSnapshotListener { snapshot, e ->
                            if (e != null) {
                                Log.e("Firestore", "Error listening to incoming requests: $e")
                                return@addSnapshotListener
                            }

                            // Gelen isteklerin işlenmesi
                            if (snapshot != null && !snapshot.isEmpty) {
                                for (doc in snapshot.documents) {
                                    btntahmin.setOnClickListener {
                                        isButtonClicked = true
                                        //countDownTimer?.cancel()
                                        shortCountDownTimer?.cancel()
                                        textViewCountdown.visibility = View.GONE


                                        val harfler =
                                            arrayOf(harf1, harf2, harf3, harf4, harf5, harf6)
//birinci satır için islemler
                                        val girilenHarf1 = harf1.text.toString()
                                        val girilenHarf2 = harf2.text.toString()
                                        val girilenHarf3 = harf3.text.toString()
                                        val girilenHarf4 = harf4.text.toString()
                                        val girilenHarf5 = harf5.text.toString()
                                        val girilenHarf6 = harf6.text.toString()
                                        val tahminedilenkelime = StringBuilder()
                                            .append(girilenHarf1)
                                            .append(girilenHarf2)
                                            .append(girilenHarf3)
                                            .append(girilenHarf4)
                                            .append(girilenHarf5)
                                            .append(girilenHarf6)
                                            .toString()

                                        if (tahminedilenkelime == girilenkelime2) {

                                            println("tahminedilen kelime dogru")
                                            tahminkontrol = true
                                            Toast.makeText(
                                                textViewCountdown.context,
                                                "Tebrikler, doğru kelimeyi buldunuz!",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()

                                        } else {


                                            tahminhakki -= 1

                                            for ((index, harf) in tahminedilenkelime.withIndex()) {

                                                if (girilenkelime2.contains(harf)) {
                                                    if (girilenkelime2[index] == harf) {

                                                        harfler[index].background.setTint(Color.GREEN)
                                                    } else {

                                                        harfler[index].background.setTint(Color.YELLOW)
                                                    }
                                                } else {

                                                    harfler[index].background.setTint(Color.GRAY)
                                                }
                                            }
                                        }

                                        val harflerikincisatır =
                                            arrayOf(harf7, harf8, harf9, harf10, harf11, harf12)
                                        //ikinci satır için işlemler
                                        val girilenHarf7 = harf7.text.toString()
                                        val girilenHarf8 = harf8.text.toString()
                                        val girilenHarf9 = harf9.text.toString()
                                        val girilenHarf10 = harf10.text.toString()
                                        val girilenHarf11 = harf11.text.toString()
                                        val girilenHarf12 = harf12.text.toString()
                                        val tahminedilenkelimeikincisatir = StringBuilder()
                                            .append(girilenHarf7)
                                            .append(girilenHarf8)
                                            .append(girilenHarf9)
                                            .append(girilenHarf10)
                                            .append(girilenHarf11)
                                            .append(girilenHarf12)
                                            .toString()

                                        if (tahminedilenkelimeikincisatir == girilenkelime2) {

                                            println("tahminedilen kelime dogru")
                                            tahminkontrol = true
                                            Toast.makeText(
                                                textViewCountdown.context,
                                                "Tebrikler, doğru kelimeyi buldunuz!",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()

                                        } else {


                                            tahminhakki -= 1

                                            for ((index, harf) in tahminedilenkelimeikincisatir.withIndex()) {

                                                if (girilenkelime2.contains(harf)) {
                                                    if (girilenkelime2[index] == harf) {

                                                        harflerikincisatır[index].background.setTint(
                                                            Color.GREEN
                                                        )
                                                    } else {

                                                        harflerikincisatır[index].background.setTint(
                                                            Color.YELLOW
                                                        )
                                                    }
                                                } else {

                                                    harflerikincisatır[index].background.setTint(
                                                        Color.GRAY
                                                    )
                                                }
                                            }
                                        }

                                        val harflerucuncusatır =
                                            arrayOf(harf13, harf14, harf15, harf16, harf17, harf18)
                                        //3.satır icin islemler
                                        val girilenHarf13 = harf13.text.toString()
                                        val girilenHarf14 = harf14.text.toString()
                                        val girilenHarf15 = harf15.text.toString()
                                        val girilenHarf16 = harf16.text.toString()
                                        val girilenHarf17 = harf17.text.toString()
                                        val girilenHarf18 = harf18.text.toString()
                                        val tahminedilenkelimeucuncusatir = StringBuilder()
                                            .append(girilenHarf13)
                                            .append(girilenHarf14)
                                            .append(girilenHarf15)
                                            .append(girilenHarf16)
                                            .append(girilenHarf17)
                                            .append(girilenHarf18)
                                            .toString()

                                        if (tahminedilenkelimeikincisatir == girilenkelime2) {

                                            println("tahminedilen kelime dogru")
                                            tahminkontrol = true
                                            Toast.makeText(
                                                textViewCountdown.context,
                                                "Tebrikler, doğru kelimeyi buldunuz!",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()

                                        } else {

                                            tahminhakki -= 1

                                            for ((index, harf) in tahminedilenkelimeucuncusatir.withIndex()) {

                                                if (girilenkelime2.contains(harf)) {
                                                    if (girilenkelime2[index] == harf) {

                                                        harflerucuncusatır[index].background.setTint(
                                                            Color.GREEN
                                                        )
                                                    } else {

                                                        harflerucuncusatır[index].background.setTint(
                                                            Color.YELLOW
                                                        )
                                                    }
                                                } else {

                                                    harflerucuncusatır[index].background.setTint(
                                                        Color.GRAY
                                                    )
                                                }
                                            }
                                        }

                                        val harflerdorduncusatır =
                                            arrayOf(harf19, harf20, harf21, harf22, harf23, harf24)
                                        //4.satır icin islemler
                                        val girilenHarf19 = harf19.text.toString()
                                        val girilenHarf20 = harf20.text.toString()
                                        val girilenHarf21 = harf21.text.toString()
                                        val girilenHarf22 = harf22.text.toString()
                                        val girilenHarf23 = harf23.text.toString()
                                        val girilenHarf24 = harf24.text.toString()
                                        val tahminedilenkelimedorduncusatir = StringBuilder()
                                            .append(girilenHarf19)
                                            .append(girilenHarf20)
                                            .append(girilenHarf21)
                                            .append(girilenHarf22)
                                            .append(girilenHarf23)
                                            .append(girilenHarf24)
                                            .toString()

                                        if (tahminedilenkelimedorduncusatir == girilenkelime2) {

                                            println("tahminedilen kelime dogru")
                                            tahminkontrol = true
                                            Toast.makeText(
                                                textViewCountdown.context,
                                                "Tebrikler, doğru kelimeyi buldunuz!",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()

                                        } else {

                                            tahminhakki -= 1

                                            for ((index, harf) in tahminedilenkelimedorduncusatir.withIndex()) {

                                                if (girilenkelime2.contains(harf)) {
                                                    if (girilenkelime2[index] == harf) {

                                                        harflerdorduncusatır[index].background.setTint(
                                                            Color.GREEN
                                                        )
                                                    } else {

                                                        harflerdorduncusatır[index].background.setTint(
                                                            Color.YELLOW
                                                        )
                                                    }
                                                } else {

                                                    harflerdorduncusatır[index].background.setTint(
                                                        Color.GRAY
                                                    )
                                                }
                                            }
                                        }

                                        val harflerbesincisatır =
                                            arrayOf(harf25, harf26, harf27, harf28, harf29, harf30)
                                        //5.satır icin islemler
                                        val girilenHarf25 = harf25.text.toString()
                                        val girilenHarf26 = harf26.text.toString()
                                        val girilenHarf27 = harf27.text.toString()
                                        val girilenHarf28 = harf28.text.toString()
                                        val girilenHarf29 = harf29.text.toString()
                                        val girilenHarf30 = harf30.text.toString()
                                        val tahminedilenkelimebesincisatir = StringBuilder()
                                            .append(girilenHarf25)
                                            .append(girilenHarf26)
                                            .append(girilenHarf27)
                                            .append(girilenHarf28)
                                            .append(girilenHarf29)
                                            .append(girilenHarf30)
                                            .toString()

                                        if (tahminedilenkelimebesincisatir == girilenkelime2) {

                                            println("tahminedilen kelime dogru")
                                            tahminkontrol = true
                                            Toast.makeText(
                                                textViewCountdown.context,
                                                "Tebrikler, doğru kelimeyi buldunuz!",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()

                                        } else {

                                            tahminhakki -= 1

                                            for ((index, harf) in tahminedilenkelimebesincisatir.withIndex()) {

                                                if (girilenkelime2.contains(harf)) {
                                                    if (girilenkelime2[index] == harf) {

                                                        harflerbesincisatır[index].background.setTint(
                                                            Color.GREEN
                                                        )
                                                    } else {

                                                        harflerbesincisatır[index].background.setTint(
                                                            Color.YELLOW
                                                        )
                                                    }
                                                } else {

                                                    harflerbesincisatır[index].background.setTint(
                                                        Color.GRAY
                                                    )
                                                }
                                            }
                                        }

                                       val harfleraltincisatır=arrayOf(harf31, harf32, harf33, harf34, harf35, harf36)
                                        //6.satır icin islemler
                                        val girilenHarf31 = harf31.text.toString()
                                        val girilenHarf32 = harf32.text.toString()
                                        val girilenHarf33= harf33.text.toString()
                                        val girilenHarf34 = harf34.text.toString()
                                        val girilenHarf35 = harf35.text.toString()
                                        val girilenHarf36= harf36.text.toString()
                                        val tahminedilenkelimealtincisatir = StringBuilder()
                                            .append(girilenHarf31)
                                            .append(girilenHarf32)
                                            .append(girilenHarf33)
                                            .append(girilenHarf34)
                                            .append(girilenHarf35)
                                            .append(girilenHarf36)
                                            .toString()

                                        if (tahminedilenkelimealtincisatir == girilenkelime2) {

                                            println("tahminedilen kelime dogru")
                                            tahminkontrol=true
                                            Toast.makeText(textViewCountdown.context, "Tebrikler, doğru kelimeyi buldunuz!", Toast.LENGTH_SHORT)
                                                .show()

                                        } else {


                                            tahminhakki-=1

                                            for ((index, harf) in tahminedilenkelimealtincisatir.withIndex()) {

                                                if (girilenkelime2.contains(harf)) {
                                                    if (girilenkelime2[index] == harf) {

                                                        harfleraltincisatır[index].background.setTint(Color.GREEN)
                                                        oyunskor2+=10
                                                    } else {

                                                        harfleraltincisatır[index].background.setTint(Color.YELLOW)
                                                        oyunskor2+=5
                                                    }
                                                } else {


                                                    harfleraltincisatır[index].background.setTint(Color.GRAY)
                                                }
                                            }
                                            oyunskor2=oyunskor2+kalansuree2.toInt()
                                            println("Toplam Puan sender: $oyunskor2")
                                            Toast.makeText(textViewCountdown.context, "OYUN PUANI:$oyunskor2", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                   //diger oyuncuya kalan her tahmin hakki icin 10sn verilecek
                                  /*  if(tahminhakki==0 && !tahminkontrol){

                                    }*/



                                       if(!tahminkontrol && !tahminkontrol2){
                                            if(oyunskor>oyunskor2){
                                                Toast.makeText(textViewCountdown.context, "senderId oyuncusu oyunu kazandı!", Toast.LENGTH_SHORT)
                                                    .show()
                                            }
                                            else if (oyunskor2>oyunskor){
                                                Toast.makeText(textViewCountdown.context, "receiverId oyuncusu oyunu kazandı!", Toast.LENGTH_SHORT)
                                                    .show()
                                            }
                                            else if(oyunskor==oyunskor2){
                                                Toast.makeText(textViewCountdown.context, "oyun berabere bitti!", Toast.LENGTH_SHORT)
                                                    .show()
                                            }
                                        }
                                    }
                                }
                            }/* else {


                                Toast.makeText(
                                    textViewCountdown.context,
                                    "Bu işlemi gerçekleştirmek için yetkiniz bulunmamaktadır sender.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }*/
                        }


            }
            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun startCountdown(textViewCountdown: TextView) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                val secondsRemaining = millisUntilFinished / 1000

            }

            override fun onFinish() {

                if (!isButtonClicked) {

                    Toast.makeText(applicationContext, "deneme yapmalısınız!!", Toast.LENGTH_SHORT).show()
                    startShortCountdown(textViewCountdown)
                }
            }
        }.start()
    }

    private fun startShortCountdown(textViewCountdown: TextView) {
        shortCountDownTimer?.cancel()
        // 10 saniyelik geri sayım başlat
        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                val secondsRemaining = millisUntilFinished / 1000
                textViewCountdown.text = "Kalan süre: $secondsRemaining saniye"
            }

            override fun onFinish() {
                // 10 saniyelik geri sayım tamamlandığında ek işlemler yapılabilir
                if(textViewCountdown.visibility==View.GONE){

                }
                else{
                    Toast.makeText(applicationContext, "Süre doldu, mağlup sayıldınız!", Toast.LENGTH_SHORT).show()
                }

            }
        }.start()
    }
    private fun mesajDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Oyundan Çıkma Uyarısı")
        builder.setMessage("Oyundan çıkmanız halinde oyunu kaybedeceksiniz. Çıkmak istiyor musunuz?")
        builder.setPositiveButton("Onayla") { dialog, which ->
            // Kullanıcı onayladı, oyunu bitir ve çıkış işlemlerini gerçekleştir
           finishGame()
        }
        builder.setNegativeButton("Red") { dialog, which ->

            dialog.dismiss()
        }
        builder.show()
    }
    private fun finishGame() {

        finish()
    }

}