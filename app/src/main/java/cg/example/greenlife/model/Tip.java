package cg.example.greenlife.model;

import java.util.UUID;

public class Tip {
    private UUID id;

    private String tipText; //TODO: not null text

    private String moreInfo;

    public Tip() {
    }

    public Tip(String tipText, String moreInfo) {
        this.tipText = tipText;
        this.moreInfo = moreInfo;
    }

    public UUID getId() {
        return id;
    }

    public String getTipText() {
        return tipText;
    }

    public void setTipText(String tipText) {
        this.tipText = tipText;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }
}
