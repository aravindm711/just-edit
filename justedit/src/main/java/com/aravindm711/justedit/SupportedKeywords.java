package com.aravindm711.justedit;

import java.util.ArrayList;
import java.util.Arrays;

public class SupportedKeywords {

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
                "cout", "cin" };
    
    private String[] brackets = { "{", "(" };
    private String[] bCompletions = { "}", ")" };

    public String[] getSupportedLanguages() {
        return supportedLanguages;
    }

    public String[] getJavaKeywords() {
        return java;
    }

    public String[] getCppKeywords() {
        return cpp;
    }

    public ArrayList<String> getbracketCompletions() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(bCompletions));
        return arrayList;
    }

    public ArrayList<String> getbrackets() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(brackets));
        return arrayList;
    }

    public ArrayList<String> setKeywords(String[] arr) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(arr));
        return arrayList;
    }

}
