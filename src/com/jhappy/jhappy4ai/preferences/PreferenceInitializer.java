package com.jhappy.jhappy4ai.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.jhappy.jhappy4ai.Activator;
import com.jhappy.jhappy4ai.aiexporter.handlers.Handler;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        
        // 
        store.setDefault(Handler.PREF_ALLOWED_EXTENSIONS, "java,xml,properties,md,txt,json,html,js,css");
        store.setDefault(Handler.PREF_EXCLUDED_EXTENSIONS, "class,exe,dll,png,jpg,gif");
        
        // 最大出力容量（MB）を設定
        store.setDefault(Handler.PREF_MAX_CHARS, "10");
        
        store.setDefault(Handler.PREF_IGNORED_FOLDERS, ".settings,.git,bin,target,build,node_modules");
    }
}