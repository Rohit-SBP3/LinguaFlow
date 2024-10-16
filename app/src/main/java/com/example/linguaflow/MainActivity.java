package com.example.linguaflow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.mlkit.nl.translate.TranslateLanguage;

public class MainActivity extends AppCompatActivity {

    // Widgets
    private AutoCompleteTextView sourceSelection;
    private AutoCompleteTextView targetSelection;

    TextInputEditText sourceText;
    MaterialButton translate;
    MaterialTextView targetText;


    // Source Array of Strings - Spinner's Data
    String[] sourceLanguages = {"from","Russian","English","Afrikaans","Arabic","Hindi","Bengali","Belarusian","Catalan","Urdu","Chinese","French"};
    String[] targetLanguages = {"to","Russian","English","Afrikaans","Arabic","Hindi","Bengali","Belarusian","Catalan","Urdu","Punjabi","French"};


    // Permissions
    private static final int REQUEST_CODE = 1;
    String languageCode, sourceLanguageCode, targetLanguageCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceSelection = findViewById(R.id.source_spinner);
        targetSelection = findViewById(R.id.target_spinner);

        sourceText = findViewById(R.id.edt_source);
        translate = findViewById(R.id.btn_translate);
        targetText = findViewById(R.id.tv_target);


        //Spinner source
        sourceSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourceLanguageCode = GetLanguageCode(sourceLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter sourceAdapter = new ArrayAdapter(this,R.layout.spinner_item,sourceLanguages);
        sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSelection.setAdapter(sourceAdapter);

        //Spinner target
        targetSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                targetLanguageCode = GetLanguageCode(targetLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter targetAdapter = new ArrayAdapter(this,R.layout.spinner_item,targetLanguages);
        targetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        targetSelection.setAdapter(targetAdapter);




    }

    private String GetLanguageCode(String language) {
        String languageCode;
        switch(language){
            case "Russian":
                languageCode = TranslateLanguage.RUSSIAN;
            case "English":
                languageCode = TranslateLanguage.ENGLISH;
                break;
            case "Afrikaans":
                languageCode = TranslateLanguage.AFRIKAANS;
                break;
            case "Arabic":
                languageCode = TranslateLanguage.ARABIC;
                break;
            case "Hindi":
                languageCode = TranslateLanguage.HINDI;
                break;
            case "Bengali":
                languageCode = TranslateLanguage.BENGALI;
                break;
            case "Belarusian":
                languageCode = TranslateLanguage.BELARUSIAN;
                break;
            case "Catalan":
                languageCode = TranslateLanguage.CATALAN;
                break;
            case "Urdu":
                languageCode = TranslateLanguage.URDU;
                break;
            case "Chinese":
                languageCode = TranslateLanguage.CHINESE;
                break;
            case "French":
                languageCode = TranslateLanguage.FRENCH;
                break;
            default:
                languageCode = "";
        }
        return languageCode;
    }
}