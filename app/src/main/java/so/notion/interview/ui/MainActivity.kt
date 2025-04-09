package so.notion.interview.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import so.notion.interview.R
import so.notion.interview.data.api.RetrofitBuilder
import so.notion.interview.data.model.CatResponse
import so.notion.interview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val disposable = CompositeDisposable()
    private lateinit var searchField: EditText
    private lateinit var searchButton: Button
    private lateinit var adapter: BreedAdapter
    private var breedList = mutableListOf<CatResponse>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.breedRecycler.layoutManager = LinearLayoutManager(this)
        adapter = BreedAdapter(breedList)
        binding.breedRecycler.adapter = adapter
        searchField = findViewById(R.id.search_field)
        searchButton = findViewById(R.id.search_button)
        searchButton.setOnClickListener {
            val query = searchField.text.toString()
            fetchBreed(query)
        }
    }
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
                            breedList.clear()
                            breedList.addAll(resultList)
                            adapter.notifyDataSetChanged()

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
