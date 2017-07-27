/*
 * This file is part of the Gerberoid project.
 *
 * Copyright (C) 2017 Marcus Comstedt <marcus@mc.pp.se>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.pp.mc.android.Gerberoid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.widget.Spinner;

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;


public class MainActivity extends Activity {


    private static final int REQUEST_GERBER = 10001;
    private static final int REQUEST_DRILL  = 10002;

    private GerbviewFrame gerbviewFrame;
    private Spinner layerSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gerbviewFrame = (GerbviewFrame) findViewById(R.id.gerbview_frame);
        if (gerbviewFrame != null) {
	    gerbviewFrame.onCreate();
	    gerbviewFrame.onRestoreInstanceState((savedInstanceState == null? new Bundle() : savedInstanceState));
	}
	layerSpinner = (Spinner) findViewById(R.id.layer_spinner);
	if (layerSpinner != null) {
	    layerSpinner.setAdapter(new LayerSpinnerAdapter(this, gerbviewFrame));
	}
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
	super.onSaveInstanceState(savedInstanceState);
	if (gerbviewFrame != null)
	    gerbviewFrame.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy ()
    {
	layerSpinner = null;
	if (gerbviewFrame != null) {
	    gerbviewFrame.onDestroy();
	    gerbviewFrame = null;
	}
	super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	switch (item.getItemId()) {
	case R.id.action_gerber:
	    SelectFile(REQUEST_GERBER, R.string.file_request_gerber,
		       "application/vnd.gerber");
	    break;
	case R.id.action_drill:
	    SelectFile(REQUEST_DRILL, R.string.file_request_drill,
		       "application/octet-stream");
	    break;
	case R.id.action_clear:
	    gerbviewFrame.Clear_DrawLayers();
	    break;
	}
	return true;
    }

    private void LoadGerber(File file)
    {
	gerbviewFrame.Erase_Current_DrawLayer();
	gerbviewFrame.Read_GERBER_File(file.getAbsolutePath());
    }

    private void LoadDrill(File file)
    {
	gerbviewFrame.Erase_Current_DrawLayer();
	gerbviewFrame.Read_EXCELLON_File(file.getAbsolutePath());
    }

    private void SelectFile(int requestCode, int titleResource,
			    String mimeType)
    {
	final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	intent.setType(mimeType);
	intent.addCategory(Intent.CATEGORY_OPENABLE);
	startActivityForResult(Intent.createChooser(intent,
						    getResources().getString(titleResource)),
			       requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
	switch (requestCode) {
	case REQUEST_GERBER:
	case REQUEST_DRILL:
	    if (resultCode == RESULT_OK) {
		final Uri uri = data.getData();
		final File file = FileUtils.getFile(this, uri);
		if (file != null) {
		    if (requestCode == REQUEST_GERBER)
			LoadGerber(file);
		    else
			LoadDrill(file);
		}
	    }
	    break;
	}
    }
}
