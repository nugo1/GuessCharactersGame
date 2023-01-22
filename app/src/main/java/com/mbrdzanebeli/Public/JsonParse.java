package com.mbrdzanebeli.Public;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.mbrdzanebeli.Entity.Model;
import com.mbrdzanebeli.App.R;import com.google.gson.Gson;

import android.content.Context;

public class JsonParse {
	Context context;

	public JsonParse(Context context) {
		this.context = context;
	}

	public String getJson() {
		String text = null;
		try {
			InputStream is = context.getResources().openRawResource(
					R.raw.photodata);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			text = new String(buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return text;
	}

	public ArrayList<Model> getData(int pool) {
		Gson gs= new Gson();
		Model[] models= gs.fromJson(this.getJson(), Model[].class);

		ArrayList<Model> listModel= new ArrayList<Model>();
		for(Model model: models){
			if(model.getPoolId()== pool){
				listModel.add(model);
			}
		}
		return listModel;

	}
}
