package com.aylinexample.yazlabproje

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject

class Direk6Kanal : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Oyuncu>
    private lateinit var myAdapter: MyAdapter
    private lateinit var db:FirebaseFirestore

   // private lateinit var auth: FirebaseAuth
    //var databaseReference: DatabaseReference?=null
    //var database: FirebaseDatabase?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_direk6_kanal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView=findViewById(R.id.recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        userArrayList= arrayListOf()
        myAdapter= MyAdapter(userArrayList)
        recyclerView.adapter=myAdapter

       /* auth = FirebaseAuth.getInstance()
        databaseReference=database?.reference!!.child("bilgi")

        var currentuser=  auth.currentUser
        var currentUserDb=currentuser?.let { it1 -> databaseReference?.child(it1.uid) }*/





        val documentId = intent.getStringExtra("documentId")
        val girisyapankullanici = intent.getStringExtra("girisyapankullanici")
        if (documentId != null) {
            if (girisyapankullanici != null) {
                oyunodasioyuncularilistele(documentId,girisyapankullanici)


            }



        }

        myAdapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

                val clickedItem: Oyuncu = userArrayList[position]
              //  println("buton tıklama basarili")

                val aliciId = clickedItem.kullaniciadi.toString()
                val requestMessage = "oyun isteği"
                if (girisyapankullanici != null) {
                    istekgondermefonksiyon(girisyapankullanici, aliciId, requestMessage)
///sayac1kısımıı(burası gonderen yerdeki sayaci calıstırıyo)

                    val requestsRef = db.collection("requests")
                    requestsRef.whereEqualTo("senderId", girisyapankullanici)
                        .get().addOnSuccessListener { documents ->
                            if (documents.isEmpty) {

                            } else {

                                val textgerisayim: TextView = findViewById(R.id.textgerisayim)
                                for (document in documents) {
                                    val documentId = document.id
                                    val countDownTimer = Zamanlayici(10000, textgerisayim,documentId)
                                    countDownTimer.start()
                                   // girisyapanoyunda(girisyapankullanici)



                                }


                            }

                        }

                   // val textgerisayim: TextView = findViewById(R.id.textgerisayim)

                   // val countDownTimer = MyCountDownTimer(10000, textgerisayim) // 10 saniye
                    //countDownTimer.start()
                }

            }

            override fun onItemClick1(position: Int) {
              //  val oyunOdasiRef = db.collection("oyunOdaları").document("oyunOdasi_kelime6")
               // val kullaniciRef = oyunOdasiRef.collection("oyuncular")
               if(girisyapankullanici!=null){

                   istekalmafonksiyon(girisyapankullanici)

                   oyundaguncelleme(position)
                   val requestsRef = db.collection("requests")
                   requestsRef.whereEqualTo("receiverId", girisyapankullanici)
                       .get().addOnSuccessListener { documents ->
                           if (!documents.isEmpty) {
                               for (document in documents) {
                                   val senderId = document.getString("senderId")
                                   if (senderId != null) {

                                       val position2 = userArrayList.indexOfFirst { it.kullaniciadi == senderId }
                                       if (position2 != -1) {
                                           oyundaguncelleme(position2)
                                       }

                                   }
                               }
                           }

                       }







               }




            }



            override fun onItemClick2(position: Int) {

                if(girisyapankullanici!=null){
                    istekalmafonksiyon2(girisyapankullanici)
                }
            }


        })



    }

    private fun oyundaguncelleme(position: Int) {
        val player: Oyuncu = userArrayList[position]
        player.aktiflikdurum = "oyunda"
        myAdapter.notifyItemChanged(position)
    }
    private var istekgondermedurumu = false
    private  fun istekgondermefonksiyon(senderId: String, receiverId: String, requestMessage: String) {
        if (istekgondermedurumu) {

            println("Zaten bir istek gönderimi işlemi devam ediyor.")

        }
        val requestsRef = db.collection("requests")
        // İstek belgesi oluşturulması
        val requestData = hashMapOf(
            "senderId" to senderId,
            "receiverId" to receiverId,
            "requestMessage" to requestMessage,
            "status" to "beklemede"
        )
        requestsRef.add(requestData)
            .addOnSuccessListener { documentReference ->
                // İstek başarıyla gönderildiğinde yapılacak işlemler
                istekgondermedurumu=true
                dinleAlinanIstekler(receiverId)

            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error sending request: $e")
            }


    }
    //aynı kanalda bulunan kullanıcıları arayuze gonderme kodu
    private fun  oyunodasioyuncularilistele(documentId:String,girisyapankullanici:String){

        db= FirebaseFirestore.getInstance()
        db.collection("oyunOdaları").document(documentId).collection("oyuncular").addSnapshotListener(object:EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error!=null){
                    Log.e("firestore error",error.message.toString())
                    return
                }
                for(dc:DocumentChange in value?.documentChanges!!){
                    if(dc.type== DocumentChange.Type.ADDED){

                        val kullaniciAdi = dc.document.getString("kullaniciadi")
                        val aktiflikDurumu = "aktif"

                        kullaniciAdi?.let {
                            if (!userArrayList.any { oyuncu -> oyuncu.kullaniciadi == it }) {

                                // userArrayList.add(dc.document.toObject(Oyuncu::class.java))
                                val oyuncu = dc.document.toObject(Oyuncu::class.java)
                                oyuncu.aktiflikdurum = aktiflikDurumu
                                userArrayList.add(oyuncu)
                            }

                            //ikincisayackımsı

                            val requestsRef = db.collection("requests")
                            requestsRef.whereEqualTo("receiverId", kullaniciAdi)
                                .get().addOnSuccessListener { documents ->
                                    if (documents.isEmpty) {

                                    } else {
                                        // Belge bulundu, kullanıcı adı zaten mevcut
                                        val textgerisayim: TextView = findViewById(R.id.textgerisayim)
                                        for (document in documents) {
                                            val documentId = document.id
                                            val countDownTimer = Zamanlayici(10000, textgerisayim,documentId) // 10 saniye
                                            countDownTimer.start()



                                        }


                                    }

                                }

                        }

                    }
                }
                myAdapter.notifyDataSetChanged()

            }


        })
    }

    fun dinleAlinanIstekler(aliciId: String) {
        val requestsRef = db.collection("requests")
        requestsRef.whereEqualTo("receiverId", aliciId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("Firestore", "Error listening to incoming requests: $e")
                    return@addSnapshotListener
                }

                // Gelen isteklerin işlenmesi
                if (snapshot != null && !snapshot.isEmpty) {
                    for (doc in snapshot.documents) {
                        val senderId = doc.getString("senderId")


                        dinleoyunda(senderId.toString())
                        dinlereddet(senderId.toString())

                        //dinlesayac(senderId.toString())
                        //dinlesayac2(receiverrr.toString())



                    }
                }
            }
    }
fun dinlereddet(kullaniiciId: String){
    val requestsRef = db.collection("requests")
    requestsRef.whereEqualTo("senderId",kullaniiciId).whereEqualTo("status","reddedildi")
        .get().addOnSuccessListener { documents ->
            if (!documents.isEmpty) {

                 Toast.makeText(this, "oyuncu isteği reddetti", Toast.LENGTH_SHORT).show()



            }

        }

}
   /* fun dinlesayac(kullaniiciId:String){
        val requestsRef = db.collection("requests")
        requestsRef.whereEqualTo("senderId",kullaniiciId)
            .get().addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                            val textgerisayim: TextView = findViewById(R.id.textgerisayim)
                            val documentId = document.id
                            val countDownTimer = Zamanlayici(10000, textgerisayim,documentId) // 10 saniye
                            countDownTimer.start()



                    }
                }

            }
    }
    fun dinlesayac2(kullaniiciId:String){
        val requestsRef = db.collection("requests")
        requestsRef.whereEqualTo("receiverId",kullaniiciId)
            .get().addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        val textgerisayim: TextView = findViewById(R.id.textgerisayim)
                        val documentId = document.id
                        val countDownTimer = Zamanlayici(10000, textgerisayim,documentId) // 10 saniye
                        countDownTimer.start()



                    }
                }

            }
    }*/


    fun dinleoyunda(kullaniiciId:String){
        val requestsRef = db.collection("requests")
        requestsRef.whereEqualTo("senderId",kullaniiciId).whereEqualTo("status","kabul edildi")
            .get().addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        val senderId = document.getString("senderId")
                        val alankullaniciId=document.getString("receiverId")

                        if (senderId != null) {

                            val position2 = userArrayList.indexOfFirst { it.kullaniciadi == senderId }
                            if (position2 != -1) {
                                oyundaguncelleme(position2)
                            }
                        }

                        if (alankullaniciId != null) {

                            val position2 = userArrayList.indexOfFirst { it.kullaniciadi == alankullaniciId }
                            if (position2 != -1) {
                                oyundaguncelleme(position2)
                            }
                        }

                        //istegi gonderen kisiyi(arda),(sender) oyun sayfasına yonlerdirir
                        val intent = Intent(this, Direk6Oyun::class.java)
                        intent.putExtra("senderId",senderId)
                       // intent.putExtra("receiverId",alankullaniciId)
                        startActivity(intent)

                    }
                }

            }
    }
    // İstek alma fonksiyonu
    fun istekalmafonksiyon(userId: String) {


        val requestsRef = db.collection("requests")
        requestsRef.whereEqualTo("receiverId",userId)
            .whereEqualTo("status", "beklemede")
            .get().addOnSuccessListener { documents ->

            if (documents.isEmpty) {

            } else {


                for (document in documents) {
                    val documentId = document.id
                     istekabulet(documentId,userId)



                }

            }
        }



    }

    // İstek alma-red fonksiyonu
    fun istekalmafonksiyon2(userId: String) {

        val requestsRef = db.collection("requests")
        requestsRef.whereEqualTo("receiverId", userId)
            .whereEqualTo("status", "beklemede").get().addOnSuccessListener { documents ->
                if (documents.isEmpty) {

                } else {

                    println("red icin dogru yer")
                    for (document in documents) {
                        val documentId = document.id
                        istekreddet(documentId)


                    }

                }
            }

    }
    // İstek kabul etme fonksiyonu
    fun istekabulet(requestId: String, receiverId:String) {
        val requestRef = db.collection("requests").document(requestId)
        requestRef.update("status", "kabul edildi")
            .addOnSuccessListener {

                Log.d("Firestore", "Request accepted")
                //istegi alan kisiyi oyun sayfasına yonlerdirir
                val intent = Intent(this, Direk6Oyun::class.java)
                intent.putExtra("receiverId2",receiverId)
                startActivity(intent)

            }
            .addOnFailureListener { e ->
                // İstek kabul edilemedi
                Log.e("Firestore", "Error accepting request: $e")
            }
    }
    // İstek reddetme fonksiyonu

    fun istekreddet(requestId: String) {
        val requestRef = db.collection("requests").document(requestId)
        // İsteği reddetmek için isteğin durumunu güncelle
        requestRef.update("status", "reddedildi")
            .addOnSuccessListener {
                Toast.makeText(this, "oyuncu isteği reddetti", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e ->


            }
    }
    // MyCountDownTimer sınıfını buraya ekleyebilirsiniz
    inner class Zamanlayici(private val totalTime: Long, private val textViewCountdown: TextView,private val requestId: String) : CountDownTimer(totalTime, 1000) {

        override fun onTick(millisUntilFinished: Long) {

            val secondsRemaining = millisUntilFinished / 1000
            val timeLeftFormatted = String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60)

            textViewCountdown.text = timeLeftFormatted
        }

        override fun onFinish() {
            // Geri sayım tamamlandığında yapılacak işlemler************************


            val requestsRef = db.collection("requests").document(requestId)
            requestsRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    val requestStatus = documentSnapshot.getString("status")
                    if (requestStatus == "beklemede") {
                        Toast.makeText(textViewCountdown.context, "Oyuncu isteği kabul etmedi", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error getting request status: $e")
                }
            


        }

    }







}


