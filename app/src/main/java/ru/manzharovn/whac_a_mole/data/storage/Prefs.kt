package ru.manzharovn.whac_a_mole.data.storage

import android.content.Context
import ru.manzharovn.whac_a_mole.R
import javax.inject.Inject

class Prefs @Inject constructor(val context: Context): LocalStorage {

    private val sharedPref = context.getSharedPreferences(
        context.resources.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    override fun getScore(): Int =
        sharedPref.getInt(context.resources.getString(R.string.saved_high_score_key), 0)

    override fun setScore(score: Int) {
        with(sharedPref.edit()) {
            putInt(context.resources.getString(ru.manzharovn.whac_a_mole.R.string.saved_high_score_key), score)
            apply()
        }
    }
}