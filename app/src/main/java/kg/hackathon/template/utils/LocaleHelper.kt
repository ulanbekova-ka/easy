package kg.hackathon.template.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import java.util.*

class LocaleHelper {

    companion object {
        private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

        private var defaultPrefs: SharedPreferences? = null

        private fun getPrefs(context: Context): SharedPreferences {
            if(defaultPrefs == null) defaultPrefs =  PreferenceManager.getDefaultSharedPreferences(context)

            return defaultPrefs!!
        }

        fun onAttach(context: Context): Context {
            val lang = getPersistedData(context, Locale.getDefault().language) ?: "ru"
            return setLocale(context, lang)
        }

        fun setLocale(context: Context, language: String): Context {
            persist(context, language)

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateResources(context, language)
            } else updateResourcesLegacy(context, language)

        }

        private fun getPersistedData(context: Context, defaultLanguage: String): String? {
            return getPrefs(context).getString(SELECTED_LANGUAGE, defaultLanguage)
        }

        private fun persist(context: Context, language: String) =
            getPrefs(context)
                .edit()
                .apply {
                    putString(SELECTED_LANGUAGE, language)
                    apply()
                }

        @TargetApi(Build.VERSION_CODES.N)
        private fun updateResources(context: Context, language: String): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)

            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            return context.createConfigurationContext(configuration)
        }

        private fun updateResourcesLegacy(context: Context, language: String): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)

            val resources = context.resources

            val configuration = resources.configuration
            configuration.locale = locale
            configuration.setLayoutDirection(locale)

            resources.updateConfiguration(configuration, resources.displayMetrics)

            return context
        }
    }
}