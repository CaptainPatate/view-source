package eu.amaurygauthier.viewsource;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class ViewSource extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		// if this is from the share menu
		if (intent.getAction().equals(Intent.ACTION_SEND)) {   
			if (intent.hasExtra(Intent.EXTRA_TEXT)) {
				StringBuilder newUri = new StringBuilder();
				newUri.append("javascript: location.href='view-source:");
				newUri.append(extras.getString(Intent.EXTRA_TEXT));
				newUri.append("';");
				Log.d(ViewSource.class.getName(), "requesting uri "+newUri.toString());
				startActivity(new Intent(Intent.ACTION_VIEW)
					.setComponent(new ComponentName("com.android.chrome", "com.android.chrome.Main"))
					.setData(Uri.parse(newUri.toString())));
		  }
		} 
	}    
}
