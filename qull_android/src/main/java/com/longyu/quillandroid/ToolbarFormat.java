package com.longyu.quillandroid;

/**
 * @Author: com.longyu
 * @CreateDate: 2021/4/7 15:16
 * @Description:
 */
public class ToolbarFormat {
    private Format format;
    private Object[] whitelistValues;

    public ToolbarFormat(Format format) {
        this.format = format;
    }

    public ToolbarFormat(Format format, Object[] whitelistValues) {
        this.format = format;
        this.whitelistValues = whitelistValues;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Object[] getWhitelistValues() {
        return whitelistValues;
    }

    public void setWhitelistValues(Object[] whitelistValues) {
        this.whitelistValues = whitelistValues;
    }
}
