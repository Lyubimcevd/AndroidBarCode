package com.example.ldo.barcodeproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

public class ReductionActivity extends AppCompatActivity {
    ArrayAdapter<ScanBarCode> arrayAdapter;
    private ListView codesList;
    ArrayList<ScanBarCode> selectedCode = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reduction);
        codesList = (ListView) findViewById(R.id.codesList);
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        List<ScanBarCode> scanbarcodes = adapter.getScanBarCodes();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, scanbarcodes);
        codesList.setAdapter(arrayAdapter);
        adapter.close();
        codesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // получаем нажатый элемент
                ScanBarCode scancode = arrayAdapter.getItem(position);
                if(codesList.isItemChecked(position)==true){
                    selectedCode.add(scancode);
                }
                else{

                    selectedCode.remove(scancode);
                }
            }
        });
    }
    public void remove(View view) {
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();
        for(int i=0; i< selectedCode.size();i++){
            adapter.delete(selectedCode.get(i).getCode());
        }
        codesList.clearChoices();
        // очищаем массив выбраных объектов
        selectedCode.clear();
        List<ScanBarCode> scanbarcodes = adapter.getScanBarCodes();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, scanbarcodes);
        codesList.setAdapter(arrayAdapter);
        adapter.close();
    }
}
