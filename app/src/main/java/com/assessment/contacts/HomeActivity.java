package com.assessment.contacts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

		buildAppBarConfiguration();
		setupNavigation();
	}

	private void buildAppBarConfiguration() {
		mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_contact_list)
				.setDrawerLayout(findViewById(R.id.ac_home_nav_drawer))
				.build();
	}

	private void setupNavigation() {
		NavController navController = Navigation.findNavController(this, R.id.ac_home_nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
		NavigationUI.setupWithNavController((NavigationView) findViewById(R.id.ac_home_nav_view), navController);
	}

	@Override
	public boolean onSupportNavigateUp() {
		NavController navController = Navigation.findNavController(this, R.id.ac_home_nav_host_fragment);
		return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
	}
}
