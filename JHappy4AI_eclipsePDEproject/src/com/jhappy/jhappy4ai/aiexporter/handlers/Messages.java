package com.jhappy.jhappy4ai.aiexporter.handlers;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.jhappy.jhappy4ai.aiexporter.handlers.messages"; //$NON-NLS-1$
    
    public static String Handler_DialogTitle;
    public static String Handler_DialogMsg;
    public static String Handler_DialogErrorNan;
    public static String Handler_DialogErrorNegative;
    public static String Handler_SaveDialogTitle;
    public static String Handler_DefaultFileName;
    public static String Handler_FileHeader;
    public static String Handler_TOCHeader;
    public static String Handler_LimitReachedWarning;
    public static String Handler_SuccessTitle;
    public static String Handler_SuccessMsg;
    public static String Handler_SuccessMsgLimit;
    public static String Handler_ErrorTitle;
    public static String Handler_ErrorRead;
    public static String PrefPage_Description;
    public static String PrefPage_AllowedExtensions;
    public static String PrefPage_IgnoredExtensions;
    public static String PrefPage_MaxChars;
    public static String PrefPage_IgnoredFolders;
    
    public static String PrefPage_GroupOutput;
    public static String PrefPage_GroupFolder;
    public static String PrefPage_GroupFilter;
    public static String PrefPage_EncodingNotice;
    public static String Handler_ErrorSaveTitle;
  
    public static String Handler_EncodingNote;
    
    public static String Handler_HowToUse;

    
    
    
    
    
    
    
    
    
    

    static {
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {}
}