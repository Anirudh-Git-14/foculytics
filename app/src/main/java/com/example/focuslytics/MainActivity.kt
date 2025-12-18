package com.example.focuslytics

import android.app.AlertDialog
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnAnalytics: LinearLayout
    private lateinit var btnUsage: LinearLayout
    private lateinit var btnFocus: LinearLayout
    private lateinit var btnProductivity: LinearLayout
    private lateinit var btnSettings: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔹 Initialize buttons (LinearLayouts)
        btnAnalytics = findViewById(R.id.btnAnalytics)
        btnUsage = findViewById(R.id.btnUsage)
        btnFocus = findViewById(R.id.btnFocus)
        btnProductivity = findViewById(R.id.btnProductivity)
        btnSettings = findViewById(R.id.btnSettings)

        // 🔹 Set click listeners for navigation
        btnAnalytics.setOnClickListener {
            startActivity(Intent(this, AnalyticsActivity::class.java))
        }

        btnUsage.setOnClickListener {
            startActivity(Intent(this, UsageActivity::class.java))
        }

        btnFocus.setOnClickListener {
            startActivity(Intent(this, FocusActivty::class.java))
        }

        btnProductivity.setOnClickListener {
            startActivity(Intent(this, ProductivityActivity::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // 🔹 Check if Usage Access permission is granted
        if (!hasUsageAccess()) {
            showPermissionPopup()
        }
    }

    // 🔹 Show custom permission popup
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

    // 🔹 Open Usage Access Settings
    private fun openUsageAccessSettings() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivity(intent)
    }

    // 🔹 Check if permission is already granted
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
