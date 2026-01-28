package com.example.uhf.activity;

import com.rscja.deviceapi.entity.UHFTAGInfo;

public class NamedTag {
    public UHFTAGInfo uhftagInfo;
    public String name;

    public NamedTag(UHFTAGInfo uhftagInfo, String name) {
        this.uhftagInfo = uhftagInfo;
        this.name = name;
    }
}
