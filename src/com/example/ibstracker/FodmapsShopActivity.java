package com.example.ibstracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FodmapsShopActivity extends Activity {

	static List<FodmapItem> knownFodmapItems = new ArrayList<FodmapItem>();
	static List<FodmapItem> chosenFodmapItems = new ArrayList<FodmapItem>();
	AutoCompleteTextView actv;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			knownFodmapItems = Utils.getFileAsFodmapItemList(getAssets().open("fodmapList.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.fodmaps_shop);
	    final Button addButton = (Button)findViewById(R.id.button1);
	    actv = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
	    actv.setThreshold(1);
	    populateListView();
	    populateActv();
		addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View w){
				addItemToListView(actv.getText().toString());
			}
		});
	}
	private void addItemToListView(String text) {
		for (FodmapItem fodmapItem : knownFodmapItems) {
			if (Utils.toDisplayCase(text).equals(fodmapItem.name) || text.equals(fodmapItem.toString())) {
				chosenFodmapItems.add(fodmapItem);
				populateListView();
				return;
			}
		}
		Bundle args = new Bundle();
		args.putString("message", "Add \"" + text + "\" to the list of known food items?");
		args.putString("item", text);
		final AddItemDialog d = new AddItemDialog();
		d.setArguments(args);
		d.show(getFragmentManager(), "Add Item?");
	}
	
	private void populateListView() {
		
		ArrayAdapter<FodmapItem> adapter = new ArrayAdapter<FodmapItem>(this, R.layout.fodmap_shop_item_red, chosenFodmapItems) {
		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		
		        TextView textView = (TextView)super.getView(position, convertView, parent);
		
		        FodmapItem item = getItem(position);
		        if (item.fodmap == Fodmap.WARNING) {
		        	textView = (TextView) getLayoutInflater().inflate(R.layout.fodmap_shop_item_red, null);
		        }
		        else if (item.fodmap == Fodmap.CAUTION) {
		        	textView = (TextView) getLayoutInflater().inflate(R.layout.fodmap_shop_item_yellow, null);
		        }
		        else if (item.fodmap == Fodmap.ALL_CLEAR) {
		        	textView = (TextView) getLayoutInflater().inflate(R.layout.fodmap_shop_item_green, null);
		        }
		        textView.setText(item.toString());
		        return textView;
		    }
		};
		
		ListView listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(adapter);
	}
	
private void populateActv() {
		
		ArrayAdapter<FodmapItem> adapter = new ArrayAdapter<FodmapItem>(this, R.layout.fodmap_shop_item_red, knownFodmapItems) {
		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		
		        TextView textView = (TextView)super.getView(position, convertView, parent);
		
		        FodmapItem item = getItem(position);
		        if (item.fodmap == Fodmap.WARNING) {
		        	textView = (TextView) getLayoutInflater().inflate(R.layout.fodmap_shop_item_red, null);
		        }
		        else if (item.fodmap == Fodmap.CAUTION) {
		        	textView = (TextView) getLayoutInflater().inflate(R.layout.fodmap_shop_item_yellow, null);
		        }
		        else if (item.fodmap == Fodmap.ALL_CLEAR) {
		        	textView = (TextView) getLayoutInflater().inflate(R.layout.fodmap_shop_item_green, null);
		        }
		        textView.setText(item.toString());
		        return textView;
		    }
		};

		actv.setAdapter(adapter);
	}
	
public class AddItemDialog extends DialogFragment{
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			super.onCreateDialog(savedInstanceState);
			String[] strings = {Fodmap.ALL_CLEAR.toString(), Fodmap.CAUTION.toString(), Fodmap.WARNING.toString()};
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
				.setSingleChoiceItems(strings, -1 , null)
				.setPositiveButton("Add", new DialogInterface.OnClickListener() {
				
					public void onClick(DialogInterface dialog, int i){
						int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
						String item = Utils.toDisplayCase((String)getArguments().get("item"));
						FodmapItem fm;
						switch(selectedPosition) {
						case 0:
							fm = new FodmapItem(item, Fodmap.ALL_CLEAR);
							knownFodmapItems.add(fm);
							chosenFodmapItems.add(fm);
							populateListView();
							break;
						case 1:
							fm = new FodmapItem(item, Fodmap.CAUTION);
							knownFodmapItems.add(fm);
							chosenFodmapItems.add(fm);
							break;
						case 2:
							fm = new FodmapItem(item, Fodmap.WARNING);
							knownFodmapItems.add(fm);
							chosenFodmapItems.add(fm);
							break;
						default:
							break;
						}
					}
				}).setNegativeButton("Not Now", null);
			builder.setTitle((String)(getArguments().get("message")));
			return builder.create();
		}
	}

}
