package com.example.ldo.barcodeproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Date;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainMenu extends AppCompatActivity {
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        Button admin = (Button) findViewById(R.id.admin);
        if (CurrentSystemStatus.current_user.isAdmin()) admin.setVisibility(View.VISIBLE);
        else admin.setVisibility(View.INVISIBLE);
    }

    public void DoScan(View view)
    {
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        // intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
        //intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    public void DoReduction(View view)
    {
        Intent intent = new Intent(this, ReductionActivity.class);
        startActivity(intent);
    }

    public void DoDownload(View view)
    {

    }

    public void DoAdmin(View view)
    {
        Intent intent = new Intent(this, AdminWindow.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Выйти из системы?")
                .setMessage("Вы действительно хотите выйти?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        MainMenu.super.onBackPressed();
                    }
                }).create().show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    DatabaseAdapter adapter = new DatabaseAdapter(this);
                   adapter.open();
                    if(adapter.getScanBarCode(barcode.displayValue)==null) {
                        ScanBarCode scanbarcode = new ScanBarCode(barcode.displayValue, CurrentSystemStatus.current_user.getLogin(), new Date());
                        adapter.insert(scanbarcode);
                    }
                    else{
                        Toast toast = Toast.makeText(this, "Штрих-код уже был считан",Toast.LENGTH_LONG);
                        toast.show();
                    }
                    adapter.close();
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
