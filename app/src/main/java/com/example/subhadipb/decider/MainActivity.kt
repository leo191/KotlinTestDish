package com.example.subhadipb.decider

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Thread.sleep
import java.util.*
import android.view.animation.AnimationUtils
import android.view.animation.Animation
import android.widget.ArrayAdapter
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot




class MainActivity : AppCompatActivity() {

    val database:FirebaseDatabase = FirebaseDatabase.getInstance()
    var listfoods = arrayListOf<String>()
    var myad : ArrayAdapter<String> ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        myad = ArrayAdapter(applicationContext,android.R.layout.simple_list_item_1,listfoods)
        addbtn.setOnClickListener{

            if(dishEntry.text.toString() != "") {
                var myref: DatabaseReference = database.getReference("Orders")
                myref.push().setValue(dishEntry.text.toString())

                //if (!dishEntry.text.equals("")) {
                listfoods.add(dishEntry.text.toString())
                dishEntry.setText("")


              /*  myref.addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(dataSnapshot: DataSnapshot, s: String) {

                        // Get the value from the DataSnapshot and add it to the teachers' list
                        var dish = dataSnapshot.getValue(String::class.java)


                        // Notify the ArrayAdapter that there was a change
                        myad!!.notifyDataSetChanged()
                    }

                    override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {

                    }

                    override fun onChildRemoved(dataSnapshot: DataSnapshot) {

                    }

                    override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {

                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })*/




            }
           //}
        }


        decidebtn.setOnClickListener{


            val startRotateAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.android_rotate_animation)
            dishImage.startAnimation(startRotateAnimation)
            Changer().execute()
            println(listfoods.toString())

        }
    }





    //UI And Random Selection

    inner class Changer: AsyncTask<Void,Int,Int>(){
        override fun onProgressUpdate(vararg values: Int?) {
            var i = values.get(0)!!
            dishNametxt.setText(listfoods[i])

        }

        override fun doInBackground(vararg p0: Void?): Int {
            var t = listfoods.count()
            var p:Int=0
           if(t!=0) {
               while (t != 0) {
                   p = Random().nextInt(listfoods.count())
                   publishProgress(p)
                   sleep(100)
                   t--
               }
           }
            return p
        }

        override fun onPostExecute(result: Int?) {


            if(result != 0) dishNametxt.setText(listfoods[Random().nextInt(listfoods.count())]) else println("problem")
        }

    }



}

