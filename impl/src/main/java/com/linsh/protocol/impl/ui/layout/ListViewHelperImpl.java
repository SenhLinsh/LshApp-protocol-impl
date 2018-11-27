package com.linsh.protocol.impl.ui.layout;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.linsh.protocol.Client;
import com.linsh.protocol.impl.R;
import com.linsh.protocol.ui.layout.ItemCounter;
import com.linsh.protocol.ui.layout.ItemViewFactory;
import com.linsh.protocol.ui.layout.ItemViewHelper;
import com.linsh.protocol.ui.layout.ListViewHelper;
import com.linsh.protocol.ui.layout.OnItemClickListener;
import com.linsh.protocol.ui.layout.OnItemLongClickListener;
import com.linsh.protocol.value.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.BiFunction;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/25
 *    desc   :
 * </pre>
 */
class ListViewHelperImpl<T> implements ListViewHelper<T>, View.OnClickListener, View.OnLongClickListener {

    private final Activity activity;
    private final RecyclerView recyclerView;

    private List<T> listData;
    private T singleData;
    private ItemCounter itemCounter;

    private List<ItemFactoryWrapper> itemFactories = new ArrayList<>();
    private List<ItemViewHelper> headers = new ArrayList<>();
    private List<ItemViewHelper> footers = new ArrayList<>();
    private ItemViewHelper divider;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public ListViewHelperImpl(Activity activity) {
        this.activity = activity;
        recyclerView = Client.ui().view(activity).viewGroup(new RecyclerView(activity)).getView();
        recyclerView.setAdapter(new AdapterImpl());
    }

    @Override
    public RecyclerView getView() {
        return recyclerView;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return recyclerView.getAdapter();
    }

    @Override
    public List<T> getData() {
        return listData;
    }

    @Override
    public T getSingleData() {
        return singleData;
    }

    @Override
    public ListViewHelper<T> setData(List<T> data) {
        listData = data;
        return this;
    }

    @Override
    public ListViewHelper<T> setData(T[] data) {
        listData = Arrays.asList(data);
        return this;
    }

    @Override
    public ListViewHelper<T> setSingleData(T data) {
        singleData = data;
        return this;
    }

    @Override
    public ListViewHelper<T> setCounter(ItemCounter counter) {
        itemCounter = counter;
        return this;
    }

    @Override
    public <V extends ItemViewHelper<T>> ListViewHelper<T> addItemView(ItemViewFactory<V> factory) {
        return addItemView(factory, null);
    }

    @Override
    public <V extends ItemViewHelper<T>> ListViewHelper<T> addItemView(ItemViewFactory<V> factory, BiFunction<ListViewHelper<T>, Integer, Boolean> filter) {
        itemFactories.add(new ItemFactoryWrapper(factory, filter));
        return this;
    }

    @Override
    public ListViewHelper<T> setDivider(Types types) {
        return this;
    }

    @Override
    public <V extends ItemViewHelper<T>> ListViewHelper<T> setDivider(V helper) {
        divider = helper;
        return this;
    }

    @Override
    public <V extends ItemViewHelper<T>> ListViewHelper<T> addHeader(V helper) {
        headers.add(helper);
        return this;
    }

    @Override
    public <V extends ItemViewHelper<T>> ListViewHelper<T> addFooter(V helper) {
        footers.add(helper);
        return this;
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            ViewHolderImpl viewHolder = (ViewHolderImpl) v.getTag();
            onItemClickListener.onItemClick(viewHolder.viewHelper, viewHolder.getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (onItemLongClickListener != null) {
            ViewHolderImpl viewHolder = (ViewHolderImpl) v.getTag();
            onItemLongClickListener.onItemLongClick(viewHolder.viewHelper, viewHolder.getAdapterPosition());
        }
        return false;
    }

    private class AdapterImpl extends RecyclerView.Adapter<ViewHolderImpl> {

        @Override
        public int getItemViewType(int position) {
            if (position < headers.size()) {
                return position;
            }
            position -= headers.size();
            if (itemFactories.size() > 0) {
                for (int i = 0; i < itemFactories.size(); i++) {
                    ItemFactoryWrapper wrapper = itemFactories.get(i);
                    try {
                        if (wrapper.filter == null || wrapper.filter.apply(ListViewHelperImpl.this, position)) {
                            return headers.size() + i;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return -1;
            }
            position -= itemFactories.size();
            if (position < footers.size()) {
                return headers.size() + itemFactories.size() + position;
            }
            return -1;
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType < headers.size()) {
                ItemViewHelper viewHelper = headers.get(viewType);
                return new ViewHolderImpl(viewHelper, viewHelper.onCreateView(parent));
            }
            viewType -= headers.size();
            if (viewType < itemFactories.size()) {
                ItemViewHelper itemViewHelper = itemFactories.get(viewType).factory.create(parent);
                return new ViewHolderImpl(itemViewHelper, itemViewHelper.onCreateView(parent));
            }
            viewType -= itemFactories.size();
            if (viewType < footers.size()) {
                ItemViewHelper viewHelper = footers.get(viewType);
                return new ViewHolderImpl(viewHelper, viewHelper.onCreateView(parent));
            }
            throw new IllegalArgumentException("viewType 无法对上");
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderImpl holder, int position) {
            if (listData != null)
                holder.viewHelper.onBindView(listData.get(position - headers.size()), position);
            else
                holder.viewHelper.onBindView(singleData, position);
        }

        @Override
        public int getItemCount() {
            if (itemCounter != null)
                return itemCounter.getItemCount();
            if (listData != null)
                return listData.size();
            return 0;
        }
    }

    private class ItemFactoryWrapper {
        private ItemViewFactory factory;
        private BiFunction<ListViewHelper<T>, Integer, Boolean> filter;

        public ItemFactoryWrapper(ItemViewFactory factory, BiFunction<ListViewHelper<T>, Integer, Boolean> filter) {
            this.factory = factory;
            this.filter = filter;
        }
    }

    private class ViewHolderImpl extends RecyclerView.ViewHolder {

        private final ItemViewHelper viewHelper;

        public ViewHolderImpl(ItemViewHelper viewHelper, View itemView) {
            super(itemView);
            this.viewHelper = viewHelper;
            itemView.setTag(R.id.tag_item_view_helper, ViewHolderImpl.this);
            itemView.setOnClickListener(ListViewHelperImpl.this);
            itemView.setOnLongClickListener(ListViewHelperImpl.this);
        }

    }
}
