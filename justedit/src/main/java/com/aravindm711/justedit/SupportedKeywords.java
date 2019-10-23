package com.aravindm711.justedit;

public class SupportedKeywords {

    public SupportedKeywords() {}

    private String[] supportedLanguages = {".cpp",".java"};

    private String[] java = {"abstract", "assert", "boolean",
            "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "extends", "false",
            "final", "finally", "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "System", "out", "print()", "println()",
            "new", "null", "package", "private", "protected", "public", "interface",
            "long", "native", "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "true",
            "try", "void", "volatile", "while", "String"};

    private String[] cpp = { "auto", "const", "double", "float", "int", "short",
                "struct", "unsigned", "break", "continue", "else", "for", "long", "signed",
                "switch", "void", "case", "default", "enum", "goto", "register", "sizeof",
                "typedef", "volatile", "char", "do", "extern", "if", "return", "static",
                "union", "while", "asm", "dynamic_cast", "namespace", "reinterpret_cast", "try",
                "bool", "explicit", "new", "static_cast", "typeid", "catch", "false", "operator",
                "template", "typename", "class", "friend", "private", "this", "using", "const_cast",
                "inline", "public", "throw", "virtual", "delete", "mutable", "protected", "true", "wchar_t",
                "cout", "cin", "include", "iostream", "string"};

    public String[] getSupportedLanguages() {
        return supportedLanguages;
    }

    public boolean supportedLanguage(String fileExtension) {
        for (String language: supportedLanguages) {
            if (language == fileExtension) {
                return true;
            }
        }

        return false;
    }

    public String[] getJavaKeywords() {
        return java;
    }

    public String[] getCppKeywords() {
        return cpp;
    }

    public String[] getKeywords(String fileExtension) {
        if (fileExtension == ".cpp") return cpp;
        else if (fileExtension == ".java") return java;
        else {
            String[] empty = {};
            return empty;
        }
    }

}
