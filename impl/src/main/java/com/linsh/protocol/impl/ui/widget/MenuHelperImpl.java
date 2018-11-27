package com.linsh.protocol.impl.ui.widget;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import com.linsh.protocol.Client;
import com.linsh.protocol.activity.ActivitySubscribe;
import com.linsh.protocol.ui.OnClickListener;
import com.linsh.protocol.ui.OnItemClickListener;
import com.linsh.protocol.ui.widget.MenuHelper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/25
 *    desc   :
 * </pre>
 */
class MenuHelperImpl implements MenuHelper, ActivitySubscribe.OnCreateOptionsMenu, ActivitySubscribe.OnOptionsItemSelected {

    private Map<Integer, ItemInfo> items = new LinkedHashMap<>();
    private OnItemClickListener<MenuItem> listener;

    @Override
    public void attach(Activity activity) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int i = 0;
        for (Map.Entry<Integer, ItemInfo> entry : items.entrySet()) {
            final ItemInfo item = entry.getValue();
            item.index = i++;
            MenuItem menuItem = menu.add(item.groupId, item.itemId, item.order, item.title);
            if (item.actionEnum != 0) menuItem.setShowAsAction(item.actionEnum);
            if (item.icon != 0) menuItem.setIcon(item.icon);
            if (item.listener != null)
                menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        item.listener.onClick(menuItem);
                        return true;
                    }
                });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (listener != null) {
            ItemInfo itemInfo = items.get(item.getItemId());
            if (itemInfo != null) {
                listener.onItemClick(item, itemInfo.index);
                return true;
            }
        }
        return false;
    }

    @Override
    public MenuHelper addItem(CharSequence title) {
        return addItem(title, null);
    }

    @Override
    public MenuHelper addItem(CharSequence title, OnClickListener<MenuItem> listener) {
        addItemInfo(new ItemInfo(title, null));
        return this;
    }

    @Override
    public MenuHelper addItems(CharSequence[] titles) {
        return addItems(titles, null);
    }

    @Override
    public MenuHelper addItems(CharSequence[] titles, OnItemClickListener<MenuItem> listener) {
        return addItems(Arrays.asList(titles), listener);
    }

    @Override
    public MenuHelper addItems(List<? extends CharSequence> titles) {
        return addItems(titles, null);
    }

    @Override
    public MenuHelper addItems(List<? extends CharSequence> titles, OnItemClickListener<MenuItem> listener) {
        for (CharSequence title : titles) {
            build(title).add();
        }
        setOnMenuItemClickListener(listener);
        return this;
    }

    @Override
    public MenuHelper setOnMenuItemClickListener(OnItemClickListener<MenuItem> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public ItemBuilder build() {
        return new ItemBuilderImpl();
    }

    @Override
    public ItemBuilder build(CharSequence title) {
        return new ItemBuilderImpl(title);
    }

    private void addItemInfo(ItemInfo itemInfo) {
        items.put(itemInfo.itemId, itemInfo);
    }

    private static class ItemInfo {
        int index;
        final int groupId;
        final int itemId;
        final int order;
        final CharSequence title;
        final int icon;
        final int actionEnum;
        final OnClickListener<MenuItem> listener;

        ItemInfo(CharSequence title, OnClickListener<MenuItem> listener) {
            this(0, 0, 0, title, 0, 0, listener);
        }

        ItemInfo(int groupId, int itemId, int order, CharSequence title, int icon, int actionEnum, OnClickListener<MenuItem> listener) {
            this.groupId = groupId;
            this.itemId = itemId != 0 ? itemId : Client.value().id().create();
            this.order = order;
            this.title = title;
            this.icon = icon;
            this.actionEnum = actionEnum;
            this.listener = listener;
        }
    }

    private class ItemBuilderImpl implements ItemBuilder {

        int groupId;
        int itemId;
        int order;
        CharSequence title;
        int icon;
        int actionEnum;
        OnClickListener<MenuItem> listener;

        ItemBuilderImpl() {
        }

        ItemBuilderImpl(CharSequence title) {
            this.title = title;
        }

        @Override
        public ItemBuilder setGroupId(int id) {
            groupId = id;
            return this;
        }

        @Override
        public ItemBuilder setItemId(int id) {
            itemId = id;
            return this;
        }

        @Override
        public ItemBuilder setOrder(int order) {
            this.order = order;
            return this;
        }

        @Override
        public ItemBuilder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        @Override
        public ItemBuilder setIcon(int icon) {
            this.icon = icon;
            return this;
        }

        @Override
        public ItemBuilder setShowAsAction(int actionEnum) {
            this.actionEnum = actionEnum;
            return this;
        }

        @Override
        public ItemBuilder setOnClickListener(OnClickListener<MenuItem> listener) {
            this.listener = listener;
            return this;
        }

        @Override
        public MenuHelper add() {
            addItemInfo(new ItemInfo(groupId, itemId, order, title, icon, actionEnum, listener));
            return MenuHelperImpl.this;
        }
    }
}
