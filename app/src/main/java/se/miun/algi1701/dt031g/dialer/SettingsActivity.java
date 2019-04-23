package se.miun.algi1701.dt031g.dialer;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import java.io.File;
import java.io.FileFilter;


public class SettingsActivity extends PreferenceActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        init();
    }

    private void init(){
        ListPreference soundPref = (ListPreference) findPreference("voice_sounds");

        File[] dirs = getChildren(Constants.EXTERNAL_STORAGE_PATH);

        if(dirs != null && dirs.length > 0){
            int size = dirs.length;
            String[] entries = new String[size];
            String[] values = new String[size];

            for(int i = 0; i < size; i++){
                entries[i] = dirs[i].getName();
                values[i] = Constants.EXTERNAL_STORAGE_PATH + "/" + dirs[i].getName();
            }

            soundPref.setEntries(entries);
            soundPref.setEntryValues(values);
        }


    }

    private File[] getChildren(String parentPath){
        File[] dirs = null;
        File dir = new File(parentPath);
        if (dir.exists() && dir.isDirectory()) {

            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File dirToFilter) {
                    return dirToFilter.isDirectory();
                }
            };
            dirs = dir.listFiles(fileFilter);
        }
        return dirs;
    }

}
