package eu.amaurygauthier.viewsource;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class ViewSource extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();

		// Verify that Chrome is installed
		Intent chromeIntent = new Intent(Intent.ACTION_VIEW)
				.setComponent(new ComponentName("com.android.chrome",
						"com.android.chrome.Main"));
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(
				chromeIntent, 0);
		boolean isSafeToCallChrome = activities.size() > 0;

		if (!isSafeToCallChrome) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.chromeInstallMessageAlert);
			builder.setTitle(R.string.chromeInstallTitleAlert);
			builder.setIcon(R.drawable.ic_launcher);

			builder.setPositiveButton(R.string.chromeInstallPositiveAlert,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri
									.parse("market://details?id=com.android.chrome")));
						}
					});
			builder.setNegativeButton(R.string.chromeInstallNegativeAlert,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
							finish();
						}
					});

			builder.create().show();
		} else if (intent.getAction().equals(Intent.ACTION_SEND)) {
			// if this is from the share menu
			if (intent.hasExtra(Intent.EXTRA_TEXT)) {
				StringBuilder newUri = new StringBuilder();
				newUri.append("javascript: location.href='view-source:");
				newUri.append(extras.getString(Intent.EXTRA_TEXT));
				newUri.append("';");
				Log.d(ViewSource.class.getName(),
						"requesting uri " + newUri.toString());
				startActivity(chromeIntent
						.setData(Uri.parse(newUri.toString())));
				finish();
			}
		}
	}
}
