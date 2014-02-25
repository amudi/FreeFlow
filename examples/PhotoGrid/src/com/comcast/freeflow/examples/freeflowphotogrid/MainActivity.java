/*******************************************************************************
 * Copyright 2013 Comcast Cable Communications Management, LLC
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.comcast.freeflow.examples.freeflowphotogrid;

import java.util.ArrayList;

import com.comcast.freeflow.animations.DefaultLayoutAnimator;
import com.comcast.freeflow.core.Container;
import com.comcast.freeflow.core.ItemProxy;
import com.comcast.freeflow.core.Section;
import com.comcast.freeflow.core.SectionedAdapter;
import com.comcast.freeflow.examples.freeflowphotogrid.R;
import com.comcast.freeflow.layouts.HGridLayout;
import com.comcast.freeflow.layouts.HLayout;
import com.comcast.freeflow.layouts.VGridLayout;
import com.comcast.freeflow.layouts.VLayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	Container container = null;
	HLayout hLayout = null;
	VLayout vLayout = null;
	VGridLayout vGridLayout = null;
	HGridLayout hGridLayout = null;
	HLayout hLayout1 = null;
	VLayout vLayout1 = null;
	VGridLayout vGridLayout1 = null;
	HGridLayout hGridLayout1 = null;
	Button changeButton, jumpButton, jumpButtonAnim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

		final ImageAdapter adapter = new ImageAdapter();

		container = new Container(this);

		// container.setOnTouchListener(this);
		// container.setFocusable(true);

		DefaultLayoutAnimator anim = (DefaultLayoutAnimator) container.getLayoutAnimator();
		anim.animateAllSetsSequentially = false;
		anim.animateIndividualCellsSequentially = false;

		container.requestFocus();
		hLayout = new HLayout();
		hLayout.setLayoutParams(new HLayout.LayoutParams(100, 150, 600));

		vLayout = new VLayout();
		vLayout.setLayoutParams(new VLayout.LayoutParams(100, 600, 150));

		vGridLayout = new VGridLayout();
		vGridLayout.setLayoutParams(new VGridLayout.LayoutParams(200,200, 600, 100));

		hGridLayout = new HGridLayout();
		hGridLayout.setLayoutParams(new HGridLayout.LayoutParams(200, 200, 100, 600));
	
		hLayout1 = new HLayout();
		hLayout1.setLayoutParams(new HLayout.LayoutParams(100, 150, 600));

		vLayout1 = new VLayout();
		vLayout1.setLayoutParams(new VLayout.LayoutParams(100, 600, 150));

		vGridLayout1 = new VGridLayout();
		vGridLayout1.setLayoutParams(new VGridLayout.LayoutParams(200,200, 600, 100));

		hGridLayout1 = new HGridLayout();
		hGridLayout1.setLayoutParams(new HGridLayout.LayoutParams(200, 200, 100, 600));
		
		container.setAdapter(adapter);
		container.setLayout(hLayout);
		// container.setLayoutAnimator(new ScaleAnimator());

		frameLayout.addView(container);

		changeButton = ((Button) frameLayout.findViewById(R.id.transitionButton));
		changeButton.setText("Layout");
		changeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (container.getLayoutController() == hLayout) {
					changeButton.setText("Layout");
					container.setLayout(vLayout);
				} else if (container.getLayoutController() == vLayout) {
					container.setLayout(vGridLayout);
				} else if (container.getLayoutController() == vGridLayout) {
					container.setLayout(hGridLayout);
				} else if (container.getLayoutController() == hGridLayout) {
					changeButton.setText("Scale");
					container.setLayout(hLayout1);
				} else if (container.getLayoutController() == hLayout1) {
					container.setLayout(vLayout1);
				} else if (container.getLayoutController() == vLayout1) {
					container.setLayout(vGridLayout1);
				} else if (container.getLayoutController() == vGridLayout1) {
					container.setLayout(hGridLayout1);
				} else {
					container.setLayout(hLayout);
				}
			}
		});

		jumpButton = (Button) findViewById(R.id.jumpButton);
		jumpButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int section = (int) (adapter.getNumberOfSections() * Math.random());
				int index = (int) (adapter.getSection(section).getDataCount() * Math.random());
				Log.d(TAG, "section = " + section + ", index = " + index);
				container.scrollToItem(section, index, false);

			}
		});
		
		jumpButtonAnim = (Button) findViewById(R.id.jumpButtonAnim);
		jumpButtonAnim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int section = (int) (adapter.getNumberOfSections() * Math.random());
				int index = (int) (adapter.getSection(section).getDataCount() * Math.random());
				Log.d(TAG, "section = " + section + ", index = " + index);
				container.scrollToItem(section, index, true);

			}
		});

		changeButton.bringToFront();
		jumpButton.bringToFront();
		jumpButtonAnim.bringToFront();
	}

	class ImageAdapter implements SectionedAdapter {

		private ArrayList<Section> sections = new ArrayList<Section>();

		public ImageAdapter() {
			for (int i = 0; i < 10; i++) {
				Section s = new Section();

				s.setSectionTitle("Section " + i);
				for (int j = 0; j < 10; j++) {
					s.addItem(new Object());
				}
				sections.add(s);
			}
		}

		@Override
		public long getItemId(int section, int position) {
			return section * 1000 + position;
		}

		@Override
		public View getItemView(int section, int position, View convertView, ViewGroup parent) {
			myTv tv = null;
			if (convertView != null) {
				// Log.d(TAG, "Convert view not null");
				tv = (myTv) convertView;
			} else {
				tv = new myTv(MainActivity.this);
			}

			tv.setFocusable(false);
			tv.setBackgroundResource(R.drawable.orange);
			// tv.setAlpha(.25f);
			// button.setOnTouchListener(MainActivity.this);
			tv.setText("s" + section + " p" + position);

			return tv;
		}

		@Override
		public View getHeaderViewForSection(int section, View convertView, ViewGroup parent) {
			myTv tv = null;
			if (convertView != null) {
				// Log.d(TAG, "Convert view not null");
				tv = (myTv) convertView;
			} else {
				tv = new myTv(MainActivity.this);
			}

			tv.setFocusable(false);
			tv.setBackgroundColor(Color.GRAY);
			// button.setOnTouchListener(MainActivity.this);
			tv.setText("section header" + section);

			return tv;
		}

		@Override
		public int getNumberOfSections() {
			// TODO Auto-generated method stub
			return sections.size();
		}

		@Override
		public Section getSection(int index) {
			// TODO Auto-generated method stub
			if (index < sections.size() && index >= 0)
				return sections.get(index);

			return null;
		}

		@Override
		public Class[] getViewTypes() {
			Class[] types = { myTv.class, myTv.class };

			return types;
		}

		@Override
		public Class getViewType(ItemProxy proxy) {

			return myTv.class;
		}

		@Override
		public boolean shouldDisplaySectionHeaders() {
			return true;
		}

	}

	class myTv extends TextView {

		public myTv(Context context) {
			super(context);
		}

		public myTv(Context context, AttributeSet attrs) {
			super(context, attrs);

		}

		public myTv(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);

		}

		@Override
		protected void onAttachedToWindow() {
			super.onAttachedToWindow();

			// Log.d(TAG, "attached: " + this.getText());

		}

		@Override
		protected void onDetachedFromWindow() {
			super.onDetachedFromWindow();

			// Log.d(TAG, "detached: " + this.getText());
		}
	}

}