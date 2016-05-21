package com.liuw53.locationprivacy;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ChooseAppActivity extends Activity {

	private class AppAdapter extends BaseAdapter {
		private class Item {
			public TextView title;
			public ImageView image;
			public ToggleButton toggle;
		}

		public class AppInfo {
			public String label ;
			public String packageName ;
			public Drawable icon;
			public AppInfo(String appLabel, String appPackageName,Drawable appIcon) {
				label=appLabel;
				packageName=appPackageName;
				icon=appIcon;
			}
		}
		private LayoutInflater mInflater;
		private Context context;
		
		ArrayList<AppInfo> appList;
		AppAdapter(Context cont) {
			context = cont;
			mInflater = LayoutInflater.from(context);
			PackageManager pm = getPackageManager();
			List<ApplicationInfo> allAppList = pm.getInstalledApplications(0);
			appList=new ArrayList<AppInfo>();
			for(ApplicationInfo app:allAppList)
				appList.add(new AppInfo(app.loadLabel(pm).toString(), app.packageName, app.loadIcon(pm)));
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return appList.size();
		}

		@Override
		public AppInfo getItem(int position) {
			// TODO Auto-generated method stub
			return appList.get(position);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {//������int position->final int position!!!
			// TODO Auto-generated method stub
			Item item;
			if (null==convertView) {
				convertView = mInflater.inflate(R.layout.appitem, null);
				item = new Item();
				item.title = (TextView) convertView.findViewById(R.id.appname);
				item.image = (ImageView) convertView.findViewById(R.id.appicon);
				item.toggle = (ToggleButton) convertView.findViewById(R.id.apptoggle);
				convertView.setTag(item);
			} else {
				item = (Item) convertView.getTag();
			}
			//AppInfo tmp = getItem(position);
			item.title.setText(getItem(position).label);
			item.image.setImageDrawable(getItem(position).icon);
//			item.toggle.setTag(position);//��������Ҫ�Ż�������Ҫ����
			
			//setOnCheckedChangeListener���ã�������״̬��query֮ǰ����������ף�ԭ��δ֪���ܿ�����OnCheckedChangeListener��ʵ�ֹ���У����¼��״̬��
			item.toggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton btnView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (!isChecked) {
								getContentResolver().delete(
										BlackAppProvider.BLACK_APP_NAME_URI,
										DBHelper.FIRST_VALUE + "=\""
												+getItem(position).label + "\" AND "
												+ DBHelper.KEY + "=\""
												+ getItem(position).packageName + "\"", null);
							}
							else {
								ContentValues values = new ContentValues();
								values.put(DBHelper.KEY, getItem(position).packageName);
								values.put(DBHelper.FIRST_VALUE,  getItem(position).label);
								getContentResolver().insert(BlackAppProvider.BLACK_APP_NAME_URI,	values);
							}
							//AppAdapter.this.notifyDataSetChanged();
						}
					});
			Cursor cr = getContentResolver().query(
					BlackAppProvider.BLACK_APP_NAME_URI,
					null,
					DBHelper.FIRST_VALUE+"=\"" + getItem(position).label + "\" AND "+DBHelper.KEY+"=\""
							+ getItem(position).packageName + "\"", null, null);
			Boolean isBlack = false;
			if (null != cr)
				isBlack = (cr.getCount() > 0);
			item.toggle.setChecked(isBlack);
			if (null != cr)
				cr.close();
			return convertView;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
	}
	//�д���ӽ��汣����Ϣ
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	//�д����ActionBar
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applist);
		ListView listview = (ListView) findViewById(R.id.appitem);
		AppAdapter adapter = new AppAdapter(this);
		listview.setAdapter(adapter);
	}
	//�д�Ķ�����setResult,startActivityForResult����
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		//intent.putExtra("ischoose", false);
		setResult(RESULT_OK, intent);
		finish();
	}
}
