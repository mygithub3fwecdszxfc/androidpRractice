package com.example.androidstudydemo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityFirstBinding
import es.dmoral.toasty.Toasty
import java.io.Serializable

class FirstActivity : AppCompatActivity() {
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val result = data?.getStringExtra("result_key")?:""
            val result2 = data?.getStringExtra("result_key2")?:""
            Toasty.success(this, "1111111111111111111111111111111111111111111111111111111111111111111111Result: $result, $result2", Toasty.LENGTH_LONG).show()
        }
    }
    private lateinit var binding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun onJumpClick(view: View) {
        val intent=Intent(this,SecondActivity::class.java).apply{
            putExtra("key", "value")
            putExtra("username","android")
            putExtra("password","123456")
        }
        startActivity(intent)

    }

    fun onJumpWithDataClick(view: View) {
        val person1 = Person1("android11111111111111111", 18852588, "m111ale")
        val person2 = Person1("android111111111111111", 1888, "m111111ntale")
        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra("person1", person1)
            putExtra("person2", person2)
        }
        startActivity(intent)
    }

    fun jump(view: View) {
        val intent =Intent(this, SecondActivity::class.java)
        startActivityForResult(intent, 100)
    }
    //接收回传数据
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val result = data?.getStringExtra("result_key")?:""
            val result2 = data?.getStringExtra("result_key2")?:""
           Toasty.success(this, "Result: $result, $result2", Toasty.LENGTH_LONG).show()
        }
    }

    fun jump1(view: View) {
        val intent = Intent(this, SecondActivity::class.java)
        resultLauncher.launch(intent)
    }
}

data class Person(val name: String, val age: Int,val sex:String): Serializable
data class Person1(val name: String, val age: Int, val sex: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readInt(),
        parcel.readString().orEmpty()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeString(sex)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Person1> {
        override fun createFromParcel(parcel: Parcel): Person1 = Person1(parcel)

        override fun newArray(size: Int): Array<Person1?> = arrayOfNulls(size)
    }
}
