package com.nadiabsag.room


import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.nadiabsag.database.SuperheroDatabase
import com.nadiabsag.database.entities.DetailEntity
import com.nadiabsag.database.entities.ListEntity
import com.nadiabsag.database.entities.toDatabase
import com.nadiabsag.room.SuperheroListActivity.Companion.EXTRA_ID
import com.nadiabsag.room.databinding.ActivityDetailSuperheroBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperheroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSuperheroBinding
    private lateinit var room: SuperheroDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailSuperheroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        room = Room.databaseBuilder(this, SuperheroDatabase::class.java, "superheroes_sagarra").build()

        val id: String = intent.getStringExtra(EXTRA_ID).orEmpty()
        getSuperheroInformation(id)

    }


    private fun getSuperheroInformation(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val superheroList: ListEntity = room.getListDao().getSuperhero(id)

            val superheroDetails: DetailEntity = room.getDetailDao().getSuperhero(id)
            runOnUiThread { createUI(superheroList, superheroDetails) }
        }
    }

    private fun createUI(superhero: ListEntity, superheroDetail : DetailEntity) {

                    Picasso.get().load(superhero.image).into(binding.ivSuperhero)
                    binding.tvSuperheroName.text = superhero.name
                    binding.tvSuperheroRealName.text = superheroDetail.fullName
                    binding.tvPublisher.text = superheroDetail.publisher
                    prepareStats(superheroDetail)

            }



    private fun prepareStats(detail: DetailEntity) {
        updateHeight(binding.viewIntelligence, detail.intelligence.orEmpty())
        updateHeight(binding.viewStrength, detail.strength.orEmpty())
        updateHeight(binding.viewSpeed, detail.speed.orEmpty())
        updateHeight(binding.viewDurability, detail.durability.orEmpty())
        updateHeight(binding.viewPower, detail.power.orEmpty())
        updateHeight(binding.viewCombat, detail.combat.orEmpty())
    }

    private fun updateHeight(view: View, stat: String) {
        val params = view.layoutParams
        if (stat != "null") {
            params.height = pxToDp(stat.toFloat())
        } else {
            params.height = 0
        }
        CoroutineScope(Dispatchers.Main).launch { view.layoutParams = params }
    }

    private fun pxToDp(px: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics)
            .roundToInt()
    }

}