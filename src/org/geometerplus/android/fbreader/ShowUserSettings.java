package org.geometerplus.android.fbreader;

import org.geometerplus.fbreader.fbreader.FBReaderApp;

public class ShowUserSettings extends FBAndroidAction {

	public ShowUserSettings(FBReader baseActivity, FBReaderApp fbreader) {
		super(baseActivity, fbreader);
	}

	@Override
	protected void run(Object... params) {
		BaseActivity.onUserSettingsRequested();

	}
}
