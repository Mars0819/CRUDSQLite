package com.example.crudsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupDialogClickListener{
    Button btnAddCountry;
    Button btnsave;
    EditText edtName,edtPopulation;
    RecyclerView rvCountry;
    CountryAdapter countryAdapter;
    SQLiteDBHandler dbHandler;
    List<CountryModel> countries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler =new SQLiteDBHandler(getApplicationContext());
        countries =dbHandler.getAllCountries();;


        rvCountry= (RecyclerView) findViewById(R.id.rvCountry);
        rvCountry.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        countryAdapter =new CountryAdapter(countries, this);
        rvCountry.setAdapter(countryAdapter);
        btnAddCountry = (Button) findViewById(R.id.btnAddCountry);

        btnAddCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater =(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.dialog_input_country,null);
                AlertDialog.Builder adBuilder =new AlertDialog.Builder(MainActivity.this);
                adBuilder.setView(popupView);
                btnsave = (Button) popupView.findViewById(R.id.btnSave);
                edtName = (EditText) popupView.findViewById(R.id.edtName);
                edtPopulation = (EditText) popupView.findViewById(R.id.edtPopulation);

                AlertDialog dialog = adBuilder.create();
                dialog.setCancelable(true);
                dialog.show();

                btnsave.setOnClickListener(new View.OnClickListener()  {
                    @Override
                    public void onClick(View view){

                        if (edtName.getText().toString().length()==0){
                            edtName.setError("Masukan Nama Negara");

                        }
                        if (edtPopulation.getText().toString().length()==0){
                            edtPopulation.setError("Masukan Populasi");
                        }
                        else{
                            CountryModel newCountry = new CountryModel(0, edtName.getText().toString(),
                                    Integer.parseInt(edtPopulation.getText().toString()));
                            dbHandler.addCountry(newCountry);
                            dialog.dismiss();
                            countries = dbHandler.getAllCountries();
                            countryAdapter.updateAndRefreshData(countries);
                            Toast.makeText(MainActivity.this, "Data Berhasil Di Simpan", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    @Override
    public void onEdit(int position) {
        //ambil object country yang lama
        CountryModel allCountry = countries.get(position);

        //Tampilkan popup
        LayoutInflater inflater =(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.dialog_input_country,null);
        AlertDialog.Builder adBuilder =new AlertDialog.Builder(MainActivity.this);
        adBuilder.setView(popupView);
        btnsave = (Button) popupView.findViewById(R.id.btnSave);
        edtName = (EditText) popupView.findViewById(R.id.edtName);
        edtPopulation = (EditText) popupView.findViewById(R.id.edtPopulation);

        AlertDialog dialog = adBuilder.create();
        dialog.setCancelable(true);
        dialog.show();

        //isi edtName oldCountry.getName();
        edtName.setText(allCountry.getName());
        //isi edtPopulation dengan oldCountry.getName();
        edtPopulation.setText(String.valueOf(allCountry.getPopulation()));
        btnsave.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view){

                if (edtName.getText().toString().length()==0){
                    edtName.setError("Masukan Nama Negara");

                }
                if (edtPopulation.getText().toString().length()==0){
                    edtPopulation.setError("Masukan Populasi");
                }
                else{
                    CountryModel allCountry = countries.get(position);
                    ;
                    CountryModel newCountry = new CountryModel(allCountry.getId(), edtName.getText().toString(),
                            Integer.parseInt(edtPopulation.getText().toString()));
                    dbHandler.updateCountry(newCountry);
                    dialog.dismiss();
                    countries = dbHandler.getAllCountries();
                    countryAdapter.updateAndRefreshData(countries);
                    Toast.makeText(MainActivity.this, "Data Berhasil Di Simpan", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    @Override
    public void onDelete(int position) {
        dbHandler.deleteCountry(countries.get(position));
        countryAdapter.removeItem(position);
    }
}