package com.example.focuslytics

import android.app.AlertDialog
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if permission is already given
        if (!hasUsageAccess()) {
            showPermissionPopup()
        }
    }

    // 🔹 Custom popup shown inside app
    private fun showPermissionPopup() {
        AlertDialog.Builder(this)
            .setTitle("Permission Required")
            .setMessage(
                "Focuslytics needs access to your app usage data to track your digital wellbeing."
            )
            .setCancelable(false)
            .setPositiveButton("Allow") { _, _ ->
                openUsageAccessSettings()
            }
            .setNegativeButton("Deny") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    // 🔹 Redirect user to Usage Access Settings
    private fun openUsageAccessSettings() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivity(intent)
    }

    // 🔹 Check if Usage Access permission is granted
    private fun hasUsageAccess(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }
}
