package com.ksy.djd.mainpanel;

//main_menu中的gridView的item对象
public class MenuItem {
	private String icon;
	private int iconId;
	private String name;
	private String activity;
	private int id;

	private boolean select=false;//默认不选中

	public MenuItem() {
		super();
	}

	public MenuItem(String icon, String name, String activity) {
		super();
		this.icon = icon;
		this.name = name;
		this.activity = activity;
	}
	public MenuItem(int iconId, String name, String activity) {
		super();
		this.iconId = iconId;
		this.name = name;
		this.activity = activity;
	}
	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
