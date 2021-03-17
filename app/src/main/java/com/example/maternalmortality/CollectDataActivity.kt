package com.example.maternalmortality

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.maternalmortality.models.ANMUser
import com.example.maternalmortality.models.AshaUser
import com.example.maternalmortality.models.PatientDetails
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_collect_data.*
import kotlinx.coroutines.*
import org.jetbrains.anko.Android
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

import kotlin.concurrent.schedule

class CollectDataActivity : AppCompatActivity() {

    var profileImageUrlText = ""
    var selectedPhotoUri: Uri? = null

    private lateinit var selectphoto_imageview_register: ImageView
    private lateinit var btCapturePhoto: Button
    private var photoFile: File? = null
    private val CAPTURE_IMAGE_REQUEST = 1
    private lateinit var mCurrentPhotoPath: String
    private val IMAGE_DIRECTORY_NAME = "dep"

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was....
            Log.d("Collect data activity","photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

//            selectphoto_imageview_register.setImageBitmap(bitmap)

            if(selectedPhotoUri!=null){
                Picasso.with(this).load(selectedPhotoUri).fit().centerInside().rotate(90F).into(selectphoto_imageview_register);
            }

//            selectphoto_button_register.alpha = 0f
//            selectphoto_imageview_register.setBackgroundColor(Color.parseColor("#ffffff"))

//      val bitmapDrawable = BitmapDrawable(bitmap)
//      selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
        }

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val myBitmap = BitmapFactory.decodeFile(photoFile!!.getAbsolutePath())
//            selectphoto_imageview_register.setImageBitmap(myBitmap)
            if(selectedPhotoUri!=null){
                Picasso.with(this).load(selectedPhotoUri).fit().centerInside().rotate(90F).into(selectphoto_imageview_register);
            }
            selectphoto_imageview_register.setBackgroundColor(Color.parseColor("#ffffff"))
        } else {
            displayMessage(baseContext, "Request cancelled or something went wrong.")
        }

        uploadImageToFirebaseStorage();
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("collect data activity", "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("TAG", "File Location: $it")
                    profileImageUrlText = it.toString()
                    Log.d("TAG", "File url: $profileImageUrlText")
//                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d("TAG", "Failed to upload image to storage: ${it.message}")
            }
    }

    var isBeingUpdated = false
    var previousDetails: PatientDetails? = null
    var previousId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect_data)

        // select image
        selectphoto_button_register.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        // camera
        selectphoto_imageview_register = findViewById(R.id.selectphoto_imageview_register)
        btCapturePhoto = findViewById(R.id.btCapturePhoto)
        btCapturePhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                captureImage()
            } else {
                captureImage2()
            }
        }

        /// date picker start
        val mPickTimeBtn = findViewById<Button>(R.id.pickDateBtn) // date
        val mPickTimeBtn1 = findViewById<Button>(R.id.pickDateBtn1) // lmp
        val mPickTimeBtn2 = findViewById<Button>(R.id.pickDateBtn2) // edod
        val mPickTimeBtn3= findViewById<Button>(R.id.pickDateBtn3) // tetanus1
        val mPickTimeBtn4 = findViewById<Button>(R.id.pickDateBtn4) // tetanus2

        val date: TextInputLayout = findViewById(R.id.two)
        val lmp: TextInputLayout = findViewById(R.id.nine)
        val edod: TextInputLayout = findViewById(R.id.ten)
        val tetanus1: TextInputLayout = findViewById(R.id.eleven)
        val tetanus2: TextInputLayout = findViewById(R.id.twelve)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        mPickTimeBtn.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                date.getEditText()?.setText("" + dayOfMonth + " / " + month + " / " + year)
            }, year, month, day)
            dpd.show()
        }
        mPickTimeBtn1.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                lmp.getEditText()?.setText("" + dayOfMonth + " / " + month + " / " + year)
            }, year, month, day)
            dpd.show()
        }
        mPickTimeBtn2.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                edod.getEditText()?.setText("" + dayOfMonth + " / " + month + " / " + year)
            }, year, month, day)
            dpd.show()
        }
        mPickTimeBtn3.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                tetanus1.getEditText()?.setText("" + dayOfMonth + " / " + month + " / " + year)
            }, year, month, day)
            dpd.show()
        }
        mPickTimeBtn4.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                tetanus2.getEditText()?.setText("" + dayOfMonth + " / " + month + " / " + year)
            }, year, month, day)
            dpd.show()
        }
        //////end

        if(intent.hasExtra("previous details")){
            isBeingUpdated = true
            previousDetails = intent.extras?.get("previous details") as PatientDetails
            previousId = intent.extras?.get("Id") as String
        }

        val name: TextInputLayout = findViewById(R.id.one)

        val age: TextInputLayout = findViewById(R.id.three)
        val village: TextInputLayout = findViewById(R.id.four)
        val religion: TextInputLayout = findViewById(R.id.five)
        val caste: TextInputLayout = findViewById(R.id.six)
        val phone: TextInputLayout = findViewById(R.id.seven)
        val lpara: TextInputLayout = findViewById(R.id.eight)

        val anc: TextInputLayout = findViewById(R.id.thirteen)
        val heartbeat: TextInputLayout = findViewById(R.id.fourteen)
        val bp: TextInputLayout = findViewById(R.id.fifteen)

        name.editText?.setText(previousDetails?.name)
        date.editText?.setText(previousDetails?.date)
        age.editText?.setText(previousDetails?.age)
        village.editText?.setText(previousDetails?.village)
        religion.editText?.setText(previousDetails?.religion)
        caste.editText?.setText(previousDetails?.caste)
        phone.editText?.setText(previousDetails?.phone)
        lpara.editText?.setText(previousDetails?.lpara)
        lmp.editText?.setText(previousDetails?.lmp)
        edod.editText?.setText(previousDetails?.edod)
        tetanus1.editText?.setText(previousDetails?.tetanus1)
        tetanus2.editText?.setText(previousDetails?.tetanus2)
        anc.editText?.setText(previousDetails?.anc)
        heartbeat.editText?.setText(previousDetails?.heartbeat)
        bp.editText?.setText(previousDetails?.bp)

        val saveButton: Button =findViewById(R.id.saveButton)

        if(isBeingUpdated){
            saveButton.text = "UPDATE DATA"
        }

        val auth = FirebaseAuth.getInstance()

        saveButton.setOnClickListener {
            var nameText = name.editText?.text.toString()
            var dateText = date.editText?.text.toString()
            var ageText = age.editText?.text.toString()
            var villageText = village.editText?.text.toString()
            var religionText = religion.editText?.text.toString()
            var casteText = caste.editText?.text.toString()
            var phoneText = phone.editText?.text.toString()
            var lparaText = lpara.editText?.text.toString()
            var lmpText = lmp.editText?.text.toString()
            var edodText = edod.editText?.text.toString()
            var tetanus1Text = tetanus1.editText?.text.toString()
            var tetanus2Text = tetanus2.editText?.text.toString()
            var ancText = anc.editText?.text.toString()
            var heartbeatText = heartbeat.editText?.text.toString()
            var bpText = bp.editText?.text.toString()


            if (nameText.isEmpty()) {
                name.setError("Required Field")
                return@setOnClickListener
            }
            if (ageText.isEmpty()) {
                age.setError("Required Field")
                return@setOnClickListener
            }
            if (villageText.isEmpty()) {
                village.setError("Required Field")
                return@setOnClickListener
            }
            if (phoneText.isEmpty()) {
                phone.setError("Required Field")
                return@setOnClickListener
            }
            if (dateText.isEmpty()) {
                date.setError("Required Field")
                return@setOnClickListener
            }

            Log.d("TAG", "File url 2: $profileImageUrlText")

            var anm_supervisor_email = ""
            var asha_supervisor_email = ""

            val fireStore = FirebaseFirestore.getInstance()

            if (isBeingUpdated) {
                anm_supervisor_email = previousDetails?.anm_supervisior_email.toString()
                asha_supervisor_email = previousDetails?.asha_supervisor_email.toString()

                val patientDetails = previousId?.let { it1 ->
                    PatientDetails(
                        nameText,
                        dateText,
                        ageText,
                        villageText,
                        religionText,
                        casteText,
                        phoneText,
                        lparaText,
                        lmpText,
                        edodText,
                        tetanus1Text,
                        tetanus2Text,
                        ancText,
                        heartbeatText,
                        bpText,
                        auth.currentUser?.uid.toString(),
                        anm_supervisor_email,
                        asha_supervisor_email,
                        profileImageUrlText
                    )


                }
                if (patientDetails != null) {
                    fireStore.document(previousId!!).set(patientDetails)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                            }
                        }
                }

            } else {
                val auth = FirebaseAuth.getInstance()

                var list: MutableList<AshaUser> = mutableListOf()
                var list1: MutableList<ANMUser> = mutableListOf()

                //
                //Assigning the Asha worker with min patients currently and having same village as of patient
//            var details = list[0]
//            if(list.size>0)
//            {
//                var curr_count_asha = details.count.toInt()
//                asha_supervisor_email = details.email.toString()
//            }
//            print("size of list is ");
//            println(list.size);


                // Here we are updating the count of asha worker............
                // var Docref = FirebaseFirestore.getInstance().collection("AshaUser").document(details.mobile);
                //Docref.update("count" , curr_count_asha+1);


                //Assigning the ANM worker with min patients currently and having same village as of patient

                //
                println(villageText);
                fireStore.collection("ANMUser").whereEqualTo("village", villageText).orderBy("count", Query.Direction.ASCENDING).limit(1).get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        list1.add(document.toObject(ANMUser::class.java))
                    }
                    println("size of list1 is here is ..........");
                    println(list1.size);

                    println("Again printing.......");
                    var details1 = list1[0]
                    println(details1.email);
                    //var curr_count_ANM = details1.count.toInt()

                    anm_supervisor_email = details1.email


                    var query = fireStore.collection("AshaUser").whereEqualTo("village", villageText).orderBy("count", Query.Direction.ASCENDING).limit(1)
                    query.get().addOnSuccessListener { documents ->
                        for (document in documents) {
                            list.add(document.toObject(AshaUser::class.java))
                        }
                        var details = list[0]
                        asha_supervisor_email = details.email

                        var curr_count_anm = details1.count.toInt() + 1
                        var Docref1 = FirebaseFirestore.getInstance().collection("ANMUser").document(details1.mobile);
                        Docref1.update("count", curr_count_anm);


                        var curr_count_asha = details.count.toInt() + 1
                        var Docref = FirebaseFirestore.getInstance().collection("AshaUser").document(details.mobile);
                        Docref.update("count", curr_count_asha);


                        println(anm_supervisor_email);
                        println(asha_supervisor_email);
                        val patientDetails = PatientDetails(nameText, dateText, ageText, villageText, religionText, casteText, phoneText, lparaText, lmpText, edodText, tetanus1Text, tetanus2Text, ancText, heartbeatText, bpText, auth.currentUser?.uid.toString(), asha_supervisor_email, anm_supervisor_email,profileImageUrlText)
                        val firestore = FirebaseFirestore.getInstance().collection("PatientDetails")

                        firestore.document().set(patientDetails)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Successfully Saved", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                                }
                            }


                    }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }
                    // Toast.makeText(this, "$(list1.size)", Toast.LENGTH_LONG).show()
                }
                    .addOnFailureListener {
                        print("Error..............aa rhi hai")
                        //       Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    }

//            println("print ho ja ");
//            println("village of patient is $villageText   ");
//            println("size of list1 is");
//            println(list1.size);
                // println(anm_supervisor_email);
                //Here we are updating the count of ANM worker...............
                // var Docref1 = FirebaseFirestore.getInstance().collection("ANMUser").document(details1.mobile);
                //Docref1.update("count" , curr_count_ANM+1);


                //Now pushing the record of new patient to the collection.............
            }
        }
    }


    /* Capture Image function for 4.4.4 and lower. Not tested for Android Version 3 and 2 */
    private fun captureImage2() {

        try {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = createImageFile4()
            if (photoFile != null) {
                displayMessage(baseContext, photoFile!!.getAbsolutePath())
                Log.i("Aman", photoFile!!.getAbsolutePath())
                selectedPhotoUri = Uri.fromFile(photoFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedPhotoUri)
                startActivityForResult(cameraIntent, 1)
            }
        } catch (e: Exception) {
            displayMessage(baseContext, "Camera is not available." + e.toString())
        }

    }

    private fun captureImage() {

        if (ContextCompat.checkSelfPermission(this,  android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                // Create the File where the photo should go
                try {

                    photoFile = createImageFile()
                    displayMessage(baseContext, photoFile!!.getAbsolutePath())
                    Log.i("Aman", photoFile!!.getAbsolutePath())

                    // Continue only if the File was successfully created
                    if (photoFile != null) {


                        selectedPhotoUri = FileProvider.getUriForFile(this,
                            "com.example.maternalmortality",
                            photoFile!!
                        )

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedPhotoUri)

                        startActivityForResult(takePictureIntent, 1)

                    }
                } catch (ex: Exception) {
                    // Error occurred while creating the File
                    displayMessage(baseContext,"Capture Image Bug: "  + ex.message.toString())
                }

            } else {
                displayMessage(baseContext, "Nullll")
            }
        }
    }

    private fun createImageFile4(): File? {
        // External sdcard location
        val mediaStorageDir = File(
            Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            IMAGE_DIRECTORY_NAME)
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                displayMessage(baseContext, "Unable to create directory.")
                return null
            }
        }

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
            Locale.getDefault()).format(Date())

        return File(mediaStorageDir.path + File.separator
                + "IMG_" + timeStamp + ".jpg")

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    private fun displayMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 0) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureImage()
            }
        }

    }

}



