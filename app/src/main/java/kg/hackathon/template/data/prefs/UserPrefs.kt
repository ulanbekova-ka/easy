package kg.hackathon.template.data.prefs

import android.content.Context
import kg.hackathon.template.prefs.EncryptedPreferences
import kg.hackathon.template.utils.PreferencesDelegate

class UserPrefs(context: Context) : EncryptedPreferences(context, "user_prefs") {

    var token: String by PreferencesDelegate(sharedPreferences, Keys.TOKEN, "")

    object Keys {
        const val TOKEN = "TOKEN"
    }
}