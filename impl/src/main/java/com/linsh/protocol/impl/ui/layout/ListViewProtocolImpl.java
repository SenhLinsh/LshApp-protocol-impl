package com.linsh.protocol.impl.ui.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.linsh.protocol.Client;
import com.linsh.protocol.impl.R;
import com.linsh.protocol.ui.layout.ItemCounter;
import com.linsh.protocol.ui.layout.ListViewProtocol;
import com.linsh.protocol.ui.layout.OnBindItemViewListener;
import com.linsh.protocol.ui.layout.OnItemClickListener;
import com.linsh.protocol.ui.layout.OnItemLongClickListener;
import com.linsh.protocol.ui.view.ViewProtocol;

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
class ListViewProtocolImpl<T> implements ListViewProtocol<T>, View.OnClickListener, View.OnLongClickListener {

    private final Context context;
    private final RecyclerView recyclerView;

    private List<T> listData;
    private T singleData;
    private Object[] extraData;
    private ItemCounter itemCounter;

    private List<ItemInfo<T>> items = new ArrayList<>();
    private List<ItemInfo<T>> headers = new ArrayList<>();
    private List<ItemInfo<T>> footers = new ArrayList<>();
    private ItemInfo<T> divider;

    private OnItemClickListener<T> onItemClickListener;
    private OnItemLongClickListener<T> onItemLongClickListener;

    public ListViewProtocolImpl(Context context) {
        this(Client.ui().view().view(new RecyclerView(context)).getView());
    }

    public ListViewProtocolImpl(RecyclerView recyclerView) {
        this.context = recyclerView.getContext();
        this.recyclerView = recyclerView;
        this.recyclerView.setAdapter(new AdapterImpl());
    }

    @Override
    public RecyclerView getView() {
        return recyclerView;
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
    public T getItemData(int position) {
        if (listData != null) {
            return listData.get(position - headers.size());
        }
        return null;
    }

    @Override
    public <U> U getExtraData() {
        return (U) extraData[0];
    }

    @Override
    public <U> U getExtraData(int index) {
        return (U) extraData[index];
    }

    @Override
    public ListViewProtocol<T> setData(List<T> data) {
        listData = data;
        return this;
    }

    @Override
    public ListViewProtocol<T> setData(T[] data) {
        listData = Arrays.asList(data);
        return this;
    }

    @Override
    public ListViewProtocol<T> setSingleData(T data) {
        singleData = data;
        return this;
    }

    @Override
    public ListViewProtocol<T> setExtraData(Object... data) {
        extraData = data;
        return this;
    }

    @Override
    public ListViewProtocol<T> setExtraData(int index, Object data) {
        if (index >= extraData.length) {
            extraData = Arrays.copyOf(extraData, index + 1);
        }
        extraData[index] = data;
        return this;
    }

    @Override
    public ListViewProtocol<T> setCounter(ItemCounter counter) {
        itemCounter = counter;
        return this;
    }

    @Override
    public <V extends ViewProtocol> ListViewProtocol<T> addItemView(Class<V> itemHelper) {
        addItemView(itemHelper, null, null);
        return this;
    }

    @Override
    public <V extends ViewProtocol> ListViewProtocol<T> addItemView(Class<V> itemHelper, OnBindItemViewListener<T, V> listener) {
        addItemView(itemHelper, listener, null);
        return this;
    }

    @Override
    public <V extends ViewProtocol> ListViewProtocol<T> addItemView(Class<V> itemHelper, OnBindItemViewListener<T, V> listener, BiFunction<ListViewProtocol<T>, Integer, Boolean> filter) {
        items.add(new ItemInfo<>(itemHelper, listener, filter));
        return this;
    }

    @Override
    public <V extends ViewProtocol> ListViewProtocol<T> addHeader(Class<V> itemHelper) {
        addHeader(itemHelper, null);
        return this;
    }

    @Override
    public <V extends ViewProtocol> ListViewProtocol<T> addHeader(Class<V> itemHelper, OnBindItemViewListener<T, V> listener) {
        items.add(new ItemInfo<>(itemHelper, listener, null));
        return this;
    }

    @Override
    public <V extends ViewProtocol> ListViewProtocol<T> addFooter(Class<V> itemHelper) {
        addFooter(itemHelper, null);
        return this;
    }

    @Override
    public <V extends ViewProtocol> ListViewProtocol<T> addFooter(Class<V> itemHelper, OnBindItemViewListener<T, V> listener) {
        items.add(new ItemInfo<>(itemHelper, listener, null));
        return this;
    }

    @Override
    public <V extends ViewProtocol> ListViewProtocol<T> setDivider(Class<V> itemHelper) {
        divider = new ItemInfo<>(itemHelper, null, null);
        return this;
    }

    @Override
    public <V extends ViewProtocol> ListViewProtocol<T> setDivider(Class<V> itemHelper, OnBindItemViewListener<T, V> listener) {
        divider = new ItemInfo<>(itemHelper, listener, null);
        return this;
    }

    @Override
    public ListViewProtocol<T> setDivider(int gap, int color) {
        // TODO: 2018/12/2
        return this;
    }

    @Override
    public ListViewProtocol<T> setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
        return this;
    }

    @Override
    public ListViewProtocol<T> setOnItemLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            ViewHolderImpl viewHolder = (ViewHolderImpl) v.getTag(R.id.tag_view_holder_impl);
            onItemClickListener.onItemClick(ListViewProtocolImpl.this, viewHolder.viewProtocol, viewHolder.getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (onItemLongClickListener != null) {
            Object tag = v.getTag(R.id.tag_view_holder_impl);
            if (tag instanceof ViewHolderImpl) {
                ViewHolderImpl viewHolder = (ViewHolderImpl) tag;
                onItemLongClickListener.onItemLongClick(ListViewProtocolImpl.this, viewHolder.viewProtocol, viewHolder.getAdapterPosition());
            }
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
            if (items.size() > 0) {
                for (int i = 0; i < items.size(); i++) {
                    ItemInfo<T> itemInfo = items.get(i);
                    try {
                        if (itemInfo.filter == null || itemInfo.filter.apply(ListViewProtocolImpl.this, position)) {
                            return headers.size() + i;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return -1;
            }
            position -= items.size();
            if (position < footers.size()) {
                return headers.size() + items.size() + position;
            }
            return -1;
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType < headers.size()) {
                ItemInfo<T> itemInfo = headers.get(viewType);
                ViewProtocol protocol = Client.ui().view().getProtocol(recyclerView, itemInfo.protocol, false);
                return new ViewHolderImpl(protocol, itemInfo.listener, ListViewProtocolImpl.this);
            }
            viewType -= headers.size();
            if (viewType < items.size()) {
                ItemInfo<T> itemInfo = items.get(viewType);
                ViewProtocol protocol = Client.ui().view().getProtocol(recyclerView, itemInfo.protocol, false);
                return new ViewHolderImpl(protocol, itemInfo.listener, ListViewProtocolImpl.this);
            }
            viewType -= items.size();
            if (viewType < footers.size()) {
                ItemInfo<T> itemInfo = footers.get(viewType);
                ViewProtocol protocol = Client.ui().view().getProtocol(recyclerView, itemInfo.protocol, false);
                return new ViewHolderImpl(protocol, itemInfo.listener, ListViewProtocolImpl.this);
            }
            throw new IllegalArgumentException("viewType 无法对上");
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderImpl holder, int position) {
            if (holder.listener != null) {
                holder.listener.onBindItemView(ListViewProtocolImpl.this, holder.viewProtocol, position);
            }
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

    private static class ViewHolderImpl extends RecyclerView.ViewHolder {

        private final ViewProtocol viewProtocol;
        private final OnBindItemViewListener listener;

        ViewHolderImpl(ViewProtocol viewProtocol, OnBindItemViewListener listener, ListViewProtocolImpl listViewProtocol) {
            super(viewProtocol.getView());
            this.viewProtocol = viewProtocol;
            this.listener = listener;
            itemView.setTag(R.id.tag_view_holder_impl, ViewHolderImpl.this);
            itemView.setOnClickListener(listViewProtocol);
            itemView.setOnLongClickListener(listViewProtocol);
        }

    }

    private static class ItemInfo<T> {
        private final Class<? extends ViewProtocol> protocol;
        private final OnBindItemViewListener listener;
        private final BiFunction<ListViewProtocol<T>, Integer, Boolean> filter;

        private ItemInfo(Class<? extends ViewProtocol> protocol, OnBindItemViewListener listener, BiFunction<ListViewProtocol<T>, Integer, Boolean> filter) {
            this.protocol = protocol;
            this.listener = listener;
            this.filter = filter;
        }
    }
}
