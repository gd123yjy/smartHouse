package com.example.yjy.smarthouse_android.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;

import com.example.yjy.smarthouse_android.MainFragmentPagerAdapter;
import com.example.yjy.smarthouse_android.R;
import com.example.yjy.smarthouse_android.fragments.ControlContainerFragment;
import com.example.yjy.smarthouse_android.fragments.DeviceFragment;
import com.example.yjy.smarthouse_android.fragments.VoiceFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
	private static String TAG = MainActivity.class.getSimpleName();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initLayout();

	}

	/**
	 * 初始化Layout。
	 */
	private void initLayout(){
		initViewPager();
	}

	private void initViewPager() {
		ViewPager viewPager;
		List<Fragment> fragmentList = new ArrayList<>();
		VoiceFragment fragment1 = new VoiceFragment();
		fragment1.setContext(this);
		Fragment fragment2 = new ControlContainerFragment();
		Fragment fragment3 = new DeviceFragment();
		fragmentList.add(fragment1);
		fragmentList.add(fragment2);
		fragmentList.add(fragment3);

		viewPager = (ViewPager) findViewById(R.id.view_pager);
		viewPager.setAdapter(new MainFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentList));
		viewPager.setOffscreenPageLimit(2);
//		viewPager.setPageTransformer(true, new MainPageTransformer());
		viewPager.setCurrentItem(0);

	}
	class MainPageTransformer implements ViewPager.PageTransformer {

		@Override
		public void transformPage(View view, float position) {
			if (position>-1 && position <= 0) {	//从右向左滑动当前View
				//设置旋转中心点；
				view.setPivotX(view.getMeasuredWidth());
				view.setPivotY(0);
				//绕Y轴做旋转操作
				view.setRotationY( 90f * position);
			} else if (position <= 1) {	//从左向右滑动当前View
				view.setPivotX(0);
				view.setPivotY(0);
				view.setRotationY( 90f * position);
			}	//超出屏幕外的不管
		}

	}

}
