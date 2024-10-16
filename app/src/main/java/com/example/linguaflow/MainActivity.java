package com.example.linguaflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class MainActivity extends AppCompatActivity {

    // Widgets
    private Spinner sourceSelection;
    private Spinner targetSelection;

    TextInputEditText sourceText;
    MaterialButton translate;
    MaterialTextView targetText;

    String[] sourceLanguages = {"Source Language","Russian","English","Afrikaans","Arabic","Hindi","Bengali","Belarusian","Catalan","Urdu","Chinese","French"};
    String[] targetLanguages = {"Target Language","Russian","English","Afrikaans","Arabic","Hindi","Bengali","Belarusian","Catalan","Urdu","Chinese","French"};


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


        ArrayAdapter<String> sourceAdapter = new ArrayAdapter<>(this,R.layout.spinner_item, sourceLanguages);
        sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSelection.setAdapter(sourceAdapter);
        sourceSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourceLanguageCode = GetLanguageCode(sourceLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sourceLanguageCode = null;
            }
        });

        ArrayAdapter<String> targetAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, targetLanguages);
        targetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        targetSelection.setAdapter(targetAdapter);
        targetSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                targetLanguageCode = GetLanguageCode(targetLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                targetLanguageCode = null; // Reset if nothing selected
            }
        });

        translate.setOnClickListener(v -> {
            targetText.setText("");
            if (sourceText.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter the Text", Toast.LENGTH_LONG).show();
            } else if (sourceLanguageCode == null || sourceLanguageCode.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please select the source language", Toast.LENGTH_LONG).show();
            } else if (targetLanguageCode == null || targetLanguageCode.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please select target language", Toast.LENGTH_LONG).show();
            } else {
                TranslateText(sourceLanguageCode, targetLanguageCode, sourceText.getText().toString());
            }
        });

    }

    private void TranslateText(String sourceLanguageCode, String targetLanguageCode, String string) {

        targetText.setText("Downloading Language Model....");
        try{
            TranslatorOptions options = new TranslatorOptions.Builder().
                    setSourceLanguage(sourceLanguageCode).
                    setTargetLanguage(targetLanguageCode).build();

            Translator translator = Translation.getClient(options);
            DownloadConditions conditions = new DownloadConditions.Builder().build();
            translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    targetText.setText("Translating.....");
                    translator.translate(string).addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            targetText.setText(s);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed to translate!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to Download the language model!", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private String GetLanguageCode(String language) {
        String languageCode;
        switch(language){
            case "Russian":
                languageCode = TranslateLanguage.RUSSIAN;
                break;
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
                break;
        }
        return languageCode;
    }
}