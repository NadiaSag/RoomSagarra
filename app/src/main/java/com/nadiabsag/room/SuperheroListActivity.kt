package com.nadiabsag.room
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.nadiabsag.database.SuperheroDatabase
import com.nadiabsag.database.entities.DetailEntity
import com.nadiabsag.database.entities.ListEntity
import com.nadiabsag.database.entities.toDatabase
import com.nadiabsag.room.databinding.ActivitySuperheroListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class SuperheroListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuperheroListBinding
    private lateinit var retrofit: Retrofit
    private lateinit var room: SuperheroDatabase
    private lateinit var adapter: SuperheroAdapter


    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperheroListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()
        room = Room.databaseBuilder(this, SuperheroDatabase::class.java, "superheroes_sagarra").fallbackToDestructiveMigration().build()
        fillDatabase()
        initUI()
    }

    private fun navigateToDetail(id: String) {
        val intent = Intent(this, DetailSuperheroActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }

    private fun initUI() {
       binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
          override fun onQueryTextSubmit(query: String?): Boolean {
               searchByName(query.orEmpty())
              return false
           }

            override fun onQueryTextChange(newText: String?) = false
        })

        adapter = SuperheroAdapter { superheroId -> navigateToDetail(superheroId) }
        binding.rvSuperhero.setHasFixedSize(true)
        binding.rvSuperhero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperhero.adapter = adapter
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {

            val listEntity: List<ListEntity> = room.getListDao().getSuperheroBy(query)
            runOnUiThread { adapter.updateList(listEntity)
                binding.progressBar.isVisible = false
                    }
            }
        }

    private fun fillDatabase() {
        binding.progressBar.isVisible = true

        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<SuperheroDataResponse> =
                retrofit.create(ApiService::class.java).getSuperheroes()
            val response: SuperheroDataResponse = myResponse.body()!!
            val myResponseDetail: Response<SuperheroDetailResponse> = retrofit.create(ApiService::class.java).getSuperheroDetail()
            val responseDetail: SuperheroDetailResponse = myResponseDetail.body()!!

            val superheroItemList = response.superheroes.map { it.toDatabase() }
            val superheroDetailList = responseDetail.listDetail.map { it.toDatabase() }

            room.getListDao().deleteAllSuperheroes()
            room.getListDao().insertAll(superheroItemList)
            room.getDetailDao().deleteAllSuperheroesDetail()
            room.getDetailDao().insertAll(superheroDetailList)
        }
    }

}


    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

