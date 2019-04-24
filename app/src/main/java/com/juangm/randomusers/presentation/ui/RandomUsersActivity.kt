package com.juangm.randomusers.presentation.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.juangm.randomusers.R

class RandomUsersActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        setupNavigation()
    }

    private fun setupNavigation() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.users_nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun getSettingsDeepLink() = findNavController(R.id.users_nav_host_fragment)
        .createDeepLink()
        .setDestination(R.id.settingsFragment)
        .createPendingIntent()

    private fun showDeppLinkNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel("deeplink", "Deep Links", NotificationManager.IMPORTANCE_HIGH)
            )
        }

        val builder = NotificationCompat.Builder(this, "deeplink")
            .setContentTitle("Settings")
            .setContentText("Deep link to settings")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(getSettingsDeepLink())
            .setAutoCancel(true)
        notificationManager.notify(0, builder.build())
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.users_nav_host_fragment).navigateUp(appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settingsFragment -> return item.onNavDestinationSelected(findNavController(R.id.users_nav_host_fragment))
            R.id.notification -> showDeppLinkNotification()
        }
        return super.onOptionsItemSelected(item)
    }
}
