package com.clb.cliembing.file.entity;

public enum FileTypeEnum {
    PHOTO('P', "사진", "jpg,jpeg,png,gif,webp"),
    FILE('F', "일반파일", "pdf,doc,docx,xls,xlsx,txt,zip,rar"),
    PROFILE('R', "프로필사진", "jpg,jpeg,png,gif"),
    VIDEO('V', "동영상", "mp4,avi,mov,wmv,flv,mkv");

    private final char code;
    private final String description;
    private final String allowedExtensions;

    FileTypeEnum(char code, String description, String allowedExtensions) {
        this.code = code;
        this.description = description;
        this.allowedExtensions = allowedExtensions;
    }

    public char getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getAllowedExtensions() {
        return allowedExtensions;
    }

    public static FileTypeEnum fromCode(char code) {
        for (FileTypeEnum type : FileTypeEnum.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown file type code: " + code);
    }

    public static FileTypeEnum fromExtension(String extension) {
        String ext = extension.toLowerCase();

        for (FileTypeEnum type : FileTypeEnum.values()) {
            if (type.getAllowedExtensions().contains(ext)) {
                return type;
            }
        }
        return FILE;
    }

    public boolean isAllowedExtension(String extension) {
        return this.allowedExtensions.contains(extension.toLowerCase());
    }
}

