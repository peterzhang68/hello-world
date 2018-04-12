/** @file DemoModeActivity.java
 * 
 * Main activity of demo mode choose.
 * 
 * Copyright (C) 2014 SHARP CORPORATION.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author ChenGuihua
 */

package com.lenovo.demo.tv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DemoModeActivity extends Activity implements OnClickListener {

    Button bn_tv, bn_motion, bn_demo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.demo_mode);

	bn_tv = (Button) findViewById(R.id.button_tv);
	bn_demo = (Button) findViewById(R.id.button_demo);
	bn_tv.setOnClickListener(this);
	bn_demo.setOnClickListener(this);
	
	Display dp = getWindowManager().getDefaultDisplay();
	Point outSize = new Point();
	dp.getSize(outSize);
	int wd = outSize.x;
	int ht = outSize.y;
	
	DisplayMetrics metic = new DisplayMetrics();
	dp.getMetrics(metic);
	int desinty = metic.densityDpi;
	Log.d("", "screen width="+wd+" height="+ht+" desnity="+desinty);
    }

    @Override
    public void onClick(View view) {
	// TODO Auto-generated method stub
	int button_event = view.getId();
	// start video show
	if (button_event == R.id.button_demo) {
	    Intent intent = new Intent(this, FeatureModeActivity.class);
	    startActivity(intent);
	    finish();
	} else if (button_event == R.id.button_tv) // start feature demo
	{
	    // TODO Auto-generated method stub
	    Intent intent = new Intent(this, VideoPlayActivity.class);
	    startActivity(intent);
	    finish();
	}
    }

}
