package com.vvl.fuel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

public class PostItem implements Parcelable {
	static SimpleDateFormat FORMATTER = new SimpleDateFormat(
			"E, dd MMM yyyy HH:mm:ss Z", Locale.US);
	static SimpleDateFormat OUT_FORMATTER = new SimpleDateFormat(
			"dd MMM yyyy HH:mm");
	public String title;
	public String link;
	public String description;
	public String date;

	public PostItem() {
		title = "";
		link = "";
		description = "";
		date = "";
	}

	public void setDate(String date) {
		try {
			this.date = (String) OUT_FORMATTER.format(FORMATTER.parse(date
					.trim()));
		} catch (ParseException e) {
			this.date = null;
		}
	}

	public PostItem copy() {
		PostItem copy = new PostItem();
		copy.title = title;
		copy.link = link;
		copy.description = description;
		copy.date = date;
		return copy;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(title);
		out.writeString(link);
		out.writeString(description);
		out.writeString(date);
	}

	public static final Parcelable.Creator<PostItem> CREATOR = new Parcelable.Creator<PostItem>() {

		public PostItem createFromParcel(Parcel source) {
			return new PostItem(source);
		}

		public PostItem[] newArray(int size) {
			return new PostItem[size];
		}
	};

	private PostItem(Parcel source) {
		title = source.readString();
		date = source.readString();
		link = source.readString();
		description = source.readString();
	}
}
