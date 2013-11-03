package org.geometerplus.android.fbreader;

import org.geometerplus.fbreader.fbreader.FBReaderApp;

public class ShowReSoAction extends FBAndroidAction {

	public ShowReSoAction(FBReader baseActivity, FBReaderApp fbreader) {
		super(baseActivity, fbreader);
	}

	@Override
	protected void run(Object... params) {
		BaseActivity.onResoRequested();

	}

}
