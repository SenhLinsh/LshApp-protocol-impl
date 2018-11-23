package com.linsh.protocol.impl.value;

import android.view.ViewGroup;

import com.linsh.protocol.Client;
import com.linsh.protocol.value.ColorType;
import com.linsh.protocol.value.SizeType;
import com.linsh.protocol.value.StatusType;
import com.linsh.protocol.value.TextType;
import com.linsh.protocol.value.Types;
import com.linsh.protocol.value.UsageType;
import com.linsh.protocol.value.ViewType;
import com.linsh.utilseverywhere.ScreenUtils;
import com.linsh.utilseverywhere.UnitConverseUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/23
 *    desc   :
 * </pre>
 */
public class TypesImpl implements Types {

    private final SizeType size;
    private final SizeType width;
    private final SizeType height;
    private final TextType text;
    private final ColorType color;
    private final ViewType view;
    private final UsageType usage;
    private final StatusType status;

    public TypesImpl(SizeType size, SizeType width, SizeType height, TextType text,
                     ColorType color, ViewType view, UsageType usage, StatusType status) {
        this.size = size;
        this.width = width;
        this.height = height;
        this.text = text;
        this.color = color;
        this.view = view;
        this.usage = usage;
        this.status = status;
    }

    @Override
    public SizeType size() {
        return size;
    }

    @Override
    public SizeType width() {
        return width;
    }

    @Override
    public SizeType height() {
        return height;
    }

    @Override
    public TextType text() {
        return text;
    }

    @Override
    public ColorType color() {
        return color;
    }

    @Override
    public ViewType view() {
        return view;
    }

    @Override
    public UsageType usage() {
        return usage;
    }

    @Override
    public StatusType status() {
        return status;
    }

    @Override
    public int guessWidth() {
        SizeType type = width != null ? width : size;
        switch (type) {
            case FULL_SCREEN:
                return ScreenUtils.getScreenWidth();
            case HALF_SCREEN:
                return ScreenUtils.getScreenWidth() / 2;
            case THIRD_SCREEN:
                return ScreenUtils.getScreenWidth() / 3;
            case QUARTER_SCREEN:
                return ScreenUtils.getScreenWidth() / 4;
            case MATCH_PARENT:
                return ViewGroup.LayoutParams.MATCH_PARENT;
            case WRAP_CONTENT:
                return ViewGroup.LayoutParams.WRAP_CONTENT;
            case AS_HEIGHT:
                return guessHeight();
        }
        switch (view) {
            case DIALOG:
                return UnitConverseUtils.dp2px(200);
        }
        switch (usage) {
            case DIVIDER:
                if (status == StatusType.VERTICAL)
                    return UnitConverseUtils.dp2px(0.75f);
                break;
        }
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public int guessHeight() {
        SizeType type = width != null ? height : size;
        switch (type) {
            case FULL_SCREEN:
                return ScreenUtils.getScreenHeight();
            case HALF_SCREEN:
                return ScreenUtils.getScreenHeight() / 2;
            case THIRD_SCREEN:
                return ScreenUtils.getScreenHeight() / 3;
            case QUARTER_SCREEN:
                return ScreenUtils.getScreenHeight() / 4;
            case MATCH_PARENT:
                return ViewGroup.LayoutParams.MATCH_PARENT;
            case WRAP_CONTENT:
                return ViewGroup.LayoutParams.WRAP_CONTENT;
            case AS_WIDTH:
                if (width != SizeType.AS_HEIGHT)
                    return guessWidth();
                break;
        }
        switch (view) {
            case DIALOG:
                return ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        switch (usage) {
            case DIVIDER:
                if (status != StatusType.VERTICAL)
                    return UnitConverseUtils.dp2px(0.75f);
                break;
        }
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public int guessMargin() {
        switch (view) {
            case DIALOG:
                return UnitConverseUtils.dp2px(32);
            case TOAST:
            case POPUP:
                return UnitConverseUtils.dp2px(32);
        }
        return 0;
    }

    @Override
    public int guessPadding() {
        switch (view) {
            case DIALOG:
                return UnitConverseUtils.dp2px(24);
            case TOAST:
            case POPUP:
            case ITEM:
                return UnitConverseUtils.dp2px(16);
        }
        return 0;
    }

    @Override
    public int guessTextSize() {
        switch (text) {
            case HUGE:
                return Client.value().textSize().superTitle() * 3 / 2;
            case SUPER_TITLE:
                return Client.value().textSize().superTitle();
            case TITLE:
                return Client.value().textSize().title();
            case SUBTITLE:
                return Client.value().textSize().subtitle();
            case SUPER_TEXT:
                return Client.value().textSize().superText();
            case TEXT:
                return Client.value().textSize().text();
            case SUBTEXT:
                return Client.value().textSize().subtext();
            case SUBTEXT2:
                return Client.value().textSize().subtext() - UnitConverseUtils.sp2px(2);
            case SUBTEXT3:
                return Client.value().textSize().subtext() - UnitConverseUtils.sp2px(4);
            case LITTLE:
                return Client.value().textSize().littleText();
        }
        switch (view) {
            case ITEM:
                return Client.value().textSize().text();
            case TOAST:
            case POPUP:
                return Client.value().textSize().subtext();
        }
        return 0;
    }

    @Override
    public int guessColor() {
        switch (color) {
            case MAIN:
                return Client.value().color().main();
            case MAIN_DARK:
                return Client.value().color().mainDark();
            case MAIN_LIGHT:
                return Client.value().color().mainLight();
        }
        switch (status) {
            case DISABLED:
                return Client.value().color().disabled();
            case SUCCESS:
                return Client.value().color().success();
            case WARNING:
                return Client.value().color().warning();
            case ERROR:
                return Client.value().color().error();
        }
        switch (usage) {
            case LINK:
                return Client.value().color().link();
        }
        switch (text) {
            case SUPER_TITLE:
            case TITLE:
                return Client.value().color().title();
            case SUBTITLE:
                return Client.value().color().subtitle();
            case SUPER_TEXT:
            case TEXT:
            case LITTLE:
                return Client.value().color().text();
            case SUBTEXT:
                return Client.value().color().subtext();
            case SUBTEXT2:
                return Client.value().color().subtext();
            case SUBTEXT3:
                return Client.value().color().subtext();
        }
        return 0;
    }

    @Override
    public int guessBgColor() {
        switch (view) {
            case STATUS_BAR:
                return Client.value().color().mainDark();
            case TOOL_BAR:
            case DIALOG:
            case TOAST:
            case POPUP:
                return Client.value().color().main();
        }
        switch (usage) {
            case DIVIDER:
                return Client.value().color().divider();
        }
        switch (status) {
            case DISABLED:
                return Client.value().color().disabled();
        }
        return 0;
    }

    @Override
    public int guessTextColor() {
        switch (text) {
            case SUPER_TITLE:
            case TITLE:
                return Client.value().color().title();
            case SUBTITLE:
                return Client.value().color().subtitle();
            case SUPER_TEXT:
            case TEXT:
            case LITTLE:
                return Client.value().color().text();
            case SUBTEXT:
                return Client.value().color().subtext();
            case SUBTEXT2:
                return Client.value().color().subtext();
            case SUBTEXT3:
                return Client.value().color().subtext();
        }
        return Client.value().color().text();
    }

    static class Builder implements Types.Builder {
        private SizeType size;
        private SizeType width;
        private SizeType height;
        private TextType text;
        private ColorType color;
        private ViewType view;
        private UsageType usage;
        private StatusType status;

        public Builder size(SizeType size) {
            this.size = size;
            return this;
        }

        public Builder width(SizeType width) {
            this.width = width;
            return this;
        }

        public Builder height(SizeType height) {
            this.height = height;
            return this;
        }

        public Builder text(TextType text) {
            this.text = text;
            return this;
        }

        public Builder color(ColorType color) {
            this.color = color;
            return this;
        }

        public Builder view(ViewType view) {
            this.view = view;
            return this;
        }

        public Builder usage(UsageType usage) {
            this.usage = usage;
            return this;
        }

        public Builder status(StatusType status) {
            this.status = status;
            return this;
        }

        public Types build() {
            return new TypesImpl(size, width, height, text, color, view, usage, status);
        }
    }
}
