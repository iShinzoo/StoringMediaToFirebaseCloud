package com.example.storingmediatofirebasecloud

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var BtnSelectImage : Button
    private lateinit var BtnUploadImage : Button
    private lateinit var imagePreview : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BtnSelectImage = findViewById(R.id.BtnSelectImage)
        BtnUploadImage = findViewById(R.id.BtnUploadImage)
        imagePreview = findViewById(R.id.image)

        BtnSelectImage.setOnClickListener(){
            val GalleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(GalleryIntent,101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK){
            imagePreview.setImageURI(data?.data)
        }

        BtnUploadImage.setOnClickListener(){
            uploadImage(data?.data)
        }
    }

    private fun uploadImage(imageUri : Uri?){
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val storageRefrence = FirebaseStorage.getInstance().reference.child("image/$fileName")
        storageRefrence.putFile(imageUri!!).addOnSuccessListener {
            Toast.makeText(this, "Image uploaded Successfully", Toast.LENGTH_SHORT).show()
        }
    }
}