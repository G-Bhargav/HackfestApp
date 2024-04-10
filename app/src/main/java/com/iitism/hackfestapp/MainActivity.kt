package com.iitism.hackfestapp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.iitism.hackfestapp.auth.authActivity
import com.iitism.hackfestapp.changePassword.ChangePassword
import com.iitism.hackfestapp.databinding.ActivityMainBinding
import com.iitism.hackfestapp.ui.aboutus.AboutUsFragment
import com.iitism.hackfestapp.ui.adminscanqr.AdminScanQrFragment
import com.iitism.hackfestapp.ui.contactus.ContactUsFragment
import com.iitism.hackfestapp.ui.gatepass.GatePassFragment
import com.iitism.hackfestapp.ui.homefragment.HomeFragment
import com.iitism.hackfestapp.ui.noticeboardfragment.NoticeBoardFragment
import com.iitism.hackfestapp.ui.problemstatement.ProblemStatementFragment
import com.iitism.hackfestapp.ui.profilefragment.ProfileFragment
import com.iitism.hackfestapp.ui.rules.RulesFragment
import com.iitism.hackfestapp.ui.scanqr.ScanQrFragment
import com.iitism.hackfestapp.ui.timelinefragment.TimelineFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.root)
        val playerEmail=intent.getStringExtra("playerEmail")
        Log.d("mainActivityData",playerEmail.toString())

        val drawerLayout:DrawerLayout  = binding.drawerLayout
        val navView:NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val bottomNavigationView: BottomNavigationView=binding.appBarMain.bottomNavigationView;

        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_gatepass->replacefragment(GatePassFragment())
                R.id.nav_timeline->replacefragment(TimelineFragment())
                R.id.nav_home->replacefragment(HomeFragment())
                R.id.nav_problemstatement->replacefragment(ProblemStatementFragment())
                R.id.nav_noticeboard->replacefragment(NoticeBoardFragment())
            }
            true
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_profile,
                R.id.nav_timeline,
                R.id.nav_problemstatement,
                R.id.nav_noticeboard,
                R.id.nav_aboutus,
                R.id.nav_contactus,
                R.id.nav_rules,
                R.id.nav_scanqr,
                R.id.nav_gatepass,
                R.id.nav_adminscanqr
            ),
            drawerLayout
        )
        binding.appBarMain.menuButton.setOnClickListener{
            replacefragment(ProfileFragment());
        }
        binding.appBarMain.scan.setOnClickListener {
            val sharedPref=this?.getSharedPreferences("myPref", Context.MODE_PRIVATE);
            if(sharedPref?.getString("email","")=="admin@admin.com")
                replacefragment(AdminScanQrFragment());
            else
                replacefragment(ScanQrFragment());
        }
        binding.appBarMain.support.setOnClickListener {
            showDialog()
        }
        navView.setupWithNavController(navController)

        navView.setCheckedItem(R.id.nav_home)

    }

    fun replacefragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment)
        fragmentTransaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }


    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheetlayout)
        val editLayout = dialog.findViewById<LinearLayout>(R.id.layoutRule)
        val shareLayout = dialog.findViewById<LinearLayout>(R.id.layoutAbout)
        val uploadLayout = dialog.findViewById<LinearLayout>(R.id.layoutContact)
        val changePassword=dialog.findViewById<LinearLayout>(R.id.logout)
        editLayout.setOnClickListener {
            replacefragment(RulesFragment());
            Toast.makeText(this@MainActivity, "Rules", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        shareLayout.setOnClickListener {
            replacefragment(AboutUsFragment());
            Toast.makeText(this@MainActivity, "About Us", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        uploadLayout.setOnClickListener {
            replacefragment(ContactUsFragment());
            Toast.makeText(this@MainActivity, "Contact Us", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        changePassword.setOnClickListener{
            replacefragment(ChangePassword());
            Toast.makeText(this@MainActivity, "Change Password", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }


}