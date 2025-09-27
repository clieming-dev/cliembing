package com.clb.cliembing.file.entity;

import lombok.Getter;

import java.util.Locale;

@Getter
public enum ImageCategory {
    ICON('I', 1 * 1024 * 1024L, true, CompressionLevel.HIGH),
    PROFILE('P', 2 * 1024 * 1024L, true, CompressionLevel.MEDIUM),
    FEED('F', 5 * 1024 * 1024L, false, CompressionLevel.LIGHT),
    BOARD('B', 5 * 1024 * 1024L, false, CompressionLevel.LIGHT);

    private final char code;
    private final long maxBytes;
    private final boolean compressIfOver;
    private final CompressionLevel compression;

    ImageCategory(char code, long maxBytes, boolean compressIfOver, CompressionLevel compression) {
        this.code = code;
        this.maxBytes = maxBytes;
        this.compressIfOver = compressIfOver;
        this.compression = compression;
    }

    public enum CompressionLevel { HIGH, MEDIUM, LIGHT }

    public static boolean isAllowedExtension(String ext) {
        if (ext == null) return false;
        String e = ext.toLowerCase(Locale.ROOT);
        return e.equals("jpg") || e.equals("jpeg") || e.equals("png") || e.equals("webp");
    }

    public static boolean isAllowedMime(String mime) {
        if (mime == null) return false;
        return mime.equals("image/jpeg") || mime.equals("image/png") || mime.equals("image/webp");
    }

    public static ImageCategory fromCode(char c) {
        for (var v : values()) if (v.code == c) return v;
        throw new IllegalArgumentException("Unknown category code: " + c);
    }
}
