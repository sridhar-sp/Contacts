package com.assessment.contacts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

	private AppBarConfiguration mAppBarConfiguration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		setSupportActionBar(findViewById(R.id.main_toolbar));

		DrawerLayout drawerLayout = findViewById(R.id.ac_home_nav_drawer);

		NavigationView navigationView = findViewById(R.id.ac_home_nav_view);

		mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_contact_list)
				.setDrawerLayout(drawerLayout)
				.build();

		NavController navController = Navigation.findNavController(this, R.id.ac_home_nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
		NavigationUI.setupWithNavController(navigationView, navController);

	}

	@Override
	public boolean onSupportNavigateUp() {
		NavController navController = Navigation.findNavController(this, R.id.ac_home_nav_host_fragment);
		return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
	}
}
