package com.just8.apps.criminalintent;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * BB
 * Created by kandinski on 2015-08-02.
 */
public class CriminalIntentJSONSerializer { private static final String TAG = "JSONSerializer";
    private Context mContext;
    private String mFilename;
    private String mExtStorageDirectoryPath;

    public CriminalIntentJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;

        try {
            FileInputStream in = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))   // Load from sd card if available.
                in = new FileInputStream(Environment.getExternalStorageDirectory().getPath() +"/"+ mFilename);
            else
                in = mContext.openFileInput(mFilename);

            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
                // Line breaks are omitted and irrelevant
                jsonString.append(line);

            // Parse the JSON using JSONTokener.
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            // Build the array of crimes from JSONObjects
            for (int i = 0; i <array.length(); ++i)
                crimes.add(new Crime(array.getJSONObject(i)));
        } catch (FileNotFoundException e) {
            // Starting without a file, so ignore.
        } finally {
            if (reader != null)
                reader.close();
        }
        return crimes;
    }


    public void saveCrimes(ArrayList<Crime> crimes)
            throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (Crime c: crimes)
            array.put(c.toJSON());

        // Write the file to disk
        OutputStreamWriter writer = null;
        try {
            FileOutputStream out = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                // Save to sd card if available.
                out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() +"/"+ mFilename);
            else
                out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }


}