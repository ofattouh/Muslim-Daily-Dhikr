package mohamed.omar.com.muslimdailydhikr2

import android.content.res.Resources
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat;


class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val prefCity: EditTextPreference
        val prefCountry: EditTextPreference
        val prefCalcMethod: ListPreference

        // City
        prefCity = findPreference("manual_entry_city_edittext") as EditTextPreference
        if (prefCity.text == ""){
            prefCity.summary = "no selection"
        }
        else{
            prefCity.summary = prefCity.text
        }
        prefCity.onPreferenceChangeListener = this


        // Country
        prefCountry = findPreference("manual_entry_country_edittext") as EditTextPreference
        if (prefCountry.text == ""){
            prefCountry.summary = "no selection"
        }
        else{
            prefCountry.summary = prefCountry.text
        }
        prefCountry.onPreferenceChangeListener = this


        // Calculation Method @ https://aladhan.com/calculation-methods
        prefCalcMethod = findPreference("calculation_method_list") as ListPreference
        if (prefCalcMethod.value == ""){
            prefCalcMethod.summary = "no selection"
        }
        else{
            prefCalcMethod.summary  = prefCalcMethod.entry
        }
        prefCalcMethod.onPreferenceChangeListener = this

    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        val key = preference.key

        // City
        if (key == "manual_entry_city_edittext") {
            val pref = preference as EditTextPreference

            if (newValue == "") {
                pref.summary = "no selection"
            }
            else{
                pref.summary = newValue as String
            }
        }

        // Country
        if (key == "manual_entry_country_edittext") {
            val pref = preference as EditTextPreference

            if (newValue == "") {
                pref.summary = "no selection"
            }
            else{
                pref.summary = newValue as String
            }
        }

        // Calculation Method
        if (key == "calculation_method_list") {
            val pref              = preference as ListPreference
            val res: Resources    = resources
            val calcMethodEntries = res.getStringArray(R.array.listcalculationmethodsentries)
            var countEntry        = 1
            var newEntry          = ""

            for (value in calcMethodEntries) {
                if (newValue == countEntry.toString()){
                    newEntry = value
                    //Log.i ("entries Equal: ", newValue.toString() + "-" + value + ": " + countEntry.toString() + ": " + newEntry)
                }
                countEntry++
            }

            if (newValue == "") {
                pref.summary = "no selection"
            }
            else{
                pref.summary = newEntry
            }
        }
        return true
    }

    //
    override fun onPreferenceClick(p0: Preference?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}