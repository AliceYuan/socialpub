package org.geometerplus.android.fbreader;


import org.geometerplus.zlibrary.ui.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UserSettingsActivity extends Activity{
	EditText userNameText;
	EditText userProfileImageText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_settings);
		userNameText = ((EditText)findViewById(R.id.userAccount));
		userProfileImageText = ((EditText)findViewById(R.id.userUrl));
		userNameText.setText(FBReader.userName);
		userProfileImageText.setText(FBReader.userProfileImage);
	}

	public void onSaveInfo(View view){
		FBReader.userName = userNameText.getText().toString();
		FBReader.userProfileImage = userProfileImageText.getText().toString();
	}
}
