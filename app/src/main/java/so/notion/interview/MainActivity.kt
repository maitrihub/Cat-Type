package so.notion.interview

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import so.notion.interview.data.api.RetrofitBuilder
import so.notion.interview.data.model.CatResponse
import so.notion.interview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val disposable = CompositeDisposable()
    private lateinit var searchField: EditText
    private lateinit var searchButton: Button
    private lateinit var catName: TextView
    private lateinit var catOrigin: TextView
    private lateinit var catDescription: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchField = findViewById(R.id.search_field)
        searchButton = findViewById(R.id.search_button)
        catName = findViewById(R.id.cat_name)
        catOrigin = findViewById(R.id.cat_origin)
        catDescription = findViewById(R.id.cat_description)

        searchButton.setOnClickListener {
            val query = searchField.text.toString()
            fetchBreed(query)
        }
    }

    // based on the query return the list of breed by calling endpoint
    fun fetchBreed(query: String) {
        Log.d("Testing", "fetchBreed reached")
        if (query.isBlank()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_LONG).show()
        } else {
            disposable.add(

                RetrofitBuilder.apiService.getBreeds(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ resultList: List<CatResponse> ->
                        if (resultList.isNotEmpty()) {
                            catName.text = resultList[0].name
                            catOrigin.text = resultList[0].origin
                            catDescription.text = resultList[0].description

                        } else {
                            Toast.makeText(this, "No Results Found", Toast.LENGTH_LONG).show()
                        }
                    }, { error ->
                        Log.d("Testing", "Error$error")
                        Toast.makeText(this, "error: $error", Toast.LENGTH_LONG).show()
                    })
            )
        }

    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }
}
